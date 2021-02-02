package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class ApplyForLoanActivity extends AppCompatActivity {

    EditText farmdescription,address,postalcode;
    Spinner state,localgovernment, agrictype;
    Button imageone,imagetwo,imagethree,butApply, cancel;


    private Uri urione, uritwo, urithree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_loan);
        defineViews();


        butApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getUid();
                String description = farmdescription.getText().toString().trim();
                String addres = address.getText().toString().trim();
                String postal = postalcode.getText().toString().trim();
                String agric = agrictype.getSelectedItem().toString();

                //AgroAppRepo.getInstanceOfAgroApp().applyForLoans(userId,"",description,{"","",""},agric,ApplyForLoanActivity.class);

            }
        });


        imageone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imagetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imagethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void defineViews(){
        farmdescription = findViewById(R.id.descript);
        state = findViewById(R.id.spinnerState);
        localgovernment = findViewById(R.id.spinnerlocal);
        address = findViewById(R.id.editTextTextAddress);
        postalcode = findViewById(R.id.editTextTextPostal);
        imageone = findViewById(R.id.button9);
        imagetwo = findViewById(R.id.button10);
        agrictype = findViewById(R.id.spinnerAgri);
        imagethree = findViewById(R.id.button8);
        butApply = findViewById(R.id.button11);
        cancel = findViewById(R.id.button12);
    }
}