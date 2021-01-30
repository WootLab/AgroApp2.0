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
import android.widget.TextView;

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
        final User user = (User) getIntent().getSerializableExtra(AgroAppRepo.ADMIN_USER);
        final User farmer = (User) getIntent().getSerializableExtra(SpecificProductActivity.FARMER);
        if(user != null){
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(user.getImage())
                    .into(imageView);
            name.setText(user.getName());
        } else{
            assert farmer != null;
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(farmer.getImage())
                    .into(imageView);
            name.setText(farmer.getName());
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        agroAppRepo = AgroAppRepo.getInstanceOfAgroApp();
        agroAppRepo.loadMessages(new AgroAppRepo.FireBaseMessages() {
            @Override
            public void firebaseMessages(List<Chat> chatCont) {
                chats = chatCont;
                chatScreenAdapter = new ChatScreenAdapter(chats,ChatScreenActivity.this);
                bar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(!(chatCont.size() > 0)){

                }
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
                        agroAppRepo.sendMessage(mess,farmer.getUid(), ChatScreenActivity.this);
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
        name = findViewById(R.id.usernamee);
        recyclerView.setHasFixedSize(true);

    }
}