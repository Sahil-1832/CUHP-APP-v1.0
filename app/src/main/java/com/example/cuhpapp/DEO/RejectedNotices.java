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

public class RejectedNotices extends AppCompatActivity {
    private RecyclerView deleteNotice;
    private LinearLayout noticeNoData;
    private List<RejectedNoticesData> list;
    private RejectedNoticesAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rejected_notices);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteNotice=findViewById(R.id.deleteNotice);
        noticeNoData=findViewById(R.id.noticeNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("RejectedNotices");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    noticeNoData.setVisibility(View.VISIBLE);
                    deleteNotice.setVisibility(View.GONE);
                }else
                {
                    noticeNoData.setVisibility(View.GONE);
                    deleteNotice.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        RejectedNoticesData data=snapshot1.getValue(RejectedNoticesData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteNotice.setHasFixedSize(true);
                    deleteNotice.setLayoutManager(new LinearLayoutManager(RejectedNotices.this));
                    adapter=new RejectedNoticesAdapter(list,RejectedNotices.this);
                    adapter.notifyDataSetChanged();
                    deleteNotice.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectedNotices.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}