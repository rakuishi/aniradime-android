package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.App;
import com.rakuishi.postalcode.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App application);
    void inject(MainActivity activity);
}
