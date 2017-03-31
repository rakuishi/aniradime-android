package com.rakuishi.postalcode.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.ActivityMainBinding;
import com.rakuishi.postalcode.view.fragment.BookmarkFragment;
import com.rakuishi.postalcode.view.fragment.PrefectureFragment;
import com.rakuishi.postalcode.view.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_TAG_PREFECTURE = "prefecture";
    private static final String FRAGMENT_TAG_SEARCH = "search";
    private static final String FRAGMENT_TAG_BOOKMARK = "bookmark";
    private ActivityMainBinding binding;
    private PrefectureFragment prefectureFragment;
    private SearchFragment searchFragment;
    private BookmarkFragment bookmarkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        appComponent().inject(this);
        setupFragments();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            replaceFragment(item.getItemId());
            return true;
        });

        if (savedInstanceState == null) {
            replaceFragment(R.id.action_prefecture);
        }
    }

    private void setupFragments() {
        final FragmentManager manager = getSupportFragmentManager();
        prefectureFragment = (PrefectureFragment) manager.findFragmentByTag(FRAGMENT_TAG_PREFECTURE);
        searchFragment = (SearchFragment) manager.findFragmentByTag(FRAGMENT_TAG_SEARCH);
        bookmarkFragment = (BookmarkFragment) manager.findFragmentByTag(FRAGMENT_TAG_BOOKMARK);

        if (prefectureFragment == null) {
            prefectureFragment = PrefectureFragment.newInstance();
        }
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
        }
        if (bookmarkFragment == null) {
            bookmarkFragment = BookmarkFragment.newInstance();
        }
    }

    private void replaceFragment(@IdRes int itemId) {
        switch (itemId) {
            case R.id.action_prefecture:
                replaceFragment(prefectureFragment, FRAGMENT_TAG_PREFECTURE);
                break;
            case R.id.action_search:
                replaceFragment(searchFragment, FRAGMENT_TAG_SEARCH);
                break;
            case R.id.action_bookmark:
                replaceFragment(bookmarkFragment, FRAGMENT_TAG_BOOKMARK);
                break;
        }
    }

    private void replaceFragment(@NonNull Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            return;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);

        final Fragment currentFragment = manager.findFragmentById(R.id.container);
        if (currentFragment != null) {
            transaction.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            transaction.attach(fragment);
        } else {
            transaction.add(R.id.container, fragment, tag);
        }
        transaction.commit();
    }
}
