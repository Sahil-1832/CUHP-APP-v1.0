package com.example.cuhpapp.delete;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
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
import com.example.cuhpapp.faculty.FacultyAdapter;
import com.example.cuhpapp.faculty.FacultyData;
import com.example.cuhpapp.faculty.UpdateFaculty;
import com.example.cuhpapp.faculty.UpdateFacultyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewAdapter> {
    private List<UploadImageData> list;
    private Context context ;
    private DatabaseReference databaseReference;
    public ImageAdapter(List<UploadImageData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.delete_image_layout,parent,false);
        return new ImageViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewAdapter holder, int position) {
        UploadImageData item = list.get(position);
        holder.category.setText(item.getCategory());
        try {
            Picasso.get().load(item.getImage()).into(holder.deleteImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this image ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("gallery");
                        databaseReference.child(item.getKey()).removeValue();
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("galleryHOD");
                        databaseReference.child(item.getKey()).removeValue();
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("Deleted").child("DeletedEvents");
                        databaseReference.child(item.getKey()).setValue(item);
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("galleryDEO");
                        databaseReference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, item.getCategory()+" image deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

    public class ImageViewAdapter extends RecyclerView.ViewHolder {

        private TextView category;
        private Button delete;
        private ImageView deleteImage;
        public ImageViewAdapter(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category);
            delete=itemView.findViewById(R.id.delete);
            deleteImage=itemView.findViewById(R.id.deleteImage);
        }
    }
}
