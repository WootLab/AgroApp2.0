package com.mystic.movieshub;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AgriNewsAdapter extends RecyclerView.Adapter<AgriNewsAdapter.AgriHolder> {

    List<AgriNews> agriNewsList;

    public AgriNewsAdapter(List<AgriNews> agriNewsList) {
        this.agriNewsList = agriNewsList;
    }

    @NonNull
    @Override
    public AgriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AgriHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AgriHolder extends RecyclerView.ViewHolder {

        public AgriHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
