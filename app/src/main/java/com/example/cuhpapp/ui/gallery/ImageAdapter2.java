package com.example.cuhpapp.ui.gallery;

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
import com.example.cuhpapp.delete.ImageAdapter;
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

public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.ImageViewAdapter> {

    private List<UploadImageData> list;
    private Context context;

    public ImageAdapter2(List<UploadImageData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.image_layout,parent,false);
        return new ImageViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter2.ImageViewAdapter holder, int position) {
        UploadImageData item = list.get(position);
        holder.date.setText(item.getDate());
        holder.category.setText(item.getCategory());
        try {
            Picasso.get().load(item.getImage()).into(holder.deleteImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewAdapter extends RecyclerView.ViewHolder {
        private TextView category,date;
        private ImageView deleteImage;
        public ImageViewAdapter(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            deleteImage = itemView.findViewById(R.id.deleteImage);
            date=itemView.findViewById(R.id.date);
        }
    }
}

