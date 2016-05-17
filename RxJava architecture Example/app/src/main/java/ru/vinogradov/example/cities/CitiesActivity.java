package ru.vinogradov.example.cities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import ru.vinogradov.example.R;
import ru.vinogradov.example.data.City;
import ru.vinogradov.example.data.RxCitiesLoader;
import rx.Observable;

public class CitiesActivity extends AppCompatActivity implements CitiesContract.View {

    CitiesContract.Presenter citiesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RxCitiesLoader citiesLoader = new RxCitiesLoader(this);

        citiesPresenter = new CitiesPresenter(this, citiesLoader, getSupportLoaderManager());
        citiesPresenter.loadCities();

    }

    @Override
    public void showProgressBar() {
        Log.d("MYAPP", "showProgressBar");
    }

    @Override
    public void setData(List<City> cities) {
        Observable.from(cities)
                .subscribe(city -> Log.d("MYAPP", city.getName()));
    }


    @Override
    public void setErrorMessage() {
        Log.d("MYAPP", "error");
    }
}
