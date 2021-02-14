package com.mystic.movieshub;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.ViewContain> {
    private final List<Chat> chats;
    private final Context context;
    private MyListener listener;

    public ChatlistAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewContain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactsview,parent,false);
        return new ViewContain(view);
    }


    public void moveToChatScreen(MyListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewContain holder, int position) {

        Chat chat = chats.get(position);
        String myId = FirebaseAuth.getInstance().getUid();

        if(chat.getSenderId().equals(myId)){
            String userId = chat.getReceiverId();
            AgroAppRepo.getInstanceOfAgroApp().loadSpecUser(userId, new AgroAppRepo.SpecificUser() {
                @Override
                public void loadSpecUse(User user) {
                    Glide.with(context)
                            .asBitmap()
                            .placeholder(R.drawable.doctor)
                            .load(Uri.parse(user.getImage()))
                            .into(holder.contactImage);

                    holder.txtName.setText(user.getName());
                    holder.email.setText(user.getEmail());
                }
            });
        }else if(chat.getReceiverId().equals(myId)){
            String userId = chat.getSenderId();
            AgroAppRepo.getInstanceOfAgroApp().loadSpecUser(userId, new AgroAppRepo.SpecificUser() {
                @Override
                public void loadSpecUse(User user) {
                    Glide.with(context)
                            .asBitmap()
                            .placeholder(R.drawable.doctor)
                            .load(Uri.parse(user.getImage()))
                            .into(holder.contactImage);

                    holder.txtName.setText(user.getName());
                    holder.email.setText(user.getEmail());
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewContain extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView txtName;
        TextView email;

        public ViewContain(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.profile_image);
            txtName = itemView.findViewById(R.id.txtName);
            email = itemView.findViewById(R.id.emailTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.respond(pos);
                        }

                    }
                }
            });
        }



    }


    public interface MyListener{
        void respond(int pos);
    }
}

