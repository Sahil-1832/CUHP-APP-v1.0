package com.example.cuhpapp.DEO;

import android.os.Bundle;
import android.text.Layout;
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

import com.example.cuhpapp.Information;
import com.example.cuhpapp.Information2;
import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.SuperAdmin.InformationAdapter2;
import com.example.cuhpapp.SyllabusAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Requests extends AppCompatActivity {
    private RecyclerView getRequests;
    private LinearLayout noRequest;
    private List<Information> list;
    private InformationAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);
        getSupportActionBar().setTitle("Requests");


        getRequests = findViewById(R.id.getRequests);
        noRequest = findViewById(R.id.noRequests);
        reference = FirebaseDatabase.getInstance().getReference().child("SignUp").child("Student");

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
                    String department = getIntent().getStringExtra("department");
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        Information data = snapshot1.getValue(Information.class);
                        if(department.equals(data.getDepartment()))
                            list.add(data);
                    }
                    getRequests.setHasFixedSize(true);
                    getRequests.setLayoutManager(new LinearLayoutManager(Requests.this));
                    adapter = new InformationAdapter(list,Requests.this);
                    adapter.notifyDataSetChanged();
                    getRequests.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Requests.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}