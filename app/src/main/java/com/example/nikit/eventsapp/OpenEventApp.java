package com.example.nikit.eventsapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nikit.eventsapp.data.RealmDatabaseMigration;
import com.example.nikit.eventsapp.rest.Urls;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.picasso.Picasso;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Locale;

import io.branch.referral.Branch;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import timber.log.Timber;

public class OpenEventApp extends MultiDexApplication {

    public static final String API_LINK = "api-link";
    public static final String EMAIL = "email";
    public static final String APP_NAME = "app-name";
    public static final String AUTH_OPTION = "is-auth-enabled";

    public static String defaultSystemLanguage;
    private static Handler handler;
    private static WeakReference<Context> context;
    public static Picasso picassoWithCache;
    private static TextDrawable.IShapeBuilder textDrawableBuilder;
    private static ObjectMapper objectMapper;
    private MainActivity activity;



    public static Context getAppContext() {
        return context.get();
    }

    public static ObjectMapper getObjectMapper(){
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    public static TextDrawable.IShapeBuilder getTextDrawableBuilder() {
        if (textDrawableBuilder == null) {
            textDrawableBuilder = TextDrawable.builder();
        }
        return textDrawableBuilder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        context = new WeakReference<>(getApplicationContext());

        Branch.getAutoInstance(this);

        defaultSystemLanguage = Locale.getDefault().getDisplayLanguage();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(RealmDatabaseMigration.DB_VERSION) // Must be bumped when the schema changes
                //TODO: Re-add migration once DB is locked/finalized
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        AndroidThreeTen.init(this);

        //Initialize Cache
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cache(cache);

        if (BuildConfig.DEBUG) {
            // Create an InitializerBuilder
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());

            //Initialize Stetho Interceptor into OkHttp client
            OkHttpClient httpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
            okHttpClientBuilder = okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());

            //Initialize Picasso
            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(httpClient)).build();
            Picasso.setSingletonInstance(picasso);

            Timber.plant(new Timber.DebugTree());
        }

        //Initialize Picasso with cache
        picassoWithCache = new Picasso.Builder(this).downloader(new OkHttp3Downloader(okHttpClientBuilder.build())).build();


        String config_json = null;
        String event_json = null;

        //getting config.json data
        try {
            InputStream inputStream = getAssets().open("config.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            config_json = new String(buffer, "UTF-8");
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(config_json);
            String email = jsonObject.has(EMAIL) ? jsonObject.getString(EMAIL) : "";
            String appName = jsonObject.has(APP_NAME) ? jsonObject.getString(APP_NAME) : "";
            String apiLink = jsonObject.has(API_LINK) ? jsonObject.getString(API_LINK) : "";
            boolean isAuthEnabled = jsonObject.has(AUTH_OPTION) && jsonObject.getBoolean(AUTH_OPTION);

            Urls.setBaseUrl(apiLink);

            SharedPreferencesUtil.putString(ConstantStrings.EMAIL, email);
            SharedPreferencesUtil.putString(ConstantStrings.APP_NAME, appName);
            SharedPreferencesUtil.putString(ConstantStrings.BASE_API_URL, apiLink);
            SharedPreferencesUtil.putBoolean(ConstantStrings.IS_AUTH_ENABLED, isAuthEnabled);

            if (extractEventIdFromApiLink(apiLink) != 0)
                SharedPreferencesUtil.putInt(ConstantStrings.EVENT_ID, extractEventIdFromApiLink(apiLink));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        }


    private int extractEventIdFromApiLink(String apiLink){
        if(apiLink.isEmpty())
            return 0;

        return Integer.parseInt(apiLink.split("/v1/events/")[1].replace("/",""));
    }

    public void attachMainActivity(MainActivity activity) {
        this.activity = activity;
    }

    public void detachMainActivity() {
        this.activity = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        defaultSystemLanguage = newConfig.locale.getDisplayLanguage();
    }


}
