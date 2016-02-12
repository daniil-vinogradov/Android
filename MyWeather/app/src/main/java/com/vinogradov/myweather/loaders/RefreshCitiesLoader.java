package com.vinogradov.myweather.loaders;

import android.content.Context;

import com.squareup.okhttp.ResponseBody;
import com.vinogradov.myweather.database.CitiesHelper;
import com.vinogradov.myweather.database.City;
import com.vinogradov.myweather.rest.ApiFactory;
import com.vinogradov.myweather.rest.RestAPIs;
import com.vinogradov.myweather.rest.response.BaseResponse;
import com.vinogradov.myweather.rest.response.RequestResult;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Response;

/**
 * @author d.vinogradov
 */
public class RefreshCitiesLoader extends BaseLoader {

    public RefreshCitiesLoader(Context context) {
        super(context);
    }

    @Override
    protected BaseResponse apiCall() throws IOException, ClassNotFoundException {

        Realm realm = Realm.getInstance(getContext());
        RealmResults<City> results = realm.where(City.class).findAll();
        realm.beginTransaction();
        for (int i = 0; i < results.size(); i++) {
            RestAPIs.WeatherAPI weatherAPI = ApiFactory.getWeatherAPI("https://api.worldweatheronline.com/");
            Call<ResponseBody> cityWeather = weatherAPI.getWeather(results.get(i).getCoord());
            Response<ResponseBody> responseWeather = cityWeather.execute();
            City refreshedCity = CitiesHelper.getGson().fromJson(responseWeather.body().string(), City.class);
            results.get(i).setFeelsLikeC(refreshedCity.getFeelsLikeC());
            results.get(i).setTemp_C(refreshedCity.getTemp_C());
            results.get(i).setWeatherCode(refreshedCity.getWeatherCode());
            results.get(i).getWeatherForDays().clear();
            for (int k = 0; k < refreshedCity.getWeatherForDays().size(); k++) {
                results.get(i).getWeatherForDays().add(refreshedCity.getWeatherForDays().get(k));
            }
        }
        realm.commitTransaction();
        realm.close();

        return new BaseResponse(getContext())
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(null);
    }

}
