package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.view.activity.MainActivity;
import com.rakuishi.postalcode.view.activity.PostalCodeActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
    void inject(PostalCodeActivity activity);
}
