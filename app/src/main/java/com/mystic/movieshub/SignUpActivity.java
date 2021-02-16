package com.mystic.movieshub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {


    private static  final int PROFILE_PIC = 2;

    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextFullName;
    private TextView btnSignIn;
    private Button btnSignUp;
    private Spinner spinner;

    private User user;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        prog = findViewById(R.id.progressBar);
        user = (User) getIntent().getSerializableExtra("QualUser");

        defineViews();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    uploadUserToBase();
            }
        });

    }

    private void uploadUserToBase() {
        prog.setVisibility(View.VISIBLE);
        Toast.makeText(SignUpActivity.this,"successfully uploaded",Toast.LENGTH_SHORT).show();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();
        String role = spinner.getSelectedItem().toString();;
        addToDatabase(email,password,phone,fullname,role);
    }


    private void addToDatabase(String email, String password,String phone,String fullname,String role) {
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(fullname.isEmpty()){
            editTextFullName.setError("Username is required");
            editTextFullName.requestFocus();
            return;
        }


        if(password.length() < 6){
            editTextPassword.setError("password can not be less than 6");
            editTextPassword.requestFocus();
            return;
        }

        AccSharedPref.setStoredEmail(this,email);
        //This will trigger what adds the user to the database
        AgroAppRepo.getInstanceOfAgroApp().signUp(email,password,this,prog,phone,fullname,role,user);
    }

    public void defineViews(){
        //Button definition
        btnSignUp = findViewById(R.id.signUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        //EditText Definition
        editTextPassword = findViewById(R.id.password);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phoneEd);
        editTextFullName = findViewById(R.id.fullname);
        spinner = findViewById(R.id.spinnini);
        //Define TextInputLayout
        /*textInputLayoutEmail = findViewById(R.id.textInputLayout2);
        textInputLayoutFullName = findViewById(R.id.textInputLayout5);
        textInputLayoutPassword = findViewById(R.id.textInputLayout4);
        textInputLayoutUsername = findViewById(R.id.textInputLayout);
        textInputLayoutPhone = findViewById(R.id.textInputLayout3);*/


    }



}