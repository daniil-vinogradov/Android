package ru.vino.weather.search;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vino.weather.OnBackPressedListener;
import ru.vino.weather.R;
import ru.vino.weather.WeatherApplication;
import ru.vino.weather.base.BaseFragment;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import ru.vino.weather.utils.GuiUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


public class SearchFragment extends BaseFragment
        implements OnBackPressedListener, SearchContract.ISearchView {

    @Inject
    SearchContract.AbstractSearchPresenter presenter;
    Subscription subscription;
    ResultsViewState viewState;

    int cx = 0;
    int cy = 0;
    int startRadius = 0;

    Unbinder unbinder;
    @BindView(R.id.transition_container)
    LinearLayout transitionContainer;
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.card_result)
    CardView cardResult;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.clear)
    ImageButton clear;

    ResultsAdapter adapter;

    public static SearchFragment newInstance(int cx, int cy, int startRadius) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cx", cx);
        bundle.putInt("cy", cy);
        bundle.putInt("startRadius", startRadius);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewState = new ResultsViewState();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (presenter == null)
            WeatherApplication.from(getActivity()).getComponent().inject(this);
        presenter.attachView(this);

        cx = getArguments().getInt("cx");
        cy = getArguments().getInt("cy");
        startRadius = getArguments().getInt("startRadius");

        if (savedInstanceState == null)
            startEnterAnimation(view);
        else {
            view.setVisibility(View.VISIBLE);
            cardSearch.setVisibility(View.VISIBLE);
        }

        clear.setOnClickListener(v -> search.setText(""));

        subscription = RxTextView.textChanges(search)
                .skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    if (s.trim().isEmpty()) {
                        GuiUtils.animateSlide(transitionContainer, 0, 250, new GuiUtils.RevealAnimationListener() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {
                                adapter.setData(null);
                            }
                        });
                        cardResult.setVisibility(View.INVISIBLE);
                    }
                })
                .filter(s -> !s.trim().isEmpty())
                .subscribe(q -> presenter.search(q));

        adapter = new ResultsAdapter(place -> {
            View v = getActivity().getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            int delay = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                delay = 220;
            (new Handler()).postDelayed(() -> {
                if (getListener() != null)
                    getListener().loadNewPlace(place);
            }, delay);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (viewState.getResults() != null && !viewState.getResults().isEmpty())
            presentResults(viewState.getResults());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
        subscription.unsubscribe();
        adapter = null;
    }

    private void startEnterAnimation(View view) {
        int startDelay = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (isAdded()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    GuiUtils.animateRevealShow(view, cx, cy, startRadius, 250);
                    GuiUtils.animateSlide(transitionContainer, 100, 250, null);
                    cardSearch.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    GuiUtils.animateSlide(transitionContainer, 0, 250, null);
                    cardSearch.setVisibility(View.VISIBLE);
                }
            }
        }, startDelay);
    }

    @Override
    public void onBackPressed() {
        int startRadius = Math.max(transitionContainer.getWidth(), transitionContainer.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GuiUtils.animateRevealHide(transitionContainer, cx, cy, startRadius, 0, 250,
                    new GuiUtils.RevealAnimationListener() {
                        @Override
                        public void onAnimationStart() {

                        }

                        @Override
                        public void onAnimationEnd() {
                            if (getListener() != null)
                                getListener().back();
                        }
                    });
        } else getListener().back();
    }

    @Override
    public void presentResults(List<AutocompleteResponseModel> results) {
        if (results.isEmpty()) {
            GuiUtils.animateSlide(transitionContainer, 0, 250, null);
            cardResult.setVisibility(View.INVISIBLE);
            return;
        }
        adapter.setData(results);
        viewState.setResults(results);
        if (cardResult.getVisibility() != View.VISIBLE) {
            GuiUtils.animateSlide(transitionContainer, 0, 250, null);
            cardResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError() {
        Snackbar
                .make(transitionContainer, getString(R.string.err_connection), Snackbar.LENGTH_LONG)
                .show();
    }
}
