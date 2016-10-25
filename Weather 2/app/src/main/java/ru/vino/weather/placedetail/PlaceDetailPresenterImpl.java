package ru.vino.weather.placedetail;

import android.content.Context;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.vino.weather.interactors.IWeatherInteractor;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.PhotoModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PlaceDetailPresenterImpl extends PlaceDetailContract.AbstractPlaceDetailPresenter {

    IWeatherInteractor interactor;
    CurrentConditionModel currentCondition;

    @Inject
    public PlaceDetailPresenterImpl(IWeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void attachView(PlaceDetailContract.IPlaceDetailView view,
                           CurrentConditionModel currentCondition) {
        super.attachView(view);
        this.currentCondition = currentCondition;
    }

    @Override
    public void getPhoto(Context context) {
        interactor
                .getCachedPhoto(currentCondition.getId())
                .doOnNext(photoModel -> {
                    if (photoModel == null)
                        throw new RuntimeException("no cached photo");
                })
                .onErrorResumeNext(throwable -> {
                    return interactor.getPhoto(currentCondition.getName())
                            .map(photosResponseModel -> {
                                List<PhotoModel> photoModels = photosResponseModel.getPhotos().getPhotoModel();
                                Collections.sort(photoModels, (o1, o2) -> o2.getViews() - o1.getViews());
                                PhotoModel photo = photoModels.get(0);
                                photo.setId(currentCondition.getId());
                                return photo;
                            })
                            .doOnNext(photo -> interactor.cachePhoto(photo));
                })
                .map(photo -> {
                    try {
                        return Picasso.with(context)
                                .load(photo.getUrlL())
                                .get();
                    } catch (IOException e) {
                        throw new RuntimeException("photo loading error");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    if (isViewAttached())
                        getView().presentPhoto(bitmap);
                }, Throwable::printStackTrace);
    }

    @Override
    public void getForecast() {
        if (isViewAttached())
            getView().showProgressBar();

        Observable.concat(
                interactor
                        .getCachedForecast(currentCondition.getId())
                        .first()
                        .filter(dailyForecastModels -> !dailyForecastModels.isEmpty()),
                interactor
                        .getForecast(currentCondition.getId())
                        .flatMap(dailyForecastResponse ->
                                Observable.from(dailyForecastResponse.getDailyForecastModels()))
                        .skip(1)
                        .doOnNext(dailyForecast -> dailyForecast.setPlaceId(currentCondition.getId()))
                        .toList()
                        .doOnNext(dailyForecastModels -> {
                            interactor.deleteForecast(currentCondition.getId());
                            interactor.cacheForecast(dailyForecastModels);
                        })
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(dailyForecastModels -> {
                    if (isViewAttached()) {
                        if (dailyForecastModels != null)
                            getView().presentForecast(dailyForecastModels);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    if (isViewAttached())
                        getView().showError();
                });

    }
}
