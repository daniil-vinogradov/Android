package ru.vinogradov.translator.models;



import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vinogradov.translator.R;
import rx.Observable;


public class ModelImpl implements IModel {

    String API_KEY = "trnsl.1.1.20160425T084208Z.53b078e2300dd613.4d0e8c0899240f4d55ded63c9eb035eaee10b923";

    @Override
    public Observable<TranslatorResponse> getResponse(String text, String lang) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslatorApi.END)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TranslatorApi translatorApi = retrofit.create(TranslatorApi.class);
        return translatorApi.getTranslate(API_KEY,text,lang);
    }

}
