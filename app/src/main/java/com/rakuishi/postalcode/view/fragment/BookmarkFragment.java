package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentToolbarRecyclerViewBinding;

public class BookmarkFragment extends BaseFragment {

    private FragmentToolbarRecyclerViewBinding binding;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    public BookmarkFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_toolbar_recycler_view, container, false);

        binding.view.toolbar.setTitle(R.string.bookmark);

        return binding.getRoot();
    }
}
