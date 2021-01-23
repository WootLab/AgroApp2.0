package com.mystic.movieshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {


    private static  final int PROFILE_PIC = 2;


    private EditText ediTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextFullName;

    private TextView btnSignIn;
    private Button btnSignUp;
    private MaterialButton cho;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploads;

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
        mStorageRef = FirebaseStorage.getInstance().getReference("USERSPIC");
        defineViews();




        cho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadUserToBase();


                String email = editTextEmail.getText().toString().trim();
                String username = ediTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String fullname = editTextFullName.getText().toString().trim();
                //addToDatabase(email, password,username,phone,fullname);
            }
        });

    }

    private void uploadUserToBase() {

        if(mImageUri != null ){
            final StorageReference fileref = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploads = fileref.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //bar.setProgress(0);
                                }
                            },500);



                            fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(SignUpActivity.this,"successfully uploaded",Toast.LENGTH_SHORT).show();
                                    String email = editTextEmail.getText().toString().trim();
                                    String username = ediTextUsername.getText().toString().trim();
                                    String password = editTextPassword.getText().toString().trim();
                                    String phone = editTextPhone.getText().toString().trim();
                                    String fullname = editTextFullName.getText().toString().trim();

                                    addToDatabase(email,password,phone,fullname);

                                }
                            });

                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        }
                    });

        }



    }


    private void addToDatabase(String email, String password,String phone,String fullname) {
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(fullname.isEmpty()){
            ediTextUsername.setError("Username is required");
            ediTextUsername.requestFocus();
            return;
        }


        if(password.length() < 6){
            editTextPassword.setError("password can not be less than 6");
            editTextPassword.requestFocus();
            return;
        }

        AccSharedPref.setStoredEmail(this,email);
        //This will trigger what adds the user to the database
        //ProgressBar prog = findViewById(R.id.progressBar);
        //repoInstance.signUp(email,password,this,prog,phone,username,fullname);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PROFILE_PIC);
    }

    private String getFileExtension(Uri uri){
        // This was just a test
        ContextWrapper rapper = new ContextWrapper(this);
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
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
        cho = findViewById(R.id.choPic);

        //Define TextInputLayout
        /*textInputLayoutEmail = findViewById(R.id.textInputLayout2);
        textInputLayoutFullName = findViewById(R.id.textInputLayout5);
        textInputLayoutPassword = findViewById(R.id.textInputLayout4);
        textInputLayoutUsername = findViewById(R.id.textInputLayout);
        textInputLayoutPhone = findViewById(R.id.textInputLayout3);*/


    }



}