package com.mystic.movieshub;

import androidx.fragment.app.Fragment;

public class SpecificNewsActivity extends SingleFragmentActivity {

    public static final String MOVIE_OBJECT = "movie";
    @Override
    protected Fragment createFragment() {
        AgriNews movie = (AgriNews) getIntent().getSerializableExtra(MOVIE_OBJECT);
      return SpecificNewsFragment.newInstance(movie);
    }

}