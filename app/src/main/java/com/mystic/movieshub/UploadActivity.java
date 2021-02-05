package com.mystic.movieshub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UploadActivity extends AppCompatActivity {

    private EditText edtTitle,edtDesc;
    private TextView fileName;
    private AgroAppRepo agroAppRepo ;
    private Button uploadBtn, cancelBtn,choBtn;
    private static final  int IMAGE_VALUE = 1;
    private Uri mImageUri;
    private ProgressBar bar;
    private StorageReference mStorageRef;
    private EditText productPrice;
    private StorageTask mUploads;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mStorageRef = FirebaseStorage.getInstance().getReference("PRODUCTS");
        choBtn = findViewById(R.id.choose);
        edtTitle = findViewById(R.id.EdtTitle);
        fileName = findViewById(R.id.fileName);
        edtDesc =  findViewById(R.id.EdtDesc);
        bar = findViewById(R.id.progress_bar);
        productPrice = findViewById(R.id.editTextNumberDecimal);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();

        uploadBtn = findViewById(R.id.button2);
        cancelBtn = findViewById(R.id.button3);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploads != null && mUploads.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "An upload is ongoing", Toast.LENGTH_LONG).show();
                    return;
                }
               uploadDetails();
            }
        });


        choBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this,ProfileFragment.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_VALUE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            fileName.setText(mImageUri.toString());
        }
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_VALUE);
    }


    private String getFileExtension(Uri uri){
        // This was just a test
        ContextWrapper rapper = new ContextWrapper(this);
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

    public void uploadDetails(){
        if(mImageUri != null){
            final StorageReference fileref = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploads = fileref.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bar.setProgress(0);
                                }
                            },500);

                            fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {
                                    Toast.makeText(UploadActivity.this,"successfully uploaded",Toast.LENGTH_SHORT).show();
                                    final String tit = edtTitle.getText().toString().trim();
                                    String desc = edtDesc.getText().toString().trim();
                                    double price = Double.parseDouble(productPrice.getText().toString().trim());
                                    final FarmProduct product = new FarmProduct(desc);

                                    AgroAppRepo.getInstanceOfAgroApp().fetchUser(new AgroAppRepo.FireBaseCallbackUser() {
                                        @Override
                                        public void fireBaseUser(User basuser) {
                                            Log.d("User",""+basuser);
                                            long millis=System.currentTimeMillis();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM hh:mm:ss", Locale.getDefault());
                                            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                            String utcTime = dateFormat.format(millis);
                                            user = basuser;
                                            product.setTitle(tit);
                                            product.setPrice(price);
                                            product.setUser(user);
                                            product.setImage(uri.toString());
                                            product.setDate(utcTime);
                                            agroAppRepo.uploadProduct(product,UploadActivity.this);
                                        }
                                    });

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(UploadActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            bar.setProgress( (int) progress);
                        }
                    });

        }else{
            Toast.makeText(this,"Nothing was collected",Toast.LENGTH_SHORT).show();

        }
    }

}