package com.example.nikola.criminal;

import android.app.Application;
import android.content.Context;

import com.example.nikola.criminal.database.CrimeLabHelper;
import com.facebook.stetho.Stetho;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrimeLabHelper.getInstance().init(this);
        stethoInit(this);
    }

    private void stethoInit(Context context) {
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(context);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(context)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(context)
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }
}

