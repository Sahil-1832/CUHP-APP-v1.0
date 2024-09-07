package com.example.cuhpapp.ui.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.R;
import com.example.cuhpapp.faculty.FacultyAdapter;
import com.example.cuhpapp.faculty.FacultyData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FacultyAdapter2 extends RecyclerView.Adapter<FacultyAdapter2.FacultyViewAdapter> {

    private List<FacultyData> list;
    private Context context ;
    private String category;

    public FacultyAdapter2(List<FacultyData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }
    @NonNull
    @Override
    public FacultyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_layout,parent,false);
        return new FacultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewAdapter holder, int position) {
        FacultyData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.position.setText(item.getPosition());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FacultyViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,position;
        private ImageView imageView;
        public FacultyViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.facultyName);
            email = itemView.findViewById(R.id.facultyEmail);
            position=itemView.findViewById(R.id.facultyPosition);
            imageView=itemView.findViewById(R.id.facultyImage);
        }
    }
}
