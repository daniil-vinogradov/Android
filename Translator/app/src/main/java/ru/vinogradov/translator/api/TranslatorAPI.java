package ru.vinogradov.translator.api;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslatorAPI {

    @POST("/api/v1.5/tr.json/translate")
    Call<TranslatorResponse> getTranslate(@Query("key") String apiKey, @Query("text") String text, @Query("lang") String lang);

}
