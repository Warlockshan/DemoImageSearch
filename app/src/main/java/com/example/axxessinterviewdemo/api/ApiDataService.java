package com.example.axxessinterviewdemo.api;

import com.example.axxessinterviewdemo.model.Imagesmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiDataService {
    @Retry
    @GET("search/1")
    Call<Imagesmodel> getImagesBySearch(@Query("q") String query, @Header("Authorization") String header);

}
