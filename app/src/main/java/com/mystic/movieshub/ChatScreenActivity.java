package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setHasFixedSize(true);

        final User user = (User) getIntent().getSerializableExtra(AgroAppRepo.ADMIN_ID);
        if(user != null){
            Glide.with(this).asBitmap().load(user.getImage()).into(imageView);
        } else{
            //Glide.with(this).asBitmap().load(user.getImage()).into(imageView);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        defineViews();
        agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
            @Override
            public void firebaseMessages(List<Chat> chatCont) {
                chats = chatCont;
                chatScreenAdapter = new ChatScreenAdapter(chats,ChatScreenActivity.this);
                bar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatScreenActivity.this);
                recyclerView.setAdapter(chatScreenAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setStackFromEnd(true);
            }
        });
        final String mess = message.getText().toString().trim();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mess.trim().equals("")){
                    if( user != null){
                        agroAppRepo.sendMessage(mess,user.getUid(), ChatScreenActivity.this);
                    }else{
                        agroAppRepo.sendMessage(mess,"", ChatScreenActivity.this);
                    }
                }

            }
        });


    }

    private void defineViews(){
        message = findViewById(R.id.edtMess);
        sendBtn = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.cycler);
        bar = findViewById(R.id.progressBar4);
        imageView = findViewById(R.id.profileImage);
    }
}