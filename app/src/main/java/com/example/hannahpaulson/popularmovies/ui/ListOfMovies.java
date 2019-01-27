package com.example.hannahpaulson.popularmovies.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hannahpaulson.popularmovies.R;
import com.example.hannahpaulson.popularmovies.data.MovieAPI;
import com.example.hannahpaulson.popularmovies.data.RestClient;
import com.example.hannahpaulson.popularmovies.data.datapojo.Movie;
import com.example.hannahpaulson.popularmovies.data.datapojo.Results;
import com.example.hannahpaulson.popularmovies.ui.adapters.ListOfPosterAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfMovies extends AppCompatActivity {
    private static final String TAG = ListOfMovies.class.getName();
    ListOfPosterAdapter listOfPosterAdapter;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_movies);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.gridview);

        if (!isNetworkAvailable()) {
            Toast.makeText(ListOfMovies.this.getApplicationContext(), "You are offline", Toast.LENGTH_SHORT).show();
        } else {
            getPopularMovies();
        }
    }

    private void getPopularMovies(){
        Call<Results> call = RestClient.get().popularMovies();
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
                    populateGridView(movies);
                } else {
                    Log.e(TAG,response.errorBody()+"");
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    private void getHighestRated(){
        Call<Results> call = RestClient.get().highestRated();
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if(response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
                    populateGridView(movies);
                } else {
                    Log.e(TAG,response.errorBody()+"");
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    private void populateGridView(List<Movie> movies) {
        listOfPosterAdapter = new ListOfPosterAdapter(ListOfMovies.this, movies);
        gridView.setAdapter(listOfPosterAdapter);
        listOfPosterAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            getPopularMovies();
            return true;
        }
        if (id == R.id.action_top_rated) {
            getHighestRated();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ListOfMovies.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
