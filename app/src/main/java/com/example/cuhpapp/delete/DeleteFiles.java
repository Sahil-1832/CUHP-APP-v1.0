package com.example.cuhpapp.delete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cuhpapp.AdminSide;
import com.example.cuhpapp.DEO.EventsDeo;
import com.example.cuhpapp.DEO.NoticeDeo;
import com.example.cuhpapp.DEO.Requests;
import com.example.cuhpapp.R;

public class DeleteFiles extends AppCompatActivity implements View.OnClickListener {

    CardView deleteNotice,deleteImage,deleteEbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_files);
        getSupportActionBar().setTitle("Delete Files");


        deleteNotice=findViewById(R.id.deleteNotice);
        deleteImage=findViewById(R.id.deleteImage);
        deleteEbook=findViewById(R.id.deleteEbook);

        deleteNotice.setOnClickListener(this);
        deleteImage.setOnClickListener(this);
        deleteEbook.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.deleteNotice){
                Intent intent = new Intent(DeleteFiles.this, DeleteNotice.class);
                startActivity(intent);
        }
        if(v.getId()==R.id.deleteImage){
                Intent intent = new Intent(DeleteFiles.this, DeleteImage.class);
                startActivity(intent);
        }
        if(v.getId()==R.id.deleteEbook){
            Intent intent=new Intent(DeleteFiles.this,DeleteEbook.class);
            startActivity(intent);
        }
    }
}