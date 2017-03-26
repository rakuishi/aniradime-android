package com.rakuishi.postalcode.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.view.fragment.PostalCodeListFragment;

public class MainActivity extends BaseActivity {

    // FIXME: I want to use `FragmentManager.getBackStackEntryCount()` for control of fragments.
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appComponent().inject(this);

        if (savedInstanceState == null) {
            PostalCodeListFragment fragment = PostalCodeListFragment.newInstance(PostalCodeListFragment.Type.PREFECTURE);
            replaceFragment(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (count > 1) {
            manager.popBackStack();
            count--;
            updateShowHomeButton();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        count++;

        updateShowHomeButton();
    }

    public void updateShowHomeButton() {
        if (getSupportActionBar() != null) {
            boolean enabled = count > 1;
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setDisplayShowHomeEnabled(enabled);
        }
    }
}
