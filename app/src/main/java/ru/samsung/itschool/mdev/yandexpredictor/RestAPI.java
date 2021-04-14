package ru.samsung.itschool.mdev.yandexpredictor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestAPI {
    @GET("api/v1/predict.json/complete")
    Call<Model> predict(@Query("key") String key, @Query("q") String q, @Query("lang") String lang);
}