package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    public BaseActivity provideActivity() {
        return activity;
    }
}
