package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.view.fragment.BaseFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final BaseFragment fragment;

    public FragmentModule(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public BaseFragment provideFragment() {
        return fragment;
    }
}
