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

public class DeleteNotice extends AppCompatActivity {

    private RecyclerView deleteNotice;
    private LinearLayout noticeNoData;
    private List<NoticeData>  list;
    private NoticeAdapter adapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
        getSupportActionBar().setTitle("Delete Notice");

        deleteNotice=findViewById(R.id.deleteNotice);
        noticeNoData=findViewById(R.id.noticeNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

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
                        NoticeData data=snapshot1.getValue(NoticeData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    deleteNotice.setHasFixedSize(true);
                    deleteNotice.setLayoutManager(new LinearLayoutManager(DeleteNotice.this));
                    adapter=new NoticeAdapter(list,DeleteNotice.this);
                    adapter.notifyDataSetChanged();
                    deleteNotice.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteNotice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}