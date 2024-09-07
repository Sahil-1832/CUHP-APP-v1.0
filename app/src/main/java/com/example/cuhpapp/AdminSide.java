package com.example.cuhpapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cuhpapp.DEO.Requests;
import com.example.cuhpapp.DEO.SendRequests;
import com.example.cuhpapp.Session.SessionManagement;
import com.example.cuhpapp.SuperAdmin.Requests2;
import com.example.cuhpapp.Teacher.Faculty;
import com.example.cuhpapp.delete.DeleteFiles;
import com.example.cuhpapp.faculty.UpdateFaculty;

public class AdminSide extends AppCompatActivity implements View.OnClickListener {

    CardView uploadNotice,addGalleryImage,requests,faculty,deleteFiles;
    TextView logOut;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side);

        uploadNotice = findViewById(R.id.addNotice);
        addGalleryImage=findViewById(R.id.addGalleryImage);
        faculty=findViewById(R.id.faculty);
        requests=findViewById(R.id.requests);
        deleteFiles=findViewById(R.id.deleteFiles);
        logOut = findViewById(R.id.logOut);

        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        requests.setOnClickListener(this);
        faculty.setOnClickListener(this);
        deleteFiles.setOnClickListener(this);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement=new SessionManagement(AdminSide.this);
                sessionManagement.removeSession();
                Intent intent = new Intent(AdminSide.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addNotice){
            Intent intent = new Intent(AdminSide.this,UploadNotice.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.addGalleryImage){
            Intent intent = new Intent(AdminSide.this,UploadImage.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.requests){
                Intent intent = new Intent(AdminSide.this, Requests2.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("department",getIntent().getStringExtra("department"));
                intent.putExtra("value",getIntent().getStringExtra("value"));
                intent.putExtra("role",getIntent().getStringExtra("role"));
                AdminSide.this.startActivity(intent);
        }
        if(v.getId()==R.id.faculty){
            Intent intent = new Intent(AdminSide.this, UpdateFaculty.class);
            startActivity(intent);
        }

        if(v.getId()==R.id.deleteFiles){
                Intent intent = new Intent(AdminSide.this, DeleteFiles.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("department",getIntent().getStringExtra("department"));
                intent.putExtra("value",getIntent().getStringExtra("value"));
                intent.putExtra("role",getIntent().getStringExtra("role"));
                AdminSide.this.startActivity(intent);
        }
    }
}