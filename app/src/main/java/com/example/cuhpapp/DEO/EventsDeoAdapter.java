package com.example.cuhpapp.DEO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImageData;
import com.example.cuhpapp.delete.ImageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsDeoAdapter extends RecyclerView.Adapter<EventsDeoAdapter.EventsDeoViewAdapter>{
    private List<UploadImageData> list;
    private Context context ;
    DatabaseReference reference;

    public EventsDeoAdapter(List<UploadImageData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsDeoAdapter.EventsDeoViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.event_deo_layout,parent,false);
        return new EventsDeoAdapter.EventsDeoViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsDeoAdapter.EventsDeoViewAdapter holder, int position) {
        UploadImageData item = list.get(position);
            holder.category.setText(item.getCategory());
            try {
                Picasso.get().load(item.getImage()).into(holder.deleteImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            holder.sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference = FirebaseDatabase.getInstance().getReference().child("galleryHOD");
                    reference.child(item.getKey()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Request Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    reference = FirebaseDatabase.getInstance().getReference().child("galleryDEO");
                    reference.child(item.getKey()).removeValue();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventsDeoViewAdapter extends RecyclerView.ViewHolder {
        private TextView category;
        private Button sendRequest;
        private ImageView deleteImage;
        public EventsDeoViewAdapter(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            sendRequest = itemView.findViewById(R.id.sendRequest);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }
}
