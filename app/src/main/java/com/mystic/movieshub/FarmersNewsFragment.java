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



public class FarmersNewsFragment extends Fragment {
    RecyclerView mRecyclerView ;

    //List<Movie> dramamovieList;
   //AgroAppRepo agroAppRepo;
   //PlatformAdapter adapter;
    AgriNewsAdapter agriNewsAdapter;
    List<AgriNews> agriNewsContainer;
    public FarmersNewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
   /* public static FarmersNewsFragment newInstance(String param1, String param2) {
        FarmersNewsFragment fragment = new FarmersNewsFragment();

        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agriNewsContainer = AgroAppRepo.getInstanceOfAgroApp().agriNewsHolder();
        agriNewsAdapter = new AgriNewsAdapter(agriNewsContainer);

        /*adapter = new PlatformAdapter(dramamovieList,getActivity());
        adapter.setOnAdapterListener(new PlatformAdapter.CustomeAdapterListener() {
            @Override
            public void adapterListener(int position) {
                Movie movie = dramamovieList.get(position);
                Intent intent = new Intent(getActivity(), SpecificNewsActivity.class);
                intent.putExtra(SpecificNewsActivity.MOVIE_OBJECT,movie);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_genericview, container, false);
        /*mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);*/
        return view;
    }
}