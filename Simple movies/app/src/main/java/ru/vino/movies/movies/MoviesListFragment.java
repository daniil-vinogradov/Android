package ru.vino.movies.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.movies.IntentStarter;
import ru.vino.movies.R;
import ru.vino.movies.Utils;
import ru.vino.movies.base.PresenterProvider;
import ru.vino.movies.model.ShortMovieModel;
import ru.vino.movies.widgets.RecyclerInsetsDecoration;

public class MoviesListFragment extends Fragment implements MoviesContract.IMoviesView {

    MoviesContract.AbstractMoviesPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.err_msg)
    ViewGroup errorContainer;

    MoviesRecyclerAdapter adapter;
    GridLayoutManager layoutManager;

    public static MoviesListFragment newInstance(String TAG) {
        MoviesListFragment fragment = new MoviesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TAG", TAG);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PresenterProvider) {
            presenter = (MoviesContract.AbstractMoviesPresenter)
                    ((PresenterProvider) context).getPresenter(getArguments().getString("TAG"));
        } else {
            throw new IllegalStateException("You should provide PresenterProvider");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.restoreState(savedInstanceState);

        adapter = new MoviesRecyclerAdapter((poster, card, movie) ->
                IntentStarter.openMovieInfoActivity(getActivity(), poster, card, movie));

        int spanCount = Utils.calculateNoOfColumns(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getMovies().get(position) == null ? spanCount : 1;
            }
        });

        recyclerView.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        RxRecyclerView.scrollEvents(recyclerView)
                .subscribe(recyclerViewScrollEvent -> {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!adapter.isLoading())
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            presenter.getMovies(true);
                        }

                });

        presenter.attachView(this);
        presenter.getMovies(false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState);
    }

    @Override
    public void presentMovies(List<ShortMovieModel> movies) {
        adapter.addMovies(movies);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    @Override
    public void showPaginationProgress() {
        adapter.setLoading();
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
    }

}
