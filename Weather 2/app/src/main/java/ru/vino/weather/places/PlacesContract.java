package ru.vino.weather.places;


import java.util.List;

import ru.vino.weather.base.BasePresenter;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public interface PlacesContract {

    interface IPlacesView {
        void presentPlace(CurrentConditionModel data);

        void presentPlaces(List<CurrentConditionModel> data);

        void showDeleteSnackBar(CurrentConditionModel currentCondition, int pos);

        void showPullToRefresh();

        void hidePullToRefresh();

        void showError();

        void placeLoadingError(long id);
    }

    abstract class AbstractPlacesPresenter extends BasePresenter<IPlacesView> {

        public abstract void attachView(IPlacesView view, PlacesViewState viewState);

        public abstract void getPlaces(boolean userSwipe);

        public abstract void addNewPlace(AutocompleteResponseModel place);

        public abstract void deletePlace(CurrentConditionModel currentCondition, int pos);

        public abstract void undoDelete(CurrentConditionModel currentCondition);
    }

}
