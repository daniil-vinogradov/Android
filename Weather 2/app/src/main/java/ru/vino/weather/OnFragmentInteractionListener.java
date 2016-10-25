package ru.vino.weather;


import android.view.View;

import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public interface OnFragmentInteractionListener {

    void openPlaceDetail(View card, CurrentConditionModel currentCondition);

    void openSearch(int cx, int cy, int startRadius);

    void back();

    void showFab();

    void loadNewPlace(AutocompleteResponseModel place);

}
