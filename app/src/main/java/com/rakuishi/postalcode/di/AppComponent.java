package com.rakuishi.postalcode.di;

import android.support.v4.app.FragmentManager;

import com.rakuishi.postalcode.App;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App application);
    ActivityComponent activityComponent(ActivityModule activityModule);
    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
