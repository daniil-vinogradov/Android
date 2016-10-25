package ru.vino.weather.places;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public class PlacesViewState {

    public static final int STATE_PULL_TO_REFRESH = 1;

    private int currentState;
    private List<CurrentConditionModel> data = new ArrayList<>();
    private Set<Long> loadingPlaces = new HashSet<>();

    public void setPlaceLoadingState(AutocompleteResponseModel place) {
        loadingPlaces.add(Long.valueOf(place.getKey()));
        data.add(new CurrentConditionModel(
                Long.valueOf(place.getKey()),
                place.getLocalizedName(),
                place.getAdministrativeArea().getLocalizedName()));
    }

    public void setPlaceLoadedState(CurrentConditionModel currentCondition) {
        loadingPlaces.remove(currentCondition.getId());
        data.remove(currentCondition);
        data.add(currentCondition);
    }

    public void deleteLoadingPlace(Long id) {
        loadingPlaces.remove(id);
        for (CurrentConditionModel item : data)
            if (item.getId() == id)
                data.remove(item);
    }

    public void setDataLoadedState(List<CurrentConditionModel> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public void setPlaceUndoState(CurrentConditionModel currentCondition, int pos) {
        data.add(pos, currentCondition);
    }

    public void setPullToRefreshState(boolean show) {
        if (show)
            currentState = STATE_PULL_TO_REFRESH;
        else currentState = 0;
    }

    public List<CurrentConditionModel> getData() {
        return data;
    }

    public Set<Long> getLoadingPlaces() {
        return loadingPlaces;
    }

    public int getCurrentState() {
        return currentState;
    }
}
