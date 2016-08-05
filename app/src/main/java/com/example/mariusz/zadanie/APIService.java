package com.example.mariusz.zadanie;

import com.example.mariusz.zadanie.Database.MyLongUrl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mariusz on 03.08.16.
 */
public interface APIService {
    @POST("/urlshortener/v1/url")
    Call<MyLongUrl> getShortenedURL(@Query("key") String key, @Body MyLongUrl longUrl );
}
