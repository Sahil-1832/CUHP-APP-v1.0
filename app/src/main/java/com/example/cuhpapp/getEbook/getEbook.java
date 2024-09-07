package com.example.cuhpapp.getEbook;

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

import com.example.cuhpapp.R;
import com.example.cuhpapp.delete.PdfAdapter;
import com.example.cuhpapp.delete.PdfData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class getEbook extends AppCompatActivity {
    private RecyclerView getEbook;
    private List<PdfData> list;
    private PdfAdapter2 adapter;
    private DatabaseReference reference;
    ShimmerFrameLayout  frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ebook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ebooks");

        getEbook = findViewById(R.id.getEbook);
        frameLayout = findViewById(R.id.shimmer_layout);
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    getEbook.setVisibility(View.GONE);
                }else{
                    getEbook.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        PdfData data = snapshot1.getValue(PdfData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    getEbook.setHasFixedSize(true);
                    getEbook.setLayoutManager(new LinearLayoutManager(getEbook.this));
                    adapter = new PdfAdapter2(list,getEbook.this);
                    adapter.notifyDataSetChanged();
                    getEbook.setAdapter(adapter);
                    frameLayout.stopShimmer();
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getEbook.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onPause(){
        frameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume(){
        frameLayout.startShimmer();
        super.onResume();
    }
}