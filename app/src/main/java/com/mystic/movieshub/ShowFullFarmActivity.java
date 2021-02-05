package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class ShowFullFarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_farm);
        ImageView imageView = findViewById(R.id.imageView5);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Uri uri = (Uri) getIntent().getSerializableExtra("PHOTO");
        Objects.requireNonNull(getSupportActionBar()).setTitle(uri.toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }
}