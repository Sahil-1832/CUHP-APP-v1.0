package com.example.cuhpapp.ui.gallery;

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
import com.example.cuhpapp.UploadImageData;
import com.example.cuhpapp.delete.DeleteImage;
import com.example.cuhpapp.delete.ImageAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView image;
    private List<UploadImageData> list;
    private ImageAdapter2 adapter;
    private DatabaseReference reference;
    ShimmerFrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);

        image=view.findViewById(R.id.deleteImage);
        frameLayout = view.findViewById(R.id.shimmer_layout);
        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                     image.setVisibility(View.GONE);
                }else{
                    image.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        UploadImageData data = snapshot1.getValue(UploadImageData.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    image.setHasFixedSize(true);
                    image.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new ImageAdapter2(list,getContext());
                    adapter.notifyDataSetChanged();
                    image.setAdapter(adapter);
                    frameLayout.stopShimmer();
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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