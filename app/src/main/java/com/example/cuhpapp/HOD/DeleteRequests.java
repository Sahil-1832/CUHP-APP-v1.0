package com.example.cuhpapp.HOD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cuhpapp.R;

public class DeleteRequests extends AppCompatActivity {
    CardView deleteNotice,deleteImage,deleteEbooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_requests);
        getSupportActionBar().setTitle("Requests");

        deleteNotice = findViewById(R.id.deleteNotice);
        deleteImage = findViewById(R.id.deleteImage);
        deleteEbooks = findViewById(R.id.deleteEbooks);

        deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteRequests.this,DeleteNoticeRequest.class));
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteRequests.this,DeleteImageRequest.class));
            }
        });

        deleteEbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteRequests.this,DeleteEbooksRequest.class));
            }
        });
    }
}