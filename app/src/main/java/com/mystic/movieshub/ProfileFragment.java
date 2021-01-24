package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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



    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        user = agroAppRepo.getUser();

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

       String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        assert userEmail != null;
        if(userEmail.equals(bamidele) || userEmail.equals(yvonne) || userEmail.equals(chinedu)){
           uploadNewsBtn.setVisibility(View.VISIBLE);
       }else{
            uploadNewsBtn.setVisibility(View.GONE);
        }


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
}