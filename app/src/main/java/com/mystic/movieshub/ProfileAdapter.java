package com.mystic.movieshub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {
    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfileHolder extends RecyclerView.ViewHolder {

        /*CircleImageView circleImageView;
        TextView textView ;
        Button upload ;
        Button uploadNewsBtn ;*/
        public ProfileHolder(@NonNull View itemView) {
            super(itemView);
            /*circleImageView =  itemView.findViewById(R.id.cycleimage);
            textView = itemView.findViewById(R.id.username);
            upload = itemView.findViewById(R.id.btnUpload);
            uploadNewsBtn = itemView.findViewById(R.id.button4);*/
        }
    }
}
