package com.rakuishi.postalcode.view.fragment;

import android.support.v4.app.Fragment;

import com.rakuishi.postalcode.App;
import com.rakuishi.postalcode.di.AppComponent;

public class BaseFragment extends Fragment {

    protected AppComponent appComponent() {
        return ((App) getActivity().getApplication()).getAppComponent();
    }
}
