package com.rakuishi.postalcode.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.view.fragment.PostalCodeListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    // FIXME: I want to use `FragmentManager.getBackStackEntryCount()` for control of fragments.
    private List<String> fragmentNames = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appComponent().inject(this);

        if (savedInstanceState == null) {
            PostalCodeListFragment fragment = PostalCodeListFragment.newInstance(PostalCodeListFragment.Type.PREFECTURE);
            replaceFragment(fragment, getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // searchView.setIconified(false);
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (fragmentNames.size() > 1) {
            manager.popBackStack();
            fragmentNames.remove(fragmentNames.size() - 1);
            updateActionBar();
            return;
        }

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void replaceFragment(@NonNull Fragment fragment, @NonNull String title) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        fragmentNames.add(title);
        updateActionBar();
    }

    public void updateActionBar() {
        if (getSupportActionBar() != null && fragmentNames.size() > 0) {
            boolean enabled = fragmentNames.size() > 1;
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setDisplayShowHomeEnabled(enabled);
            getSupportActionBar().setTitle(fragmentNames.get(fragmentNames.size() - 1));
        }
    }
}
