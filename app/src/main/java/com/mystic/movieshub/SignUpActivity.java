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
    private TextView btnSignIn,picName;
    private Button btnSignUp;
    private MaterialButton cho;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private Spinner spinner;
    private StorageTask mUploads;
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
                if (mUploads != null && mUploads.isInProgress()) {
                    Toast.makeText(SignUpActivity.this, "An upload is ongoing", Toast.LENGTH_LONG).show();
                }else{
                    uploadUserToBase();
                }

            }
        });

    }

    private void uploadUserToBase() {
        if(mImageUri != null ){

            prog.setVisibility(View.VISIBLE);
            //String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
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
                                    String password = editTextPassword.getText().toString().trim();
                                    String phone = editTextPhone.getText().toString().trim();
                                    String fullname = editTextFullName.getText().toString().trim();
                                    String role = spinner.getSelectedItem().toString();;
                                    addToDatabase(email,password,phone,fullname,uri,role);

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


    private void addToDatabase(String email, String password,String phone,String fullname,Uri uri,String role) {
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
        AgroAppRepo.getInstanceOfAgroApp().signUp(email,password,this,prog,phone,fullname,uri,role,user);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_PIC && resultCode == Activity.RESULT_OK && data != null  && data.getData() != null){
            mImageUri = data.getData();
            picName.setText(mImageUri.toString());
        }

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
        cho = findViewById(R.id.choPic);
        picName = findViewById(R.id.picname);
        spinner = findViewById(R.id.spinnini);
        //Define TextInputLayout
        /*textInputLayoutEmail = findViewById(R.id.textInputLayout2);
        textInputLayoutFullName = findViewById(R.id.textInputLayout5);
        textInputLayoutPassword = findViewById(R.id.textInputLayout4);
        textInputLayoutUsername = findViewById(R.id.textInputLayout);
        textInputLayoutPhone = findViewById(R.id.textInputLayout3);*/


    }



}