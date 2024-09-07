package com.example.cuhpapp.DEO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RejectedEventsAdapter extends RecyclerView.Adapter<RejectedEventsAdapter.RejectedEventsViewAdapter>{

    private List<RejectedEventsData> list;
    private Context context ;
    DatabaseReference reference;

    public RejectedEventsAdapter(List<RejectedEventsData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RejectedEventsAdapter.RejectedEventsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reject_event_layout,parent,false);
        return new RejectedEventsAdapter.RejectedEventsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectedEventsAdapter.RejectedEventsViewAdapter holder, int position) {
        RejectedEventsData item = list.get(position);
        holder.category.setText(item.getCategory());
        try {
            Picasso.get().load(item.getImage()).into(holder.deleteImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.reason.setText(item.getReason());
        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Want to remove this ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference = FirebaseDatabase.getInstance().getReference().child("RejectedEvents");
                        reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog;
                try {
                    alertDialog = builder.create();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RejectedEventsViewAdapter extends RecyclerView.ViewHolder {
        TextView category,reason;
        Button ok;
        ImageView deleteImage;
        public RejectedEventsViewAdapter(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            reason = itemView.findViewById(R.id.reason);
            ok = itemView.findViewById(R.id.ok);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }
}
