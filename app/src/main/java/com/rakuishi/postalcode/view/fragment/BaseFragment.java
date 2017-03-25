package com.rakuishi.postalcode.view.fragment;

import android.support.v4.app.Fragment;

import com.rakuishi.postalcode.App;
import com.rakuishi.postalcode.di.FragmentComponent;
import com.rakuishi.postalcode.di.FragmentModule;

public class BaseFragment extends Fragment {

    protected FragmentComponent appComponent() {
        App app = (App) getActivity().getApplication();
        return app.getAppComponent().fragmentComponent(new FragmentModule(this));
    }
}
