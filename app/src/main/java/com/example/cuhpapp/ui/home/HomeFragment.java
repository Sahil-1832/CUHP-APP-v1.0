package com.example.cuhpapp.ui.home;

import android.content.Context;
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

import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.delete.DeleteNotice;
import com.example.cuhpapp.delete.NoticeAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView homeNotice;
    private List<NoticeData> list;
    private NoticeAdapter2 adapter;
    private DatabaseReference reference;
    ShimmerFrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeNotice = view.findViewById(R.id.homeNotice);
        reference = FirebaseDatabase.getInstance().getReference().child("Notice");
        frameLayout = view.findViewById(R.id.shimmer_layout);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    homeNotice.setVisibility(View.GONE);
                }else
                {
                    homeNotice.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        NoticeData data=snapshot1.getValue(NoticeData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    homeNotice.setHasFixedSize(true);
                    homeNotice.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new NoticeAdapter2(list,getContext());
                    adapter.notifyDataSetChanged();
                    homeNotice.setAdapter(adapter);
                    frameLayout.stopShimmer();
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
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