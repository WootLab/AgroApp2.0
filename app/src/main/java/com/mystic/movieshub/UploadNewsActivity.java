package com.mystic.movieshub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UploadNewsActivity extends AppCompatActivity {

    private EditText title,description, link;
    private Button uploadNews, cancelUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_news);

        title = findViewById(R.id.newsTitle);
        description = findViewById(R.id.newsDesc);
        link = findViewById(R.id.newsLink);

        uploadNews = findViewById(R.id.uploadNews);
        cancelUpload = findViewById(R.id.cancel);


        uploadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit = title.getText().toString().trim();
                String descri = description.getText().toString().trim();
                AgriNews news = new AgriNews(tit,descri);
                if(link != null){
                    String lin = link.getText().toString().trim();
                    news.setSource(lin);
                }
                AgroAppRepo.getInstanceOfAgroApp().uploadNews(news,UploadNewsActivity.this);
            }
        });

    }
}