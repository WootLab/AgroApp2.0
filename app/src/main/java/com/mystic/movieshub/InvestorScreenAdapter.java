package com.mystic.movieshub;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class InvestorScreenAdapter extends RecyclerView.Adapter<InvestorScreenAdapter.InvestorScreenHolder> {

    private List<User> userList;
    Context context;
    public InvestorScreenAdapter(List<User> userList, Context context){
        this.userList = userList;
        this.context = context;
    }
    @NonNull
    @Override

    public InvestorScreenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qualifiedfarmerview,parent,false);
        return new InvestorScreenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestorScreenHolder holder, int position) {

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

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class InvestorScreenHolder extends RecyclerView.ViewHolder {

        TextView name,localgov,state,typeoffarming,description;
        ImageView imageView;
        public InvestorScreenHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView12);
            localgov = itemView.findViewById(R.id.textView13);
            imageView = itemView.findViewById(R.id.imageView2);
            state = itemView.findViewById(R.id.textView14);
            description = itemView.findViewById(R.id.textView15);
            typeoffarming = itemView.findViewById(R.id.textView16);
        }
    }
}
