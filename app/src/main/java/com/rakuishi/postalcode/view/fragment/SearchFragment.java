package com.rakuishi.postalcode.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.FragmentSearchBinding;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.repository.PostalCodeRepository;
import com.rakuishi.postalcode.view.adapter.PostalCodeListAdapter;
import com.rakuishi.postalcode.view.helper.DividerItemDecoration;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

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

    }

    // endregion

    // region FloatingSearchView.OnSearchListener

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {
        Disposable disposable = postalCodeRepository.find(currentQuery)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    if (throwable == null) {
                        adapter.addAll(postalCodes);
                    }
                    // binding.progressBar.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    // endregion
}
