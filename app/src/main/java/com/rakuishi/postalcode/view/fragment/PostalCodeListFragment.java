package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentPostalCodeListBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.view.adapter.PostalCodeListAdapter;

public class PostalCodeListFragment extends BaseFragment implements PostalCodeListAdapter.Callback {

    private FragmentPostalCodeListBinding binding;
    private PostalCodeListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_postal_code_list, container, false);
        adapter = new PostalCodeListAdapter(getContext(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // region PostalCodeListAdapter.Callback

    @Override
    public void onItemClick(PostalCode postalCode) {
        // do something
    }

    // endregion
}
