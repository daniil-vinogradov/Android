package ru.vino.weather.places;

import javax.inject.Inject;

import ru.vino.weather.interactors.IWeatherInteractor;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacesPresenterImpl extends PlacesContract.AbstractPlacesPresenter {

    IWeatherInteractor interactor;

    PlacesViewState viewState;

    @Inject
    public PlacesPresenterImpl(IWeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void attachView(PlacesContract.IPlacesView view, PlacesViewState viewState) {
        super.attachView(view);
        this.viewState = viewState;
    }

    @Override
    public void getPlaces(boolean userSwipe) {
        if (isViewAttached())
            getView().showPullToRefresh();
        else viewState.setPullToRefreshState(true);

        interactor
                .getCachedCurrentCondition()
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(currentConditionModels -> {
                    if (!userSwipe)
                        if (isViewAttached())
                            getView().presentPlaces(currentConditionModels);
                        else viewState.setDataLoadedState(currentConditionModels);
                })
                .observeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(currentCondition ->
                        interactor
                                .getCurrentCondition(currentCondition.getId())
                                .map(item ->
                                        setCurrentConditionData(item,
                                                currentCondition.getId(),
                                                currentCondition.getName(),
                                                currentCondition.getArea(),
                                                currentCondition.getAddTime()))
                )
                .toList()
                .doOnNext(currentConditions -> interactor.cacheCurrentConditions(currentConditions))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentConditions -> {
                    if (isViewAttached()) {
                        getView().presentPlaces(currentConditions);
                        getView().hidePullToRefresh();
                    } else {
                        viewState.setDataLoadedState(currentConditions);
                        viewState.setPullToRefreshState(false);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    if (isViewAttached()) {
                        getView().hidePullToRefresh();
                        getView().showError();
                    } else viewState.setPullToRefreshState(false);
                });
    }

    @Override
    public void addNewPlace(AutocompleteResponseModel place) {
        long placeId = Long.valueOf(place.getKey());
        interactor
                .getCurrentCondition(Long.valueOf(place.getKey()))
                .map(currentCondition ->
                        setCurrentConditionData(currentCondition,
                                placeId,
                                place.getLocalizedName(),
                                place.getAdministrativeArea().getLocalizedName(),
                                System.currentTimeMillis()))
                .doOnNext(currentConditionModel ->
                        interactor.cacheCurrentCondition(currentConditionModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentConditionModel -> {
                    if (isViewAttached())
                        getView().presentPlace(currentConditionModel);
                    else viewState.setPlaceLoadedState(currentConditionModel);
                }, throwable -> {
                    throwable.printStackTrace();
                    if (isViewAttached())
                        getView().placeLoadingError(placeId);
                });
    }

    @Override
    public void deletePlace(CurrentConditionModel currentCondition, int pos) {
        interactor.deleteCurrentCondition(currentCondition);
        if (isViewAttached())
            getView().showDeleteSnackBar(currentCondition, pos);
    }

    @Override
    public void undoDelete(CurrentConditionModel currentCondition) {
        interactor.cacheCurrentCondition(currentCondition);
    }

    private CurrentConditionModel setCurrentConditionData(CurrentConditionModel o, long id,
                                                          String name, String area, long addTime) {
        o.setId(id);
        o.setName(name);
        o.setArea(area);
        o.setAddTime(addTime);
        return o;
    }

}
