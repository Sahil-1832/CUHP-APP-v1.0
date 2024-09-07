package com.example.cuhpapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.delete.NoticeAdapter;
import com.example.cuhpapp.delete.PdfData;
import com.example.cuhpapp.getEbook.PdfAdapter2;
import com.example.cuhpapp.getEbook.getEbook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Syllabus extends AppCompatActivity {

    private RecyclerView getSyllabus;
    private List<NoticeData> list;
    private SyllabusAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_syllabus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Syllabus");

        getSyllabus = findViewById(R.id.getSyllabus);
        reference = FirebaseDatabase.getInstance().getReference().child("Syllabus");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    getSyllabus.setVisibility(View.GONE);
                }else{
                    getSyllabus.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    getSyllabus.setHasFixedSize(true);
                    getSyllabus.setLayoutManager(new LinearLayoutManager(Syllabus.this));
                    adapter = new SyllabusAdapter(list,Syllabus.this);
                    adapter.notifyDataSetChanged();
                    getSyllabus.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Syllabus.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}