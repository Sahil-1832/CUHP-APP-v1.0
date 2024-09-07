package com.example.cuhpapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cuhpapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView CSIDepartment,MathDepartment,LibDepartment,PhysicsDepartment;
    private LinearLayout CSINoData,MathNoData,LibNoData,PhysicsNoData;
    private List<FacultyData> list1,list2,list3,list4;
    private FacultyAdapter adapter;
    private DatabaseReference reference,dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        getSupportActionBar().setTitle("Faculty Details");

        fab=findViewById(R.id.fab);
        CSINoData=findViewById(R.id.CSINoData);
        MathNoData=findViewById(R.id.MathNoData);
        LibNoData=findViewById(R.id.LibNoData);
        PhysicsNoData=findViewById(R.id.PhysicsNoData);
        CSIDepartment=findViewById(R.id.CSIDepartment);
        MathDepartment=findViewById(R.id.MathDepartment);
        LibDepartment=findViewById(R.id.LibDepartment);
        PhysicsDepartment=findViewById(R.id.PhysicsDepartment);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        CSIDepartment();
        MathDepartment();
        LibDepartment();
        PhysicsDepartment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this,Addteacher.class));
            }
        });
    }

    private void CSIDepartment() {
        dbRef=reference.child("Computer Science & Informatics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if(!snapshot.exists()){
                    CSINoData.setVisibility(View.VISIBLE);
                    CSIDepartment.setVisibility(View.GONE);
                }else{
                    CSINoData.setVisibility(View.GONE);
                    CSIDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data=snapshot1.getValue(FacultyData.class);
                        list1.add(data);
                    }
                    CSIDepartment.setHasFixedSize(true);
                    CSIDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new FacultyAdapter(list1,UpdateFaculty.this,"Computer Science & Informatics");
                    CSIDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void MathDepartment() {
        dbRef=reference.child("Srinivasa Ramanujan Department of Mathematics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(!snapshot.exists()){
                    MathNoData.setVisibility(View.VISIBLE);
                    MathDepartment.setVisibility(View.GONE);
                }else{
                    MathNoData.setVisibility(View.GONE);
                    MathDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data=snapshot1.getValue(FacultyData.class);
                        list2.add(data);
                    }
                    MathDepartment.setHasFixedSize(true);
                    MathDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new FacultyAdapter(list2,UpdateFaculty.this,"Srinivasa Ramanujan Department of Mathematics");
                    MathDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LibDepartment() {
        dbRef=reference.child("Department of Library & Information Sciences");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(!snapshot.exists()){
                    LibNoData.setVisibility(View.VISIBLE);
                    LibDepartment.setVisibility(View.GONE);
                }else{
                    LibNoData.setVisibility(View.GONE);
                    LibDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data=snapshot1.getValue(FacultyData.class);
                        list3.add(data);
                    }
                    LibDepartment.setHasFixedSize(true);
                    LibDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new FacultyAdapter(list3,UpdateFaculty.this,"Department of Library & Information Sciences");
                    LibDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void PhysicsDepartment() {
        dbRef=reference.child("Physical & Astronomical Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if(!snapshot.exists()){
                    PhysicsNoData.setVisibility(View.VISIBLE);
                    PhysicsDepartment.setVisibility(View.GONE);
                }else{
                    PhysicsNoData.setVisibility(View.GONE);
                    PhysicsDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data=snapshot1.getValue(FacultyData.class);
                        list4.add(data);
                    }
                    PhysicsDepartment.setHasFixedSize(true);
                    PhysicsDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new FacultyAdapter(list4,UpdateFaculty.this,"Physical & Astronomical Science");
                    PhysicsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}