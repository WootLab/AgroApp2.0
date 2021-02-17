package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class StatusActivity extends AppCompatActivity {

    private TextView txt, valance;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Account Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        valance = findViewById(R.id.valance);
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

        valance.setText(Double.toString(statusUser.getBalance()));







    }
}