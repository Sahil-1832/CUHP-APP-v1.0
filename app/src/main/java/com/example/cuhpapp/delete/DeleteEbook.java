package com.example.cuhpapp.delete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteEbook extends AppCompatActivity {

    private RecyclerView deleteEbook;
    private LinearLayout ebookNoData;
    private List<PdfData> list;
    private PdfAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ebook);
        getSupportActionBar().setTitle("Delete Ebooks");

        deleteEbook = findViewById(R.id.deleteEbook);
        ebookNoData = findViewById(R.id.ebookNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");

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
                        PdfData data=snapshot1.getValue(PdfData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteEbook.setHasFixedSize(true);
                    deleteEbook.setLayoutManager(new LinearLayoutManager(DeleteEbook.this));
                    adapter=new PdfAdapter(list,DeleteEbook.this);
                    adapter.notifyDataSetChanged();
                    deleteEbook.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteEbook.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}