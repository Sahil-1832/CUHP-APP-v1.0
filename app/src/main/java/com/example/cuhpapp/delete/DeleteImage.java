package com.example.cuhpapp.delete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImage;
import com.example.cuhpapp.UploadImageData;
import com.example.cuhpapp.faculty.FacultyAdapter;
import com.example.cuhpapp.faculty.FacultyData;
import com.example.cuhpapp.faculty.UpdateFaculty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteImage extends AppCompatActivity {

    private RecyclerView deleteImage;
    private LinearLayout imageNoData;
    private List<UploadImageData> list;
    private ImageAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_image);
        getSupportActionBar().setTitle("Delete Image");

        deleteImage = findViewById(R.id.deleteImage);
        imageNoData = findViewById(R.id.imageNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if (!snapshot.exists()) {
                    imageNoData.setVisibility(View.VISIBLE);
                    deleteImage.setVisibility(View.GONE);
                } else {
                    imageNoData.setVisibility(View.GONE);
                    deleteImage.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        UploadImageData data = snapshot1.getValue(UploadImageData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteImage.setHasFixedSize(true);
                    deleteImage.setLayoutManager(new LinearLayoutManager(DeleteImage.this));
                    adapter = new ImageAdapter(list,DeleteImage.this);
                    adapter.notifyDataSetChanged();
                    deleteImage.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteImage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}