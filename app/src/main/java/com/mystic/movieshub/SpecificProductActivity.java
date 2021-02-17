package com.mystic.movieshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class SpecificProductActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PRODUCT = "Product";
    public static final String FARMER = "farmer";
    private ImageView imageView;
    private TextView time, desc,location;
    private TextView name;
    private Button call;
    private Button chat;
    private TextView price;
    private FarmProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_product);
        defineView();

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        product = (FarmProduct) getIntent().getSerializableExtra(PRODUCT);
        if(product != null){
            time.setText(product.getDate());
            desc.setText(product.getDescription());
            name.setText(product.getUser().getName());
            location.setText(product.getLocation());
            price.setText(Double.toString(product.getPrice()));
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(product.getImage()))
                    .into(imageView);

        }

        respondToClick();

    }

    private void respondToClick() {
        call.setOnClickListener(this);
        chat.setOnClickListener(this);
    }


    public void defineView(){
        imageView = findViewById(R.id.image);
        time = findViewById(R.id.time);
        desc = findViewById(R.id.textView8);
        name = findViewById(R.id.sellersname);
        call = findViewById(R.id.imageButton2);
        chat = findViewById(R.id.imageButton3);
        location = findViewById(R.id.locatio);
        price = findViewById(R.id.textViewPrice);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton2:
                Toast.makeText(SpecificProductActivity.this,"You clicked call",Toast.LENGTH_LONG).show();
                String number = product.getUser().getPhoneNumber();
                Intent intenti = new Intent(Intent.ACTION_CALL);
                intenti.setData(Uri.parse(number));
                startActivity(intenti);
                break;
            case R.id.imageButton3:
                Intent intent = new Intent(this,ChatScreenActivity.class);
                intent.putExtra(FARMER,product.getUser());
                startActivity(intent);
                break;
        }

    }
}