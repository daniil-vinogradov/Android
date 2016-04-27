package ru.vinogradov.translator.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import ru.vinogradov.translator.R;
import ru.vinogradov.translator.api.TranslatorAPI;
import ru.vinogradov.translator.api.TranslatorResponse;


public class TranslatorLoader extends AsyncTaskLoader<String> {

    Context context;
    String text;
    String lang;

    public TranslatorLoader(Context context, String text, String lang) {
        super(context);
        this.context = context;
        this.text = text;
        this.lang = lang;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public String loadInBackground() {

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/translate/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        TranslatorAPI service = retrofit.create(TranslatorAPI.class);
        Call<TranslatorResponse> call = service.getTranslate(context.getResources().getString(R.string.api_key), text, lang);

        TranslatorResponse response = null;

        try {
            response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (response != null && response.getCode() == 200)
            return response.getText().get(0);
        else return null;
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}
