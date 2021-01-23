package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {




    private EditText ediTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextFullName;

    private TextView btnSignIn;
    private Button btnSignUp;

    /*private TextInputLayout
            textInputLayoutUsername,
            textInputLayoutPassword,
            textInputLayoutEmail,
            textInputLayoutPhone,
            textInputLayoutFullName;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        defineViews();




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String username = ediTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String fullname = editTextFullName.getText().toString().trim();
                //addToDatabase(email, password,username,phone,fullname);
            }
        });

    }


    public void defineViews(){
        //Button definition
        btnSignUp = findViewById(R.id.signUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        //EditText Definition
        editTextPassword = findViewById(R.id.password);
        ediTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phoneEd);
        editTextFullName = findViewById(R.id.fullname);

        //Define TextInputLayout
        /*textInputLayoutEmail = findViewById(R.id.textInputLayout2);
        textInputLayoutFullName = findViewById(R.id.textInputLayout5);
        textInputLayoutPassword = findViewById(R.id.textInputLayout4);
        textInputLayoutUsername = findViewById(R.id.textInputLayout);
        textInputLayoutPhone = findViewById(R.id.textInputLayout3);*/


    }
}