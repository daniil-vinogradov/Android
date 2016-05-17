package ru.vinogradov.example.cities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import ru.vinogradov.example.R;
import ru.vinogradov.example.data.City;
import ru.vinogradov.example.data.RxCitiesLoader;
import rx.Observable;

public class CitiesActivity extends AppCompatActivity implements CitiesContract.View {

    CitiesContract.Presenter citiesPresenter;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    CitiesAdapter citiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        citiesAdapter = new CitiesAdapter();
        recyclerView.setAdapter(citiesAdapter);

        RxCitiesLoader citiesLoader = new RxCitiesLoader(this);

        citiesPresenter = new CitiesPresenter(this, citiesLoader, getSupportLoaderManager());
        citiesPresenter.loadCities();

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(List<City> cities) {
        progressBar.setVisibility(View.GONE);
        citiesAdapter.setData(cities);
    }


    @Override
    public void setErrorMessage() {
        Log.d("MYAPP", "error");
    }
}
