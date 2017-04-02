package com.rakuishi.postalcode.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentPostalCodeDetailBinding;
import com.rakuishi.postalcode.repository.BookmarkRepository;
import com.rakuishi.postalcode.repository.PostalCodeRepository;
import com.rakuishi.postalcode.view.adapter.PostalCodeDetailAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;
import com.rakuishi.postalcode.view.helper.NendHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PostalCodeDetailFragment extends BaseFragment implements
        FloatingActionButton.OnClickListener, PostalCodeDetailAdapter.Callback {

    private final static String CODE = "code";
    private FragmentPostalCodeDetailBinding binding;
    private String code;
    private PostalCodeDetailAdapter adapter;
    @Inject
    Gson gson;
    @Inject
    BookmarkRepository bookmarkRepository;
    @Inject
    PostalCodeRepository postalCodeRepository;
    @Inject
    CompositeDisposable compositeDisposable;

    public static PostalCodeDetailFragment newInstance(String code) {
        PostalCodeDetailFragment fragment = new PostalCodeDetailFragment();
        Bundle args = new Bundle();
        args.putString(CODE, code);
        fragment.setArguments(args);
        return fragment;
    }

    public PostalCodeDetailFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appComponent().inject(this);
        code = getArguments().getString(CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_postal_code_detail, container, false);

        adapter = new PostalCodeDetailAdapter(getContext(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        binding.recyclerView.setAdapter(adapter);
        NendHelper.setPadding(getContext(), binding.recyclerView);

        binding.fab.setOnClickListener(this);
        binding.fab.setEnabled(false);
        NendHelper.setMargin(getContext(), binding.fab);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Disposable disposable = postalCodeRepository.findByCode(code)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCode, throwable) -> {
                    if (postalCode != null) {
                        adapter.add(postalCode);
                        binding.fab.setEnabled(true);
                        updateFABImageDrawable(code);
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void updateFABImageDrawable(String code) {
        int resId = bookmarkRepository.exists(code) ? R.drawable.ic_bookmark_white_24dp : R.drawable.ic_bookmark_border_white_24dp;
        binding.fab.setImageDrawable(getResources().getDrawable(resId));
    }

    // region FloatingActionButton.OnClickListener

    @Override
    public void onClick(View v) {
        if (bookmarkRepository.exists(code)) {
            bookmarkRepository.remove(code);
        } else {
            bookmarkRepository.add(code);
        }

        updateFABImageDrawable(code);
    }

    // engregion

    // region PostalCodeDetailAdapter.Callback

    @Override
    public void onLongClickText(String text) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), getString(R.string.copied_format, text), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickShare(String q) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, q);
        intent.setType("text/plain");
        startActivity(intent);
    }

    @Override
    public void onClickOpenInGoogleMaps(String q) {
        Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(q));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    // endregion
}
