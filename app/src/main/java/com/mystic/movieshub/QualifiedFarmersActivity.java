package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class QualifiedFarmersActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private FarmPhotoAdapter farmPhotoAdapter;
    private List<Uri> uriConatiner;
    private TextView name,email,adress, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualified_farmers);
        uriConatiner = new ArrayList<>();
        User user = (User) getIntent().getSerializableExtra("QualifiedFarmers");
        defineViews();

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(user.getImage()))
                .into(imageView);
        name.setText(user.getName());
        email.setText(user.getEmail());
        adress.setText(user.getRequirements().getLocation());
        description.setText(user.getRequirements().getDescription());
        uriConatiner = user.getRequirements().getImages();

        if(farmPhotoAdapter == null){
            farmPhotoAdapter = new FarmPhotoAdapter(uriConatiner,this);
        }
        recyclerView.setAdapter(farmPhotoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    private void defineViews(){
        imageView = findViewById(R.id.imageView4);
        recyclerView = findViewById(R.id.recycooler);
        name = findViewById(R.id.textView17);
        email = findViewById(R.id.textView18);
        adress = findViewById(R.id.textView19);
        description = findViewById(R.id.textView22);
    }
}