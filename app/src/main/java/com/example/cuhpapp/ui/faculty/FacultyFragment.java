package com.example.cuhpapp.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cuhpapp.R;
import com.example.cuhpapp.faculty.FacultyAdapter;
import com.example.cuhpapp.faculty.FacultyData;
import com.example.cuhpapp.faculty.UpdateFaculty;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {
    private RecyclerView CSIDepartment,MathDepartment,LibDepartment,PhysicsDepartment;
    private LinearLayout CSINoData,MathNoData,LibNoData,PhysicsNoData,Content;
    private List<FacultyData> list1,list2,list3,list4;
    private FacultyAdapter2 adapter;
    private DatabaseReference reference,dbRef;

    ShimmerFrameLayout  frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_faculty, container, false);

        CSINoData=view.findViewById(R.id.CSINoData);
        MathNoData=view.findViewById(R.id.MathNoData);
        LibNoData=view.findViewById(R.id.LibNoData);
        Content = view.findViewById(R.id.content);
        frameLayout = view.findViewById(R.id.shimmer_layout);
        PhysicsNoData=view.findViewById(R.id.PhysicsNoData);
        CSIDepartment=view.findViewById(R.id.CSIDepartment);
        MathDepartment=view.findViewById(R.id.MathDepartment);
        LibDepartment=view.findViewById(R.id.LibDepartment);
        PhysicsDepartment=view.findViewById(R.id.PhysicsDepartment);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        CSIDepartment();
        MathDepartment();
        LibDepartment();
        PhysicsDepartment();
        return view;
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
                    Content.setVisibility(View.VISIBLE);
                    CSINoData.setVisibility(View.GONE);
                    CSIDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        FacultyData data=snapshot1.getValue(FacultyData.class);
                        list1.add(data);
                    }
                    CSIDepartment.setHasFixedSize(true);
                    CSIDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new FacultyAdapter2(list1,getContext(),"Computer Science & Informatics");
                    CSIDepartment.setAdapter(adapter);
                    frameLayout.stopShimmer();
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    MathDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new FacultyAdapter2(list2,getContext(),"Srinivasa Ramanujan Department of Mathematics");
                    MathDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    LibDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new FacultyAdapter2(list3,getContext(),"Department of Library & Information Sciences");
                    LibDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    PhysicsDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new FacultyAdapter2(list4,getContext(),"Physical & Astronomical Science");
                    PhysicsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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