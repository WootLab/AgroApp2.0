package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    private TextView txt;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        txt = findViewById(R.id.noInvestor);
        linearLayout = findViewById(R.id.eligibleView);
        User statusUser = (User)getIntent().getSerializableExtra("Status");


        if(statusUser.getRequirements().isEligible()){
            txt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            txt.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }





    }
}