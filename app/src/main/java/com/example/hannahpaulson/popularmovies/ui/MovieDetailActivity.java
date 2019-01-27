package com.example.hannahpaulson.popularmovies.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hannahpaulson.popularmovies.R;
import com.example.hannahpaulson.popularmovies.data.RestClient;
import com.example.hannahpaulson.popularmovies.data.datapojo.Movie;
import com.example.hannahpaulson.popularmovies.data.datapojo.MovieDetails;
import com.example.hannahpaulson.popularmovies.data.datapojo.Results;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private String TAG = MovieDetailActivity.class.getName();
    private final String MOVIE_ID = "MOVIEID";
    private String movieID;
    private ImageView posterImage;
    private TextView releaseDate;
    private TextView ratingValue;
    private TextView summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        posterImage = findViewById(R.id.movie_poster);
        releaseDate = findViewById(R.id.movie_release_date);
        ratingValue = findViewById(R.id.movie_rate_text);
        summaryText = findViewById(R.id.movie_summary);

        Intent intent = this.getIntent();
        movieID = intent.getStringExtra(MOVIE_ID);
        getDetails(movieID);
    }

    private void getDetails(String movieID) {
        Call<MovieDetails> call = RestClient.get().movieDetails(movieID);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful()) {
                    MovieDetails details = response.body();
                    displayMovieDetails(details);
                } else {
                    Log.e(TAG, response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private void displayMovieDetails(MovieDetails details) {
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + details.getPosterPath())
                .into(posterImage);
        releaseDate.setText("Release Date: " + details.getReleaseDate());
        ratingValue.setText("User Rating: " + details.getVoteAverage().toString());
        summaryText.setText(details.getOverview());
    }
}
