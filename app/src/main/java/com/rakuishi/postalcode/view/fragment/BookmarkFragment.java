package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentToolbarRecyclerViewEmptyViewBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.repository.BookmarkRepository;
import com.rakuishi.postalcode.repository.PostalCodeRepository;
import com.rakuishi.postalcode.view.activity.PostalCodeActivity;
import com.rakuishi.postalcode.view.adapter.PostalCodeListAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;
import com.rakuishi.postalcode.view.helper.NendHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.rakuishi.postalcode.PostalCodeViewType.BOOKMARK;

public class BookmarkFragment extends BaseFragment implements PostalCodeListAdapter.Callback {

    private FragmentToolbarRecyclerViewEmptyViewBinding binding;
    private PostalCodeListAdapter adapter;
    @Inject
    PostalCodeRepository postalCodeRepository;
    @Inject
    BookmarkRepository bookmarkRepository;
    @Inject
    CompositeDisposable compositeDisposable;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    public BookmarkFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_toolbar_recycler_view_empty_view, container, false);

        binding.view.toolbar.setTitle(R.string.bookmark);

        adapter = new PostalCodeListAdapter(getContext(), BOOKMARK, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        binding.recyclerView.setAdapter(adapter);
        NendHelper.setPadding(getContext(), binding.recyclerView);

        binding.emptyView.setDrawable(R.drawable.ic_bookmark_white_24dp);
        binding.emptyView.setText(R.string.empty_bookmark);
        binding.emptyView.setVisibility(View.GONE);
        NendHelper.setPadding(getContext(), binding.emptyView);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.progressBar.setVisibility(View.VISIBLE);

        Disposable disposable = postalCodeRepository.findByCodes(bookmarkRepository.findAll())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    if (throwable == null) {
                        adapter.addAll(postalCodes);
                        binding.emptyView.setVisibility(postalCodes.size() > 0 ? View.GONE : View.VISIBLE);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    // region PostalCodeListAdapter.Callback

    @Override
    public void onItemClick(PostalCode postalCode) {
        startActivity(PostalCodeActivity.newIntent(getContext(), postalCode));
    }

    // endregion
}
