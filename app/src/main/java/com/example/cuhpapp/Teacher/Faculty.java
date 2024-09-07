package com.example.cuhpapp.Teacher;

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
import com.example.cuhpapp.MainActivity;
import com.example.cuhpapp.R;
import com.example.cuhpapp.Session.SessionManagement;
import com.example.cuhpapp.UploadPdf;
import com.example.cuhpapp.delete.DeleteEbook;

public class Faculty extends AppCompatActivity{

    CardView uploadEresources,studentDetails,deleteEbooks,rejectedEbooks;
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
        setContentView(R.layout.activity_faculty);

        uploadEresources = findViewById(R.id.uploadEresources);
        studentDetails = findViewById(R.id.studentDetails);
        deleteEbooks = findViewById(R.id.deleteEbooks);
        logOut = findViewById(R.id.logOut);
        rejectedEbooks = findViewById(R.id.rejectedEbooks);

        uploadEresources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Faculty.this, UploadPdf.class);
                startActivity(intent);
            }
        });
        studentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Faculty.this, ViewStudent.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("department",getIntent().getStringExtra("department"));
                intent.putExtra("value",getIntent().getStringExtra("value"));
                intent.putExtra("role",getIntent().getStringExtra("role"));
                Faculty.this.startActivity(intent);
            }
        });
        
        deleteEbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Faculty.this, EbooksFaculty.class);
                startActivity(intent);
            }
        });


        rejectedEbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Faculty.this,RejectedEbooks.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement=new SessionManagement(Faculty.this);
                sessionManagement.removeSession();
                Intent intent = new Intent(Faculty.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}