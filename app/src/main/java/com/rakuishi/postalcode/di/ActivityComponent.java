package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.view.activity.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
