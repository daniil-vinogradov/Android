package com.vinogradov.myweather.database;

import android.graphics.drawable.Drawable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.realm.RealmObject;

/**
 * @author d.vinogradov
 */
public class CitiesHelper {

    public static class WeatherDeserializer implements JsonDeserializer<City> {

        @Override
        public City deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject city = new JsonObject();
            JsonElement current_condition = json.getAsJsonObject().get("data").getAsJsonObject().get("current_condition").getAsJsonArray().get(0).getAsJsonObject();
            city.addProperty("FeelsLikeC", current_condition.getAsJsonObject().get("FeelsLikeC").getAsString());
            city.addProperty("temp_C", current_condition.getAsJsonObject().get("temp_C").getAsString());
            city.addProperty("weatherCode", current_condition.getAsJsonObject().get("weatherCode").getAsString());
            JsonArray weatherFromAPI = json.getAsJsonObject().get("data").getAsJsonObject().get("weather").getAsJsonArray();
            JsonArray weather = new JsonArray();
            for (int i = 0; i < weatherFromAPI.size(); i++) {
                JsonObject day = new JsonObject();
                day.addProperty("sunrise", weatherFromAPI.get(i).getAsJsonObject().get("astronomy").getAsJsonArray().get(0).getAsJsonObject().get("sunrise").getAsString());
                day.addProperty("sunset", weatherFromAPI.get(i).getAsJsonObject().get("astronomy").getAsJsonArray().get(0).getAsJsonObject().get("sunset").getAsString());
                day.addProperty("date", weatherFromAPI.get(i).getAsJsonObject().get("date").getAsString());
                day.addProperty("maxtempC", weatherFromAPI.get(i).getAsJsonObject().get("maxtempC").getAsString());
                day.addProperty("mintempC", weatherFromAPI.get(i).getAsJsonObject().get("mintempC").getAsString());
                day.addProperty("weatherCode", weatherFromAPI.get(i).getAsJsonObject().get("hourly").getAsJsonArray().get(0).getAsJsonObject().get("weatherCode").getAsString());
                day.addProperty("value", weatherFromAPI.get(i).getAsJsonObject().get("hourly").getAsJsonArray().get(0).getAsJsonObject().get("weatherDesc").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString());
                weather.add(day);
            }
            city.add("weather", weather);
            return new Gson().fromJson(city, City.class);

        }
    }

    public static class WeatherSerializer implements JsonSerializer<City> {

        @Override
        public JsonElement serialize(City src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.addProperty("FeelsLikeC", src.getFeelsLikeC());
            result.addProperty("temp_C", src.getTemp_C());
            return result;
        }
    }

    public static Gson getGson() throws ClassNotFoundException {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(City.class, new WeatherSerializer())
                .registerTypeAdapter(City.class, new WeatherDeserializer())
                .create();
    }

}
