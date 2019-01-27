package com.example.hannahpaulson.popularmovies.data;

import com.example.hannahpaulson.popularmovies.data.datapojo.MovieDetails;
import com.example.hannahpaulson.popularmovies.data.datapojo.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {
    @GET("movie/popular?api_key=")
    Call<Results> popularMovies();

    @GET("movie/top_rated?api_key=")
    Call<Results> highestRated();

    @GET("movie/{id}?api_key=")
    Call<MovieDetails> movieDetails(@Path("id") String id);
}
