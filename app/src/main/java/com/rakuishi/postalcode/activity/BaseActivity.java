package com.rakuishi.postalcode.activity;

import android.support.v7.app.AppCompatActivity;

import com.rakuishi.postalcode.App;
import com.rakuishi.postalcode.di.ActivityComponent;
import com.rakuishi.postalcode.di.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    protected ActivityComponent appComponent() {
        App app = (App) getApplication();
        return app.getAppComponent().activityComponent(new ActivityModule(this));
    }
}
