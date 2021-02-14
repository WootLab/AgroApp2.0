package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

public class ChatScreenActivity extends AppCompatActivity {
    private List<Chat> chats;
    private EditText message;
    private ImageButton sendBtn;
    private RecyclerView recyclerView;
    private AgroAppRepo agroAppRepo;
    private ChatScreenAdapter chatScreenAdapter;
    private ProgressBar bar;
    private ImageView imageView;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        defineViews();
        final User contact = (User) getIntent().getSerializableExtra("CONTACT");
        final User user = (User) getIntent().getSerializableExtra(AgroAppRepo.ADMIN_USER);
        final User farmer = (User) getIntent().getSerializableExtra(SpecificProductActivity.FARMER);
        final User qualifiedfarmer = (User) getIntent().getSerializableExtra("ChatQualifiedFarmer");
        if(user != null){
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(user.getImage())
                    .into(imageView);
            name.setText(user.getName());
        } else if(farmer != null){
            //assert farmer != null;
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(farmer.getImage())
                    .into(imageView);
            name.setText(farmer.getName());
        }else if(qualifiedfarmer != null){
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(qualifiedfarmer.getImage())
                    .into(imageView);
            name.setText(qualifiedfarmer.getName());
        }else if(contact != null){
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(contact.getImage())
                    .into(imageView);
            name.setText(contact.getName());
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        if(farmer != null ){
            agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
                @Override
                public void firebaseMessages(List<Chat> chatCont) {
                    chats = chatCont;
                    Log.d("Chats Size",""+chats.size());
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    //setUpAdapter(chats);

                    chatScreenAdapter = new ChatScreenAdapter(chats,ChatScreenActivity.this);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatScreenActivity.this);
                recyclerView.setAdapter(chatScreenAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setStackFromEnd(true);
                }
            },farmer.getUid());

        }else if(user != null){
            agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
                @Override
                public void firebaseMessages(List<Chat> chatCont) {
                    chats = chatCont;
                    Log.d("Chats Size",""+chats.size());
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    setUpAdapter(chats);
                }
            },user.getUid());
        }else if(qualifiedfarmer != null){
            agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
                @Override
                public void firebaseMessages(List<Chat> chatCont) {
                    chats = chatCont;
                    Log.d("Chats Size",""+chats.size());
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    setUpAdapter(chats);
                }
            },qualifiedfarmer.getUid());



        }else if(contact != null){
            agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
                @Override
                public void firebaseMessages(List<Chat> chatCont) {
                    chats = chatCont;
                    Log.d("Chats Size",""+chats.size());
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    setUpAdapter(chats);
                }
            },contact.getUid());
        }


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mess = message.getText().toString().trim();
                    if( user != null){
                        agroAppRepo.sendMessage(mess,user.getUid(), ChatScreenActivity.this);
                    }else if(farmer != null){
                        agroAppRepo.sendMessage(mess,farmer.getUid(), ChatScreenActivity.this);
                    } else if(qualifiedfarmer != null){
                        agroAppRepo.sendMessage(mess,qualifiedfarmer.getUid(), ChatScreenActivity.this);
                    }else if(contact != null){
                        agroAppRepo.sendMessage(mess,contact.getUid(), ChatScreenActivity.this);
                    }
                    message.setText("");
            }
        });



    }

    private void defineViews(){
        message = findViewById(R.id.edtMess);
        sendBtn = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.cycler);
        bar = findViewById(R.id.progressBar4);
        imageView = findViewById(R.id.profileImage);
        name = findViewById(R.id.usernamee);
        recyclerView.setHasFixedSize(true);

    }

    private void setUpAdapter(List<Chat> chatholder){
        if(chatScreenAdapter == null ){
            chatScreenAdapter = new ChatScreenAdapter(chatholder,ChatScreenActivity.this);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatScreenActivity.this);
        recyclerView.setAdapter(chatScreenAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
    }
}