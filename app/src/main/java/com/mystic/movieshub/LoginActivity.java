package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputEditEmail;
    private TextInputEditText inputEditPassword;
    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        inputEditEmail = findViewById(R.id.logInEmail);
        inputEditPassword = findViewById(R.id.loginInPassword);
        Button logbtn = findViewById(R.id.Login);
        TextView ntmb = findViewById(R.id.donthaveccount);
        //firebase = FirebaseAuth.getInstance();

        String emailPref = AccSharedPref.getStoredEmail(this);
        inputEditEmail.setText(emailPref);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.login();
            }
        });
        ntmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.moveToSignUp();
            }
        });

    }

    private void moveToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpPage.class);
        startActivity(intent);
        finish();
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        new FirebaseAuth.AuthStateListener(){
          /*  final FirebaseUser firebaseUser = firebase.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "you are already loggd in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "You are loggeg out", Toast.LENGTH_LONG).show();
                }
            }


        };
    }*/

    private void login(){

        String email = Objects.requireNonNull(inputEditEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(inputEditPassword.getText()).toString().trim();
        AccSharedPref.setStoredEmail(this,email);
        //ProgressBar bar = findViewById(R.id.progressBar2);
       // FarmRepository.getFarmRepositoryInstance(getApplicationContext()).login(email, password,this,bar,editTextName,editTextPassword);

    }
}
