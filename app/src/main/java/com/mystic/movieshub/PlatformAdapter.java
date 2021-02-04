package com.mystic.movieshub;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;



public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.MovieHolder> {
    private List<FarmProduct> farmProductList;
    private Context context;
    private CustomeAdapterListener listener;
    private String userId;

    public PlatformAdapter(List<FarmProduct> farmProductList, Context context){
        this.farmProductList = farmProductList;
        this.context = context;
        userId = FirebaseAuth.getInstance().getUid();
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
        holder.textView2.setText(farmProduct.getDescription());

        if(farmProduct.getUser().getUid().equals(userId) || userId.equals("ADMIN ID")){
            holder.deleteBtn.setVisibility(View.VISIBLE);
        }else{
            holder.deleteBtn.setVisibility(View.GONE);
        }

        if(farmProduct.getUser().getImage() != null){
            Glide.with(context)
                    .asBitmap()
                    .load(Uri.parse(farmProduct.getImage()))
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
        ImageView circleImageView;
        TextView textView1,textView2;
        Button deleteBtn;
        public MovieHolder(@NonNull View itemView, final CustomeAdapterListener listener) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.cycleimage);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            material = itemView.findViewById(R.id.material);
            deleteBtn = itemView.findViewById(R.id.button6);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int positon = getAdapterPosition();
                        if(positon != RecyclerView.NO_POSITION){
                            listener.deleteAdvert(positon);
                        }
                    }
                }
            });
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
        void deleteAdvert(int position);
    }
}
