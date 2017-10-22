package com.udacity.gradle.builditbigger.retrievaltool.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Retrofit service for public joke service.
 */
public interface PublicJokeService {

    @GET("/")
    @Headers("Accept: application/json")
    Call<Joke> get();
}
