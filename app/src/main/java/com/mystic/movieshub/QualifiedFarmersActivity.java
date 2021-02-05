package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;
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

public class QualifiedFarmersActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private FarmPhotoAdapter farmPhotoAdapter;
    private ImageButton call, chat;
    private TextView name,email,adress, description, phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualified_farmers);
        final User user = (User) getIntent().getSerializableExtra("QualifiedFarmers");
        defineViews();

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(user.getImage()))
                .into(imageView);
        name.setText(user.getName());
        email.setText(user.getEmail());
        adress.setText(user.getRequirements().getLocation());
        phoneNumber.setText(user.getPhoneNumber());
        description.setText(user.getRequirements().getDescription());
        final List<Uri> uriContainer = user.getRequirements().getImages();

        if(farmPhotoAdapter == null){
            farmPhotoAdapter = new FarmPhotoAdapter(uriContainer,this);
        }

        farmPhotoAdapter.showPhoto(new FarmPhotoAdapter.PhotoAdapterListener() {
            @Override
            public void photolistener(int position) {
                Intent intent = new Intent(QualifiedFarmersActivity.this,ShowFullFarmActivity.class);
                Uri uri = uriContainer.get(position);
                intent.putExtra("PHOTO",uri);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(farmPhotoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = user.getPhoneNumber();
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
    }
}