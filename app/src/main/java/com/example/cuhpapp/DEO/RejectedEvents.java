package com.example.cuhpapp.DEO;

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

import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RejectedEvents extends AppCompatActivity {

    private RecyclerView deleteImage;
    private LinearLayout imageNoData;
    private List<RejectedEventsData> list;
    private RejectedEventsAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rejected_events);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteImage = findViewById(R.id.deleteImage);
        imageNoData = findViewById(R.id.imageNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("RejectedEvents");

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
                        RejectedEventsData data = snapshot1.getValue(RejectedEventsData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteImage.setHasFixedSize(true);
                    deleteImage.setLayoutManager(new LinearLayoutManager(RejectedEvents.this));
                    adapter = new RejectedEventsAdapter(list,RejectedEvents.this);
                    adapter.notifyDataSetChanged();
                    deleteImage.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectedEvents.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}