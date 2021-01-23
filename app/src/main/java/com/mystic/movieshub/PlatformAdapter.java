package com.mystic.movieshub;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.MovieHolder> {
    List<FarmProduct> farmProductList;
    Context context;
    CustomeAdapterListener listener;

    public PlatformAdapter(List<FarmProduct> farmProductList, Context context){
        this.farmProductList = farmProductList;
        this.context = context;
    }

    public void setOnAdapterListener(CustomeAdapterListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platformspec,parent,false);
        return new MovieHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        FarmProduct farmProduct = farmProductList.get(position);
        holder.textView1.setText(farmProduct.getTitle());
        holder.textView2.setText(farmProduct.getDescriptrion());

        if(farmProduct.getUser().getImage() != null){
            Glide.with(context)
                    .asBitmap()
                    .load(Uri.parse(farmProduct.getUser().getImage()))
                    .into(holder.circleImageView);
        }

    }

    @Override
    public int getItemCount() {
        return farmProductList.size();
    }
    //This inner class serves as the viewHolder it holds the view that is repeated
    public static class MovieHolder extends RecyclerView.ViewHolder {
        MaterialCardView material;
        CircleImageView circleImageView;
        TextView textView1,textView2;
        public MovieHolder(@NonNull View itemView, final CustomeAdapterListener listener) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.cycleimage);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            material = itemView.findViewById(R.id.material);

            material.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int positon = getAdapterPosition();
                        if (positon != RecyclerView.NO_POSITION){
                            listener.adapterListener(positon);
                        }
                    }
                }
            });
        }
    }

    public interface CustomeAdapterListener{
        void adapterListener(int position);
    }
}