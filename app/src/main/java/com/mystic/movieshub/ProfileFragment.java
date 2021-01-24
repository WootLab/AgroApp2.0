package com.mystic.movieshub;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private ImageView circleImageView;
    private TextView textView;
    private Button upload,uploadNewsBtn;
    private AgroAppRepo agroAppRepo;
    private ProgressBar bar ;
    private LinearLayout linearLayout;

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
            public void fireBaseUser(User basuser) {
                bar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                String userEmail = basuser.getEmail();
                textView.setText(basuser.getName());
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
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        circleImageView = view.findViewById(R.id.cycleimage);
        textView = view.findViewById(R.id.username);
        upload = view.findViewById(R.id.btnUpload);
        uploadNewsBtn = view.findViewById(R.id.button4);
        bar = view.findViewById(R.id.prog);
        linearLayout = view.findViewById(R.id.lay);
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

}