package com.vinogradov.myweather.ui;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.vinogradov.myweather.R;
import com.vinogradov.myweather.adapters.DayAdapter;
import com.vinogradov.myweather.database.City;

import io.realm.Realm;

/**
 * @author d.vinogradov
 */
public class WeatherActivity extends AppCompatActivity {

    RecyclerView days;
    ImageView image;
    Realm realm;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));

        realm = Realm.getInstance(this);
        City city = realm.where(City.class).equalTo("coord", getIntent().getStringExtra("coord")).findFirst();

        days = (RecyclerView) findViewById(R.id.days);
        days.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        days.setHasFixedSize(true);
        days.setLayoutManager(new LinearLayoutManager(this));
        days.setAdapter(new DayAdapter(city.getWeatherForDays()));

        image = (ImageView) findViewById(R.id.cityImage);
        image.setImageBitmap(BitmapFactory.decodeFile(city.getImage()));

        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
