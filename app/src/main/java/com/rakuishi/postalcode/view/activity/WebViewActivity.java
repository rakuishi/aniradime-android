package com.rakuishi.postalcode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.ActivityWebViewBinding;

public class WebViewActivity extends BaseActivity {

    private static final String URL = "url";
    private ActivityWebViewBinding binding;

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(URL)) {
            throw new IllegalStateException("WebViewActivity requires url parameter.");
        }

        setSupportActionBar(binding.view.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.license);

        binding.webView.loadUrl(intent.getStringExtra(URL));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
