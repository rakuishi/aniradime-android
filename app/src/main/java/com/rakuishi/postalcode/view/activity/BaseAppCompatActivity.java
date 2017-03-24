package com.rakuishi.postalcode.view.activity;

import android.support.v7.app.AppCompatActivity;

import com.rakuishi.postalcode.App;
import com.rakuishi.postalcode.di.AppComponent;

public class BaseAppCompatActivity extends AppCompatActivity {

    protected AppComponent appComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
