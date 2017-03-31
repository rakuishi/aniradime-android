package com.rakuishi.postalcode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.ActivityPostalCodeBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.view.fragment.PostalCodeDetailFragment;
import com.rakuishi.postalcode.view.fragment.PostalCodeListFragment;
import com.rakuishi.postalcode.PostalCodeViewType;

import java.util.ArrayList;
import java.util.List;

import static com.rakuishi.postalcode.PostalCodeViewType.CITY;
import static com.rakuishi.postalcode.PostalCodeViewType.DETAIL;
import static com.rakuishi.postalcode.PostalCodeViewType.STREET;

public class PostalCodeActivity extends BaseActivity {

    private final static String TYPE = "type";
    private final static String ID = "id";
    private final static String CODE = "code";
    private final static String TITLE = "title";
    private ActivityPostalCodeBinding binding;
    private List<String> fragmentNames = new ArrayList<>();

    public static Intent newIntent(Context context, PostalCodeViewType type, int id, String title) {
        Intent intent = new Intent(context, PostalCodeActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        return intent;
    }

    public static Intent newIntent(Context context, PostalCode postalCode) {
        Intent intent = new Intent(context, PostalCodeActivity.class);
        intent.putExtra(TYPE, DETAIL);
        intent.putExtra(CODE, postalCode.code);
        intent.putExtra(TITLE, postalCode.getFullName());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_postal_code);
        appComponent().inject(this);
        setSupportActionBar(binding.view.toolbar);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(TYPE)) {
            throw new IllegalStateException("PostalCodeActivity requires type and id parameters.");
        }

        PostalCodeViewType type = (PostalCodeViewType) intent.getSerializableExtra(TYPE);
        int id = intent.getIntExtra(ID, 0);
        String code = intent.hasExtra(CODE) ? intent.getStringExtra(CODE) : "";
        String title = intent.hasExtra(TITLE) ? intent.getStringExtra(TITLE) : "";
        Fragment fragment = null;

        switch (type) {
            case CITY:
                fragment = PostalCodeListFragment.newInstance(CITY, id);
                replaceFragment(fragment, title);
                break;
            case STREET:
                fragment = PostalCodeListFragment.newInstance(STREET, id);
                replaceFragment(fragment, title);
                break;
            case DETAIL:
                fragment = PostalCodeDetailFragment.newInstance(code);
                replaceFragment(fragment, title);
                break;
        }
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(fragmentNames.get(fragmentNames.size() - 1));
        }
    }
}
