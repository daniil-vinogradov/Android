package ru.vinogradov.example.cities;


import java.util.List;

import ru.vinogradov.example.data.City;

public interface CitiesContract {

    interface View {
        void showProgressBar();
        void setData(List<City> cities);
        void setErrorMessage();
    }

    interface Presenter {
        void loadCities();
    }

}
