package com.rakuishi.postalcode.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.view.fragment.PostalCodeListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appComponent().inject(this);

        if (savedInstanceState == null) {
            PostalCodeListFragment fragment = new PostalCodeListFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container, fragment);
            // transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commit();
        }
    }
}
