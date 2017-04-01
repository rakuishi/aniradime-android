package com.rakuishi.postalcode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.ActivityInfoBinding;
import com.rakuishi.postalcode.view.adapter.InfoAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;

import timber.log.Timber;

public class InfoActivity extends BaseActivity implements InfoAdapter.Callback {

    private ActivityInfoBinding binding;
    private InfoAdapter adapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, InfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        setSupportActionBar(binding.view.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.info);

        adapter = new InfoAdapter(this, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // region InfoAdapter.Callback

    @Override
    public void onClickUri(String uri) {
        Timber.d("uri: " + uri);
    }

    // endregion
}