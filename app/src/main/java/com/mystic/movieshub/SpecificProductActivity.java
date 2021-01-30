package com.mystic.movieshub;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SpecificProductActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PRODUCT = "Product";
    private ImageView imageView,authorimage;
    private TextView time, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_product);
        defineView();

        FarmProduct product = (FarmProduct) getIntent().getSerializableExtra(PRODUCT);
        if(product != null){

            time.setText(product.getDate());
            desc.setText(product.getDescription());
            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(Uri.parse(product.getImage()))
                    .into(authorimage);

            Glide.with(this)
                    .asBitmap()
                    .circleCrop()
                    .load(Uri.parse(product.getUser().getImage()))
                    .into(imageView);
        }

    }




    public void defineView(){
        imageView = findViewById(R.id.image);
        authorimage = findViewById(R.id.imagi);
        time = findViewById(R.id.time);
        desc = findViewById(R.id.textView8);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton2:
                //gguuuhu
                break;
            case R.id.imageButton3:
                //iufeheiojiojdiodj
                break;
            case R.id.imageButton4:
                //hfifdbdfufghbdfiufhui
                break;
        }

    }
}