package com.example.cuhpapp.HOD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cuhpapp.AdminSide;
import com.example.cuhpapp.DEO.Requests;
import com.example.cuhpapp.MainActivity;
import com.example.cuhpapp.R;
import com.example.cuhpapp.Session.SessionManagement;
import com.example.cuhpapp.SuperAdmin.Requests2;
import com.example.cuhpapp.Teacher.Faculty;
import com.example.cuhpapp.UploadImage;
import com.example.cuhpapp.UploadNotice;
import com.example.cuhpapp.delete.DeleteFiles;
import com.example.cuhpapp.faculty.UpdateFaculty;

public class HODSide extends AppCompatActivity implements View.OnClickListener{
    CardView uploadNotice,addGalleryImage,requests,faculty,deleteFiles,deleteRequests;
    TextView logOut;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hodside);

        uploadNotice = findViewById(R.id.addNotice);
        addGalleryImage=findViewById(R.id.addGalleryImage);
        faculty=findViewById(R.id.faculty);
        requests=findViewById(R.id.requests);
        deleteFiles=findViewById(R.id.deleteFiles);
        deleteRequests = findViewById(R.id.deleteRequests);
        logOut= findViewById(R.id.logOut);

        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        requests.setOnClickListener(this);
        faculty.setOnClickListener(this);
        deleteFiles.setOnClickListener(this);
        deleteRequests.setOnClickListener(this);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement=new SessionManagement(HODSide.this);
                sessionManagement.removeSession();
                Intent intent = new Intent(HODSide.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addNotice){
            Intent intent = new Intent(HODSide.this, UploadNotice.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.addGalleryImage){
            Intent intent = new Intent(HODSide.this, UploadImage.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.requests){
                Intent intent = new Intent(HODSide.this, Requests2.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("department",getIntent().getStringExtra("department"));
                intent.putExtra("value",getIntent().getStringExtra("value"));
                intent.putExtra("role",getIntent().getStringExtra("role"));
                HODSide.this.startActivity(intent);
        }
        if(v.getId()==R.id.faculty){
            Intent intent = new Intent(HODSide.this, UpdateFaculty.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.deleteFiles){
            Intent intent = new Intent(HODSide.this, DeleteFiles.class);
            startActivity(intent);
        }if(v.getId()==R.id.deleteRequests){
            Intent intent = new Intent(HODSide.this, DeleteRequests.class);
            startActivity(intent);
        }
    }
}