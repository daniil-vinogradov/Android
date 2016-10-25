package ru.vino.weather.places;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vino.weather.R;
import ru.vino.weather.WeatherApplication;
import ru.vino.weather.base.BaseFragment;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import rx.Observable;

public class PlacesFragment extends BaseFragment implements PlacesContract.IPlacesView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    PlacesContract.AbstractPlacesPresenter presenter;
    PlacesViewState viewState;

    PlacesAdapter adapter;

    Unbinder unbinder;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.empty_state)
    LinearLayout emptyState;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (viewState == null)
            viewState = new PlacesViewState();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (presenter == null)
            WeatherApplication.from(getActivity()).getComponent().inject(this);
        presenter.attachView(this, viewState);

        toolbar.setTitle(R.string.title_cities);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PlacesAdapter(
                viewState.getData(),
                viewState.getLoadingPlaces(),
                (card, currentCondition) -> getListener().openPlaceDetail(card, currentCondition),
                (currentCondition, pos) -> presenter.deletePlace(currentCondition, pos));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(v -> {
            getListener().openSearch(
                    (fab.getLeft() + fab.getRight()) / 2,
                    (fab.getTop() + fab.getBottom()) / 2,
                    fab.getWidth() / 2);
            fab.hide();
        });

        if (viewState.getData().isEmpty())
            presenter.getPlaces(false);

        if (viewState.getCurrentState() == PlacesViewState.STATE_PULL_TO_REFRESH)
            showPullToRefresh();

    }

    public void showFab() {
        fab.show();
    }

    public void loadNewPlace(AutocompleteResponseModel place) {
        boolean exist = false;
        for (CurrentConditionModel item : viewState.getData())
            if (item.getId() == Long.valueOf(place.getKey()))
                exist = true;
        if (!exist) {
            presenter.addNewPlace(place);
            viewState.setPlaceLoadingState(place);
            adapter.addNewPlace();
            emptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void presentPlace(CurrentConditionModel data) {
        viewState.setPlaceLoadedState(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void presentPlaces(List<CurrentConditionModel> data) {
        viewState.setDataLoadedState(data);
        adapter.notifyDataSetChanged();
        if (data == null || data.isEmpty())
            emptyState.setVisibility(View.VISIBLE);
        else emptyState.setVisibility(View.GONE);
    }

    @Override
    public void showDeleteSnackBar(CurrentConditionModel currentCondition, int pos) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.place_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), view -> {
                    viewState.setPlaceUndoState(currentCondition, pos);
                    presenter.undoDelete(currentCondition);
                    adapter.notifyItemInserted(pos);
                });
        snackbar.show();
    }

    @Override
    public void showPullToRefresh() {
        viewState.setPullToRefreshState(true);
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setFocusableInTouchMode(false);
    }

    @Override
    public void hidePullToRefresh() {
        viewState.setPullToRefreshState(false);
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setFocusableInTouchMode(true);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.err_connection), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void placeLoadingError(long id) {
        showError();
        viewState.deleteLoadingPlace(id);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        viewState.setPullToRefreshState(true);
        presenter.getPlaces(true);
        swipeRefreshLayout.setRefreshing(true);
    }
}
