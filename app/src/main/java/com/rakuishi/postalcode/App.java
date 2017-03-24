package com.rakuishi.postalcode;

import android.app.Application;
import android.support.annotation.NonNull;

import com.rakuishi.postalcode.di.AppComponent;
import com.rakuishi.postalcode.di.AppModule;
import com.rakuishi.postalcode.di.DaggerAppComponent;
import com.rakuishi.postalcode.util.UnzipDbUtil;

import java.io.IOException;

import timber.log.Timber;

import static timber.log.Timber.DebugTree;

public class App extends Application {

    AppComponent appComponent;

    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        try {
            UnzipDbUtil.unzipIfNecessary(
                    this,
                    getString(R.string.zipped_database_name),
                    getString(R.string.database_name)
            );
        } catch (IOException | SecurityException e) {
            // TODO: Show alert message
            Timber.e(e);
        }
    }
}
