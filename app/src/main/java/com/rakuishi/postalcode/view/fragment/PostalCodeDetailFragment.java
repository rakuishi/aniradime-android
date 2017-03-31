package com.rakuishi.postalcode.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentPostalCodeDetailBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.repository.BookmarkRepository;
import com.rakuishi.postalcode.view.adapter.PostalCodeDetailAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;

import javax.inject.Inject;

import timber.log.Timber;

public class PostalCodeDetailFragment extends BaseFragment implements FloatingActionButton.OnClickListener {

    private final static String POSTAL_CODE = "postal_code";
    private FragmentPostalCodeDetailBinding binding;
    private PostalCode postalCode;
    @Inject
    Gson gson;
    @Inject
    BookmarkRepository repository;

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

        binding.fab.setOnClickListener(this);
        updateFABImageDrawable(postalCode.code);

        return binding.getRoot();
    }

    private void updateFABImageDrawable(String code) {
        int resId = repository.exists(code) ? R.drawable.ic_bookmark_white_24dp : R.drawable.ic_bookmark_border_white_24dp;
        binding.fab.setImageDrawable(getResources().getDrawable(resId));
    }

    // region FloatingActionButton.OnClickListener

    @Override
    public void onClick(View v) {
        if (repository.exists(postalCode.code)) {
            repository.remove(postalCode.code);
        } else {
            repository.add(postalCode.code);
        }

        updateFABImageDrawable(postalCode.code);
    }

    // engregion
}
