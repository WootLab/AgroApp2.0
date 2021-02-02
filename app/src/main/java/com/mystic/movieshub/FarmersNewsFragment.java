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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

public class FarmersNewsFragment extends Fragment {
    RecyclerView mRecyclerView ;
    AgriNewsAdapter agriNewsAdapter;
    AgroAppRepo agroAppRepo;
    ProgressBar bar;
    public FarmersNewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();

        agroAppRepo.fetchAgriNews(new AgroAppRepo.FireBaseCallbackAgriNews() {
            @Override
            public void firebaseAgriNews(List<AgriNews> agriNews) {
                bar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                agriNewsAdapter = new AgriNewsAdapter(agriNews);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(agriNewsAdapter);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_genericview, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        Button layout = view.findViewById(R.id.button5);
        layout.setVisibility(View.GONE);
        bar = view.findViewById(R.id.progressBar3);
        return view;
    }
}