package com.mystic.movieshub;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class ApplyForLoanActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 4;
    private EditText farmdescription,address,postalcode;
    private Spinner agrictype,state, localGovernment;
    private Button chooseMultipleImages,butApply, cancel;
    private List<Uri> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_loan);
        defineViews();
        imageList = new ArrayList<>();
        butApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getUid();
                String description = farmdescription.getText().toString().trim();
                String stateStr = state.getSelectedItem().toString();
                String locgov = localGovernment.getSelectedItem().toString();
                String addres = address.getText().toString().trim();
                String postal = postalcode.getText().toString().trim();
                String agric = agrictype.getSelectedItem().toString();
                AgroAppRepo.getInstanceOfAgroApp().applyForLoans(userId,"",description,imageList,agric,ApplyForLoanActivity.this);

            }
        });


        chooseMultipleImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMultiple();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getClipData() != null){
            int count = data.getClipData().getItemCount();
            int currentImageSelect = 0;
            while (currentImageSelect < count){
                Uri imageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                imageList.add(imageUri);
                currentImageSelect++;
            }

        }else{
            Toast.makeText(ApplyForLoanActivity.this,"please select multiple pictures",Toast.LENGTH_SHORT).show();
        }
    }


    private void chooseMultiple(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,PICK_IMAGE);
    }





    /*public void uploadFarmsToLoan(){
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

    }*/

    public void defineViews(){
        farmdescription = findViewById(R.id.descript);
        state = findViewById(R.id.spinnerState);
        localGovernment = findViewById(R.id.spinnerlocal);
        address = findViewById(R.id.editTextTextAddress);
        postalcode = findViewById(R.id.editTextTextPostal);
        chooseMultipleImages = findViewById(R.id.button9);
        agrictype = findViewById(R.id.spinnerAgri);
        butApply = findViewById(R.id.button11);
        cancel = findViewById(R.id.button12);
    }
}