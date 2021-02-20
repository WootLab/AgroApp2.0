package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class FundFarmerActivity extends AppCompatActivity {

    private EditText amount;
    private EditText message;
    private Button credit, debit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_farmer);
        AgroAppRepo agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        defineViews();
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Credit Or Debit Farmers Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        User user = (User) getIntent().getSerializableExtra("FundUser");

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amou = amount.getText().toString().trim();
                String mess = message.getText().toString().trim();
                double value = Double.parseDouble(amou);
                agroAppRepo.addMoney(user,value,mess,FundFarmerActivity.this);
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amou = amount.getText().toString().trim();
                String mess = message.getText().toString().trim();
                double value = Double.parseDouble(amou);
                agroAppRepo.removeMoney(user,value,mess,FundFarmerActivity.this);
            }
        });

    }


    public void defineViews(){
        amount = findViewById(R.id.Amount);
        message = findViewById(R.id.message);
        credit = findViewById(R.id.credit);
        debit = findViewById(R.id.debit);
    }
}