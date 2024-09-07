package com.example.cuhpapp.Teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.DEO.RejectedEventsData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.delete.PdfData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RejectedEbooks extends AppCompatActivity {
    private RecyclerView deleteEbook;
    private LinearLayout ebookNoData;
    private List<RejectedEbooksData> list;
    private RejectedEbooksAdapter adapter;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rejected_ebooks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteEbook = findViewById(R.id.deleteEbook);
        ebookNoData = findViewById(R.id.ebookNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("RejectedEbooks");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    ebookNoData.setVisibility(View.VISIBLE);
                    deleteEbook.setVisibility(View.GONE);
                }
                else{
                    ebookNoData.setVisibility(View.GONE);
                    deleteEbook.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        RejectedEbooksData data=snapshot1.getValue(RejectedEbooksData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteEbook.setHasFixedSize(true);
                    deleteEbook.setLayoutManager(new LinearLayoutManager(RejectedEbooks.this));
                    adapter=new RejectedEbooksAdapter(list,RejectedEbooks.this);
                    adapter.notifyDataSetChanged();
                    deleteEbook.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectedEbooks.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}