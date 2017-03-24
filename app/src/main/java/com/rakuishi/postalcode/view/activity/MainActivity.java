package com.rakuishi.postalcode.view.activity;

import android.os.Bundle;

import com.rakuishi.postalcode.R;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appComponent().inject(this);
    }
}
