package com.vinogradov.myweather.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;
import com.vinogradov.myweather.database.CitiesHelper;
import com.vinogradov.myweather.database.City;
import com.vinogradov.myweather.rest.ApiFactory;
import com.vinogradov.myweather.rest.RestAPIs;
import com.vinogradov.myweather.rest.response.BaseResponse;
import com.vinogradov.myweather.rest.response.NewCityResponse;
import com.vinogradov.myweather.rest.response.RequestResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

/**
 * @author d.vinogradov
 */
public class NewCityLoader extends BaseLoader {

    private String name;

    public NewCityLoader(Context context, String name) {
        super(context);
        this.name = name;
    }

    @Override
    protected BaseResponse apiCall() throws IOException, ClassNotFoundException {
        RestAPIs.GeocodeAPI geocodeAPI = ApiFactory.getGeocodeAPI("https://maps.googleapis.com");
        Call<ResponseBody> cityCoord = geocodeAPI.getCityLatLng(name);
        Response<ResponseBody> responseCoord = cityCoord.execute();
        JSONObject json;
        JSONObject location;
        String latlng = null;
        try {
            json = new JSONObject(responseCoord.body().string());
            location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            latlng = location.getString("lat") + "," + location.getString("lng");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        RestAPIs.WeatherAPI weatherAPI = ApiFactory.getWeatherAPI("https://api.worldweatheronline.com/");
        Call<ResponseBody> cityWeather = weatherAPI.getWeather(latlng);
        Response<ResponseBody> responseWeather = cityWeather.execute();
        City city = CitiesHelper.getGson().fromJson(responseWeather.body().string(), City.class);

        RestAPIs.SearchAPI searchAPI = ApiFactory.getSearchAPI("https://www.googleapis.com/");
        Call<ResponseBody> cityImage = searchAPI.getImage(name);
        Response<ResponseBody> responseImage = cityImage.execute();
        String imageLink = null;
        try {
            json = new JSONObject(responseImage.body().string());
            imageLink = json.getJSONArray("items").getJSONObject(0).getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bitmap image = Picasso.with(getContext()).load("http://" + Uri.parse(imageLink).getHost() + Uri.encode(Uri.parse(imageLink).getPath()).replace("%2F", "/")).get();

        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/CitiesPhotos";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, "city" + latlng + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();

        city.setImage(file.getAbsolutePath());
        city.setName(name);
        city.setCoord(latlng);
        return new NewCityResponse(getContext())
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(city);
    }


}
