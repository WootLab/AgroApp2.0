package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    //RecyclerView mRecyclerView ;
    //List<Movie> comedymovieList;
    private AgroAppRepo agroAppRepo;
    private User user;
    private CircleImageView circleImageView;
    private TextView textView;
    private Button upload,uploadNewsBtn;
    //PlatformAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();

        //mAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //user = agroAppRepo.getUser();

        /*fetchUser(new FireBaseCallbackUser() {
            @Override
            public void fireBaseUser(User basuser) {

                user = basuser;
            }
        });*/
        //Log.d("Working",""+user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);
       circleImageView = view.findViewById(R.id.cycleimage);
       textView = view.findViewById(R.id.username);
       upload = view.findViewById(R.id.btnUpload);
       String bamidele = "bam@gmail.com";
       String yvonne = "yvonne@gmail.com";
       String chinedu = "chinedu@gmail.com";
       uploadNewsBtn = view.findViewById(R.id.button4);


       /*String userEmail = user.getEmail();

        if(userEmail.equals(bamidele) || userEmail.equals(yvonne) || userEmail.equals(chinedu)){
           uploadNewsBtn.setVisibility(View.VISIBLE);
       }else{
            uploadNewsBtn.setVisibility(View.GONE);
        }

       textView.setText(user.getName());*/
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
        return view;
    }



    //fetches from base
    private void fetchUser(final FireBaseCallbackUser fireBaseCallback){
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatebaseReference = firebaseDatabase.getReference("USERS").child(userId);
        mDatebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                fireBaseCallback.fireBaseUser(user);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface FireBaseCallbackUser {
        void fireBaseUser(User basuser);
    }

}