package com.example.cuhpapp.SuperAdmin;

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

import com.example.cuhpapp.Information2;
import com.example.cuhpapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Requests2 extends AppCompatActivity {
    private RecyclerView getRequests;
    private LinearLayout noRequest;
    private List<Information2> list;
    private InformationAdapter2 adapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests2);
        getSupportActionBar().setTitle("Requests");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requests");

        getRequests = findViewById(R.id.getRequests);
        noRequest = findViewById(R.id.noRequests);
        reference = FirebaseDatabase.getInstance().getReference().child("SignUp").child("Faculty");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    noRequest.setVisibility(View.VISIBLE);
                    getRequests.setVisibility(View.GONE);
                }else{
                    noRequest.setVisibility(View.GONE);
                    getRequests.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        Information2 data = snapshot1.getValue(Information2.class);
                        list.add(data);
                    }
                    getRequests.setHasFixedSize(true);
                    getRequests.setLayoutManager(new LinearLayoutManager(Requests2.this));
                    adapter = new InformationAdapter2(list,Requests2.this);
                    adapter.notifyDataSetChanged();
                    getRequests.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Requests2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}