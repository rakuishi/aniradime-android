package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentSearchBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.repository.PostalCodeRepository;
import com.rakuishi.postalcode.view.activity.PostalCodeActivity;
import com.rakuishi.postalcode.view.adapter.PostalCodeListAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.rakuishi.postalcode.PostalCodeViewType.DETAIL;
import static com.rakuishi.postalcode.PostalCodeViewType.PREFECTURE;
import static com.rakuishi.postalcode.PostalCodeViewType.SEARCH;

public class SearchFragment extends BaseFragment implements PostalCodeListAdapter.Callback,
        FloatingSearchView.OnSearchListener {

    private FragmentSearchBinding binding;
    private PostalCodeListAdapter adapter;
    @Inject
    PostalCodeRepository postalCodeRepository;
    @Inject
    CompositeDisposable compositeDisposable;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        adapter = new PostalCodeListAdapter(getContext(), SEARCH, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
        binding.recyclerView.setAdapter(adapter);

        binding.searchView.setOnSearchListener(this);

        binding.emptyView.setDrawable(R.drawable.ic_search_white_24dp);
        binding.emptyView.setText(R.string.empty_search);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    // region PostalCodeListAdapter.Callback

    @Override
    public void onItemClick(PostalCode postalCode) {
        startActivity(PostalCodeActivity.newInstance(getContext(), postalCode));
    }

    // endregion

    // region FloatingSearchView.OnSearchListener

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {
        if (TextUtils.isEmpty(currentQuery)) {
            adapter.clear();
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.emptyView.setText(R.string.empty_search);
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        Disposable disposable = postalCodeRepository.find(currentQuery)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    if (throwable == null) {
                        adapter.addAll(postalCodes);
                        binding.emptyView.setVisibility(postalCodes.size() > 0 ? View.GONE : View.VISIBLE);
                        binding.emptyView.setText(TextUtils.isEmpty(currentQuery) ? R.string.empty_search : R.string.no_results);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    // endregion
}
