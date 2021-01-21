package com.mystic.movieshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    //RecyclerView mRecyclerView ;
    //List<Movie> comedymovieList;
    AgroAppRepo agroAppRepo;
    private User user;
    //PlatformAdapter adapter;



    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    /*public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        user = agroAppRepo.getUserDetail();

        //comedymovieList = agroAppRepo.getComedyMovieList();
        //adapter = new PlatformAdapter(comedymovieList,getActivity());

        /*adapter.setOnAdapterListener(new PlatformAdapter.CustomeAdapterListener() {
            @Override
            public void adapterListener(int position) {
                Movie movie = comedymovieList.get(position);
                Intent intent = new Intent(getActivity(), SpecificNewsActivity.class);
                intent.putExtra(SpecificNewsActivity.MOVIE_OBJECT,movie);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);



       /* mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);*/
        return view;
    }
}