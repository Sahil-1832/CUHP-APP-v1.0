package com.example.cuhpapp.DEO;

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
import com.example.cuhpapp.delete.DeleteEbook;
import com.example.cuhpapp.delete.DeleteFiles;
import com.example.cuhpapp.delete.DeleteImage;
import com.example.cuhpapp.delete.DeleteNotice;

public class SendRequests extends AppCompatActivity implements View.OnClickListener{
    CardView deleteNotice,deleteImage,rejectedNotices,rejectedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_requests);
        getSupportActionBar().setTitle("Deletion Requests");

        deleteNotice=findViewById(R.id.deleteNotice);
        deleteImage=findViewById(R.id.deleteImage);
        rejectedNotices = findViewById(R.id.rejectedNotices);
        rejectedEvents = findViewById(R.id.rejectedEvents);

        deleteNotice.setOnClickListener(this);
        deleteImage.setOnClickListener(this);
        rejectedNotices.setOnClickListener(this);
        rejectedEvents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.deleteNotice){
                Intent intent = new Intent(SendRequests.this, NoticeDeo.class);
                startActivity(intent);
        }
        if(v.getId()==R.id.deleteImage){
                Intent intent = new Intent(SendRequests.this, EventsDeo.class);
                startActivity(intent);
        }
        if(v.getId()==R.id.rejectedNotices){
            Intent intent = new Intent(SendRequests.this,RejectedNotices.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.rejectedEvents){
            Intent intent = new Intent(SendRequests.this,RejectedEvents.class);
            startActivity(intent);
        }
    }
}