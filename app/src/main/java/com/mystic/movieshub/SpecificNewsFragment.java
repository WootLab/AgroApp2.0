package com.mystic.movieshub;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.Objects;


public class SpecificNewsFragment extends Fragment {
    /*ImageView topImage,real;
    TextView TV_movietitle, TV_moviestory,TV_date;
    RatingBar bar;*/
    AgriNews movie;

    public SpecificNewsFragment() {
        // Required empty public constructor
    }

    public static SpecificNewsFragment newInstance(AgriNews agriNews){
        Bundle bundle = new Bundle();
        bundle.putSerializable(SpecificNewsActivity.MOVIE_OBJECT,agriNews);
        SpecificNewsFragment fragment = new SpecificNewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        movie = (AgriNews) getArguments().getSerializable(SpecificNewsActivity.MOVIE_OBJECT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        /*bar = view.findViewById(R.id.ratingBar2);
        topImage = view.findViewById(R.id.toolimage);
        real = view.findViewById(R.id.imagetosee);
        TV_movietitle = view.findViewById(R.id.movtitle);
        TV_moviestory = view.findViewById(R.id.storymode);
        TV_date = view.findViewById(R.id.textdate);*/




        /*Glide.with(Objects.requireNonNull(getActivity()))
                .asDrawable()
                .load(Uri.parse(movie.getImage()))
                .placeholder(R.drawable.camera)
                .into(topImage);

        Glide.with(Objects.requireNonNull(getActivity()))
                .asBitmap()
                .load(Uri.parse(movie.getPosterImages()))
                .placeholder(R.drawable.camera)
                .into(real);
        TV_movietitle.setText(movie.getName());
        TV_moviestory.setText(movie.getDescription());
        TV_date.setText(String.valueOf(movie.getYear()));
        bar.setRating(movie.getRating());*/

        //Toolbar toolbar = view.findViewById(R.id.toolbarsecond);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.getSupportActionBar();


        return view;
    }
}