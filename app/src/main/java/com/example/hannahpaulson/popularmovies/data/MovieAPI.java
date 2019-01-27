package com.example.hannahpaulson.popularmovies.data;

import com.example.hannahpaulson.popularmovies.data.datapojo.Results;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {
    @GET("movie/popular?api_key=")
    Call<Results> popularMovies();
}
