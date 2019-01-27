package com.example.hannahpaulson.popularmovies.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.hannahpaulson.popularmovies.R;
import com.example.hannahpaulson.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListOfPosterAdapter extends ArrayAdapter {

    private Context context;
    private List<Movie> movies;

    public ListOfPosterAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = movies.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.linearlayout_poster, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w780/" + movie.getPosterPath())
                .into(imageView);

        return convertView;
    }
}
