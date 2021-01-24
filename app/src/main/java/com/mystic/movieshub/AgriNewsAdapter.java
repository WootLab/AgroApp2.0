package com.mystic.movieshub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agrinews,parent,false);
        return new AgriHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgriHolder holder, int position) {

        AgriNews news = agriNewsList.get(position);
        holder.title.setText(news.getTitle());
        holder.desc.setText(news.getDescription());
        if(news.getSource() != null){
            holder.link.setText(news.getSource());
        }
    }

    @Override
    public int getItemCount() {

        return agriNewsList.size();
    }

    public class AgriHolder extends RecyclerView.ViewHolder {
        TextView title,desc,link;
        public AgriHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView5);
            desc = itemView.findViewById(R.id.textView7);
            link = itemView.findViewById(R.id.link);
        }


    }
}
