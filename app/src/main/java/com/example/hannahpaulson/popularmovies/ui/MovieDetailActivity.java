package com.example.hannahpaulson.popularmovies.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hannahpaulson.popularmovies.R;
import com.example.hannahpaulson.popularmovies.api.RestClient;
import com.example.hannahpaulson.popularmovies.data.MovieDetails;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private String TAG = MovieDetailActivity.class.getName();

    private final String MOVIE_ID = "MOVIEID";
    private String movieID;

    private ImageView posterImage;
    private ImageView bannerImage;

    private TextView movieTitle;
    private TextView movieTagLine;
    private TextView releaseDate;
    private TextView summaryText;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        posterImage = findViewById(R.id.movie_poster);
        bannerImage = findViewById(R.id.banner);

        movieTagLine = findViewById(R.id.movie_tag_line);
        releaseDate = findViewById(R.id.movie_release_date);
        summaryText = findViewById(R.id.movie_summary);
        ratingBar = findViewById(R.id.rating_bar);
        movieTitle = findViewById(R.id.movie_title);

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
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayMovieDetails(MovieDetails details) {
        setTitle(details.getTitle());
        releaseDate.setText("Release Date: " + details.getReleaseDate());
        movieTitle.setText(details.getTitle());
        movieTagLine.setText(details.getTagline());
        ratingBar.setNumStars(10);
        ratingBar.setRating(details.getVoteAverage());
        summaryText.setText(details.getOverview());

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + details.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(bannerImage);

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w500/" + details.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(posterImage);
    }
}
