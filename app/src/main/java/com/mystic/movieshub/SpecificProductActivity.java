package com.mystic.movieshub;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SpecificProductActivity extends AppCompatActivity {

    public static final String PRODUCT = "Product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_product);
    }
}