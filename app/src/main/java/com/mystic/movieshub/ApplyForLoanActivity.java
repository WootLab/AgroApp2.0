package com.mystic.movieshub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ApplyForLoanActivity extends AppCompatActivity {

    EditText farmdescription,address,postalcode;
    Spinner state,localgovernment, agrictype;
    Button imageone,imagetwo,imagethree,butApply, cancel;
    private List<Uri> imageUri;

   // private ProgressDialog progressDialog;
    private Uri urione, uritwo, urithree;


    public static final int IMAGEONE = 1;
    public static final int IMAGETWO = 2;
    public static final int IMAGETHREE = 3;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_loan);
        defineViews();
        imageUri = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference("FARMIMAGES");
        butApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = FirebaseAuth.getInstance().getUid();
                String description = farmdescription.getText().toString().trim();
                String addres = address.getText().toString().trim();
                String postal = postalcode.getText().toString().trim();
                String agric = agrictype.getSelectedItem().toString();

                AgroAppRepo.getInstanceOfAgroApp().applyForLoans(userId,"",description,imageUri,agric,ApplyForLoanActivity.this);

            }
        });


        imageone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(IMAGEONE);

            }
        });

        imagetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(IMAGETWO);

            }
        });

        imagethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(IMAGETHREE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGEONE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            urione = data.getData();
        }else if(requestCode == IMAGETWO && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            uritwo = data.getData();
        }else if(requestCode == IMAGETHREE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            urithree = data.getData();
        }else{
            Toast.makeText(this,"Nothing was selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImage(int image){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image);
    }


    private String getFileExtension(Uri uri){
        // This was just a test
        ContextWrapper rapper = new ContextWrapper(this);
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }




    public void uploadFarmsToLoan(){
        if(urione != null && uritwo != null && urithree != null){
            imageUri.add(urione);
            imageUri.add(uritwo);
            imageUri.add(urithree);

            int imageListSize = imageUri.size();
            List<Task<Uri>> uploadedImageUrlTasks = new ArrayList<>(imageListSize);
            StorageReference filerefone = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(urione));
            StorageReference filereftwo = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(uritwo));
            StorageReference filerefthree = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(urithree));

            for(Uri imageri : imageUri){

                final String imageFilename = imageri.getLastPathSegment();
                final StorageReference imageRef = mStorageRef.child(imageFilename); // Warning: potential for collisions/overwrite

                UploadTask currentUploadTask = imageRef.putFile(imageri);

                Task<Uri> currentUrlTask = currentUploadTask
                        .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Log.d("upload.onClick()", "Upload for \"" + imageFilename + "\" failed!");
                                    throw task.getException(); // rethrow any errors
                                }

                                Log.d("upload.onClick()", "Upload for \"" + imageFilename + "\" finished. Fetching download URL...");
                                return imageRef.getDownloadUrl();

                            }
                        })
                        .continueWithTask(new Continuation<Uri, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<Uri> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Log.d("upload.onClick()", "Could not get download URL for \"" + imageFilename + "\"!");
                                    throw task.getException(); // rethrow any errors
                                }

                                Log.d("upload.onClick()", "Download URL for \"" + imageFilename + "\" is \"" + task.getResult() + "\".");
                                return null;
                               // return task.getResult();
                            }
                        });
                uploadedImageUrlTasks.add(currentUrlTask);
            }


        }

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