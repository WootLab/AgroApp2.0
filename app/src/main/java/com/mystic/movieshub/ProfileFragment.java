package com.mystic.movieshub;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static  final int PROFILE_PIC = 2;
    private ImageView circleImageView;
    private TextView nametextView,email,phone,role,changeProfilePhoto;
    private Button upload,uploadNewsBtn;
    private AgroAppRepo agroAppRepo;
    private ProgressBar bar , barPicture;
    private LinearLayout linearLayout;
    private Uri mImageUri;
    private Button buttonApply;
    private StorageTask mUploads;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        final String bamidele = "bam@gmail.com";
        final String yvonne = "yvonne@gmail.com";
        final String chinedu = "chinedu@gmail.com";

        agroAppRepo.fetchUser(new AgroAppRepo.FireBaseCallbackUser() {
            @Override
            public void fireBaseUser(final User basuser) {
                bar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                String userEmail = basuser.getEmail();
                nametextView.setText(basuser.getName());
                email.setText(basuser.getEmail());
                phone.setText(basuser.getPhoneNumber());
                role.setText(basuser.getRole());
                Glide.with(getActivity())
                        .asBitmap()
                        .load(Uri.parse(basuser.getImage()))
                        .circleCrop()
                        .into(circleImageView);
                if(userEmail.equals(bamidele) || userEmail.equals(yvonne) || userEmail.equals(chinedu)){
                    uploadNewsBtn.setVisibility(View.VISIBLE);
                }else{
                    uploadNewsBtn.setVisibility(View.GONE);
                }

                if(basuser.getRole().equals("farmer") || basuser.getEmail().equals("bam@gmail.com")){
                    buttonApply.setVisibility(View.VISIBLE);
                    upload.setVisibility(View.VISIBLE);
                    if(basuser.getRequirements().isApplicationState()){
                        buttonApply.setText("ALREADY APPLIED CHECK STATUS");
                    }else{
                        buttonApply.setText("APPLY FOR LOANS");
                    }
                }else{
                    buttonApply.setVisibility(View.GONE);
                }


                buttonApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(basuser.getRequirements().isApplicationState()){
                            Intent status= new Intent(getActivity(),StatusActivity.class);
                            startActivity(status);
                        }else{
                            Intent intent = new Intent(getActivity(),ApplyForLoanActivity.class);
                            startActivity(intent);
                        }

                    }
                });
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        circleImageView = view.findViewById(R.id.cycleimage);
        nametextView = view.findViewById(R.id.username);
        upload = view.findViewById(R.id.btnUpload);
        uploadNewsBtn = view.findViewById(R.id.button4);
        bar = view.findViewById(R.id.prog);
        buttonApply = view.findViewById(R.id.button7);
        linearLayout = view.findViewById(R.id.lay);
        email = view.findViewById(R.id.Txtemail);
        phone = view.findViewById(R.id.phoneNumber);
        role = view.findViewById(R.id.position);
        changeProfilePhoto = view.findViewById(R.id.usernamey);
        barPicture = view.findViewById(R.id.progressBar7);

        uploadNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UploadNewsActivity.class);
                startActivity(intent);
            }
        });


       upload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(),UploadActivity.class);
               startActivity(intent);
           }
       });

       circleImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               chooseImage();
           }
       });


       changeProfilePhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mUploads != null && mUploads.isInProgress()){
                   Toast.makeText(getActivity(), "An upload is ongoing", Toast.LENGTH_LONG).show();
               }else{
                   uploadtobase();
               }

           }
       });


        return view;
    }


    private String getFileExtension(Uri uri){
        // This was just a test
        ContextWrapper rapper = new ContextWrapper(getActivity());
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_PIC && resultCode == Activity.RESULT_OK && data != null  && data.getData() != null){
            mImageUri = data.getData();
            Glide.with(getActivity())
                    .asBitmap()
                    .circleCrop()
                    .load(mImageUri)
                    .into(circleImageView);
            changeProfilePhoto.setVisibility(View.VISIBLE);
            //uploadtobase();

        }else{
            changeProfilePhoto.setVisibility(View.GONE);
        }
    }





    public void uploadtobase(){
        if(mImageUri != null ){
            barPicture.setVisibility(View.VISIBLE);
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("USERSPIC");
            final StorageReference fileref = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploads = fileref.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if(FirebaseAuth.getInstance().getUid() == null){
                                        String userId = FirebaseAuth.getInstance().getUid();
                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference usersBaseReference = firebaseDatabase.getReference("USERS");
                                        AgroAppRepo.getInstanceOfAgroApp().loadSpecUser(userId, new AgroAppRepo.SpecificUser() {
                                            @Override
                                            public void loadSpecUse(User user) {
                                                user.setImage(uri.toString());
                                                usersBaseReference.child(userId)
                                                        .setValue(user)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    barPicture.setVisibility(View.GONE);
                                                                    Toast.makeText(getActivity(),"Picture Succesfully uploaded uploaded",Toast.LENGTH_SHORT).show();
                                                                }else {
                                                                    Toast.makeText(getActivity(),"There was an error ",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                    }

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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PROFILE_PIC);
    }

    private void uploadPicture() {


    }


    //fetches from base

}