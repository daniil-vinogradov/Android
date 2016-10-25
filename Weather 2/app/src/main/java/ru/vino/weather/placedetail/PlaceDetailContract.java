package ru.vino.weather.placedetail;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import ru.vino.weather.base.BasePresenter;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.DailyForecastModel;

public interface PlaceDetailContract {

    interface IPlaceDetailView {
        void presentPhoto(Bitmap bitmap);

        void presentForecast(List<DailyForecastModel> data);

        void showProgressBar();

        void showError();
    }

    abstract class AbstractPlaceDetailPresenter extends BasePresenter<IPlaceDetailView> {

        public abstract void attachView(PlaceDetailContract.IPlaceDetailView view,
                                        CurrentConditionModel currentCondition);

        public abstract void getPhoto(Context context);

        public abstract void getForecast();
    }

}
