package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QualifiedFarmersActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private FarmPhotoAdapter farmPhotoAdapter;
    private ImageButton call, chat;
    private TextView name,email,adress, description, phoneNumber,stateAndLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualified_farmers);

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final User seefull = (User) getIntent().getSerializableExtra("SeeFull");
        final User user = (User) getIntent().getSerializableExtra("QualifiedFarmers");
        final User qual = (User) getIntent().getSerializableExtra("QualUser");
        defineViews();
        if(user != null ){
            Objects.requireNonNull(getSupportActionBar()).setTitle(user.getName());

            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(user.getImage()))
                    .into(imageView);
            name.setText(user.getName());
            email.setText(user.getEmail());
            adress.setText(user.getRequirements().getLocation());
            phoneNumber.setText(user.getPhoneNumber());
            description.setText(user.getRequirements().getDescription());
            final List<String> uriContainer = user.getRequirements().getImages();
            if(farmPhotoAdapter == null){
                farmPhotoAdapter = new FarmPhotoAdapter(uriContainer,this);
            }

            farmPhotoAdapter.showPhoto(position -> {
                Intent intent = new Intent(QualifiedFarmersActivity.this,ShowFullFarmActivity.class);
                String uri = uriContainer.get(position);
                intent.putExtra("PHOTO",uri);
                startActivity(intent);
            });
        }else if(qual != null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(qual.getName());
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(qual.getImage()))
                    .into(imageView);
            name.setText(qual.getName());
            email.setText(qual.getEmail());
            adress.setText(qual.getRequirements().getLocation());
            phoneNumber.setText(qual.getPhoneNumber());
            description.setText(qual.getRequirements().getDescription());
            String location = qual.getRequirements().getState()+","+qual.getRequirements().getLocalgov();
            stateAndLocal.setText(location);
            final List<String> uriContainer = qual.getRequirements().getImages();
            if(farmPhotoAdapter == null){
                farmPhotoAdapter = new FarmPhotoAdapter(uriContainer,this);
            }
            farmPhotoAdapter.showPhoto(position -> {
                Intent intent = new Intent(QualifiedFarmersActivity.this,ShowFullFarmActivity.class);
                String uri = uriContainer.get(position);
                intent.putExtra("PHOTO",uri);
                startActivity(intent);
            });
        } else if(seefull != null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(seefull.getName());
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(seefull.getImage()))
                    .into(imageView);
            name.setText(seefull.getName());
            email.setText(seefull.getEmail());
            adress.setText(seefull.getRequirements().getLocation());
            phoneNumber.setText(seefull.getPhoneNumber());
            description.setText(seefull.getRequirements().getDescription());
            String location = seefull.getRequirements().getState()+","+seefull.getRequirements().getLocalgov();
            stateAndLocal.setText(location);
            final List<String> uriContainer = seefull.getRequirements().getImages();
            if(farmPhotoAdapter == null){
                farmPhotoAdapter = new FarmPhotoAdapter(uriContainer,this);
            }
            farmPhotoAdapter.showPhoto(position -> {
                Intent intent = new Intent(QualifiedFarmersActivity.this,ShowFullFarmActivity.class);
                String uri = uriContainer.get(position);
                intent.putExtra("PHOTO",uri);
                startActivity(intent);
            });
        }

        recyclerView.setAdapter(farmPhotoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        call.setOnClickListener(v -> {
            if(user != null){
                String number = user.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(number));
                startActivity(intent);
            } else if(qual != null) {
                String number = qual.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(number));
                startActivity(intent);
            } else if(seefull != null){
                String number = seefull.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(number));
                startActivity(intent);
            }


        });





        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QualifiedFarmersActivity.this,ChatScreenActivity.class);
                intent.putExtra("ChatQualifiedFarmer",user);
                startActivity(intent);
            }
        });

    }



    private void defineViews(){
        imageView = findViewById(R.id.imageView4);
        recyclerView = findViewById(R.id.recycooler);
        name = findViewById(R.id.textView17);
        email = findViewById(R.id.textView18);
        adress = findViewById(R.id.textView19);
        description = findViewById(R.id.textView22);
        call = findViewById(R.id.imageButton5);
        chat = findViewById(R.id.imageButton6);
        phoneNumber = findViewById(R.id.textView23);
        stateAndLocal = findViewById(R.id.textView20);
    }
}