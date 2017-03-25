package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.view.fragment.PostalCodeListFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(PostalCodeListFragment fragment);
}
