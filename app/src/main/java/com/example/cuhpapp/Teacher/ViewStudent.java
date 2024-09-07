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

import com.example.cuhpapp.DEO.InformationAdapter;
import com.example.cuhpapp.DEO.Requests;
import com.example.cuhpapp.Information;
import com.example.cuhpapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudent extends AppCompatActivity {

    private RecyclerView getStudents;
    private LinearLayout noData;
    private List<Information> list;
    private StudentAdapter adapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_student);
        getSupportActionBar().setTitle("Student Details");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getStudents = findViewById(R.id.getStudents);
        noData = findViewById(R.id.noData);
        reference = FirebaseDatabase.getInstance().getReference().child("Activated").child("Student");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    noData.setVisibility(View.VISIBLE);
                    getStudents.setVisibility(View.GONE);
                }else{
                    noData.setVisibility(View.GONE);
                    getStudents.setVisibility(View.VISIBLE);
                    String department = getIntent().getStringExtra("department");
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        Information data = snapshot1.getValue(Information.class);
                        if(data.getDepartment().equals(department))
                            list.add(data);
                    }
                    getStudents.setHasFixedSize(true);
                    getStudents.setLayoutManager(new LinearLayoutManager(ViewStudent.this));
                    adapter = new StudentAdapter(list,ViewStudent.this);
                    adapter.notifyDataSetChanged();
                    getStudents.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewStudent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}