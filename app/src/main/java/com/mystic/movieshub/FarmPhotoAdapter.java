package com.mystic.movieshub;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FarmPhotoAdapter extends RecyclerView.Adapter<FarmPhotoAdapter.FarmPhotoHolder> {
    private final List<Uri> uriList;
    private final Context context;
    private PhotoAdapterListener photoAdapterListener;

    public FarmPhotoAdapter(List<Uri> uriList , Context context){
        this.uriList = uriList;
        this.context = context;
    }
    @NonNull
    @Override
    public FarmPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmphotoadapterspec,parent,false);
        return new FarmPhotoHolder(view);
    }


    public void showPhoto(PhotoAdapterListener photoAdapterListener){
        this.photoAdapterListener = photoAdapterListener;
    }
    @Override
    public void onBindViewHolder(@NonNull FarmPhotoHolder holder, int position) {
        Uri uri = uriList.get(position);
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class FarmPhotoHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        ImageView imageView;
        public FarmPhotoHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView = itemView.findViewById(R.id.mata);
            imageView = itemView.findViewById(R.id.magi);

            materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(photoAdapterListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            photoAdapterListener.photolistener(pos);
                        }
                    }
                }
            });
        }
    }


    public interface PhotoAdapterListener{
        void photolistener(int position);
    }
}
