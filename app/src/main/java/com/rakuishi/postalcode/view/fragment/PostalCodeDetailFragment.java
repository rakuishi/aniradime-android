package com.rakuishi.postalcode.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentPostalCodeDetailBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.view.adapter.PostalCodeDetailAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;

import javax.inject.Inject;

public class PostalCodeDetailFragment extends BaseFragment {

    private final static String POSTAL_CODE = "postal_code";
    private FragmentPostalCodeDetailBinding binding;
    private PostalCode postalCode;
    @Inject
    Gson gson;

    public static PostalCodeDetailFragment newInstance(String postalCode) {
        PostalCodeDetailFragment fragment = new PostalCodeDetailFragment();
        Bundle args = new Bundle();
        args.putString(POSTAL_CODE, postalCode);
        fragment.setArguments(args);
        return fragment;
    }

    public PostalCodeDetailFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appComponent().inject(this);
        postalCode = gson.fromJson(getArguments().getString(POSTAL_CODE), PostalCode.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_postal_code_detail, container, false);
        PostalCodeDetailAdapter adapter = new PostalCodeDetailAdapter(getContext(), postalCode);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}
