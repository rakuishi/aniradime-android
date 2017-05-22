package com.rakuishi.postalcode.di;

import com.rakuishi.postalcode.fragment.BookmarkFragment;
import com.rakuishi.postalcode.fragment.PostalCodeDetailFragment;
import com.rakuishi.postalcode.fragment.PostalCodeListFragment;
import com.rakuishi.postalcode.fragment.PrefectureFragment;
import com.rakuishi.postalcode.fragment.SearchFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(PrefectureFragment fragment);
    void inject(SearchFragment fragment);
    void inject(BookmarkFragment fragment);
    void inject(PostalCodeListFragment fragment);
    void inject(PostalCodeDetailFragment fragment);
}
