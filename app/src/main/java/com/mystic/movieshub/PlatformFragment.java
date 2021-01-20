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
 * Use the {@link PlatformFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatformFragment extends Fragment {


    RecyclerView mRecyclerView ;
    List<Movie> actionmovieList;
    AgroAppRepo agroAppRepo;
    PlatformAdapter adapter;

    public PlatformFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PlatformFragment newInstance() {
        return new PlatformFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = new AgroAppRepo();

        actionmovieList = agroAppRepo.getActionMovieList();
        adapter = new PlatformAdapter(actionmovieList,getActivity());
        adapter.setOnAdapterListener(new PlatformAdapter.CustomeAdapterListener() {
            @Override
            public void adapterListener(int position) {
                Movie movie = actionmovieList.get(position);
                Intent intent = new Intent(getActivity(), SpecificNewsActivity.class);
                intent.putExtra(SpecificNewsActivity.MOVIE_OBJECT,movie);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genericview, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}