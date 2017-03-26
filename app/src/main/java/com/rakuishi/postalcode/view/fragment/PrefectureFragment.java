package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentPrefectureBinding;

public class PrefectureFragment extends BaseFragment {

    private FragmentPrefectureBinding binding;

    public static PrefectureFragment newInstance() {
        return new PrefectureFragment();
    }

    public PrefectureFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prefecture, container, false);
        return binding.getRoot();
    }
}
