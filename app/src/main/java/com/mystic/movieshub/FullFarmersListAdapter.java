package com.mystic.movieshub;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class FullFarmersListAdapter extends RecyclerView.Adapter<FullFarmersListAdapter.FullFarmersHolder> implements Filterable {

    List<User> userList;
    List<User> fullUserList;
    Context context;
    FullFarmersListener fullFarmersListener;
    AppListener appListener;

    public FullFarmersListAdapter(List<User> userList, Context context){
        this.userList = userList;
        this.context = context;
        fullUserList = new ArrayList<>(userList);
    }
    @NonNull
    @Override
    public FullFarmersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fullfarmersview,parent,false);
        return new FullFarmersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FullFarmersHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.localgov.setText(user.getRequirements().getLocalgov());
        holder.state.setText(user.getRequirements().getState());
        holder.typeoffarming.setText(user.getRequirements().getAgricTypes());
        if(user.getImage() != null){
            Glide.with(context)
                    .asBitmap()
                    .load(Uri.parse(user.getImage()))
                    .into(holder.imageView);
        }
    }


    public void moveToSpecificFarmer(FullFarmersListener fullFarmersListener){
        this.fullFarmersListener = fullFarmersListener;
    }

    public void approveApplication(AppListener appListener){
        this.appListener = appListener;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private final Filter filteredFarmer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredByNameUser = new ArrayList<>();
            if(constraint == null || constraint.length() < 0  ){
                filteredByNameUser.addAll(fullUserList);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for( User user : fullUserList){
                    if(user.getName().toLowerCase().contains(filteredPattern)){
                        filteredByNameUser.add(user);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredByNameUser;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userList.clear();
            userList.addAll((List)results.values);
            Log.d("plat","I am in search");
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return filteredFarmer;
    }

    public class FullFarmersHolder extends RecyclerView.ViewHolder {

        MaterialCardView materialCardView;
        TextView name,localgov,state,typeoffarming,description;
        ImageView imageView;
        Button butonAprove;

        public FullFarmersHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView = itemView.findViewById(R.id.msterial);
            name = itemView.findViewById(R.id.textView12);
            localgov = itemView.findViewById(R.id.textView13);
            imageView = itemView.findViewById(R.id.imageView2);
            state = itemView.findViewById(R.id.textView14);
            description = itemView.findViewById(R.id.textView15);
            butonAprove = itemView.findViewById(R.id.button15);

            materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fullFarmersListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            fullFarmersListener.farmerlistener(pos);
                        }
                    }
                }
            });


            butonAprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(appListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            appListener.appListenerRes(pos);
                        }
                    }
                }
            });
        }
    }


    public interface FullFarmersListener{
        void farmerlistener(int position);
    }


    public  interface AppListener{
        void appListenerRes(int position);
    }
}
