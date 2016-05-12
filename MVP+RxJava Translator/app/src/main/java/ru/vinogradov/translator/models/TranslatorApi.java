package ru.vinogradov.translator.models;


import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface TranslatorApi {

    String END = "https://translate.yandex.net/api/v1.5/tr.json/translate/";

    @POST("/api/v1.5/tr.json/translate")
    Observable<TranslatorResponse> getTranslate(@Query("key") String apiKey, @Query("text") String text, @Query("lang") String lang);
}
