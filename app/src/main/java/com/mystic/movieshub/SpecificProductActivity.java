package com.mystic.movieshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SpecificProductActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PRODUCT = "Product";
    public static final String FARMER = "farmer";
    private ImageView imageView,authorimage;
    private TextView time, desc;
    private TextView name;
    private ImageButton call;
    private ImageButton chat;
    private ImageButton cart;
    private FarmProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_product);
        defineView();


        product = (FarmProduct) getIntent().getSerializableExtra(PRODUCT);
        if(product != null){
            time.setText(product.getDate());
            desc.setText(product.getDescription());
            name.setText(product.getUser().getName());
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(product.getImage()))
                    .into(imageView);

            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(Uri.parse(product.getUser().getImage()))
                    .into(authorimage);
        }

        respondToClick();

    }

    private void respondToClick() {
        call.setOnClickListener(this);
        chat.setOnClickListener(this);
        cart.setOnClickListener(this);
    }


    public void defineView(){
        imageView = findViewById(R.id.image);
        authorimage = findViewById(R.id.imagi);
        time = findViewById(R.id.time);
        desc = findViewById(R.id.textView8);
        name = findViewById(R.id.sellersname);
        call = findViewById(R.id.imageButton2);
        chat = findViewById(R.id.imageButton3);
        cart = findViewById(R.id.imageButton4);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton2:
                Toast.makeText(SpecificProductActivity.this,"You clicked call",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageButton3:
                Toast.makeText(SpecificProductActivity.this,"You clicked chat",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ChatScreenActivity.class);
                intent.putExtra(FARMER,product.getUser());
                startActivity(intent);
                break;
            case R.id.imageButton4:
                Toast.makeText(SpecificProductActivity.this,"You clicked cart",Toast.LENGTH_LONG).show();
                break;
        }

    }
}