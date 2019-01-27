package com.example.hannahpaulson.popularmovies.api;

import com.example.hannahpaulson.popularmovies.api.MovieAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static MovieAPI movieAPI;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    static {
        setupRestClient();
    }

    public RestClient() {
    }

    public static MovieAPI get() {
        return movieAPI;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        movieAPI = retrofit.create(MovieAPI.class);
    }
}