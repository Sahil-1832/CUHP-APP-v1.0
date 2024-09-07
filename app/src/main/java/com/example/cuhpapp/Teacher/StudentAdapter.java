package com.example.cuhpapp.Teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.DEO.InformationAdapter;
import com.example.cuhpapp.Information;
import com.example.cuhpapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewAdapter>{
    private List<Information> list;
    private Context context;

    public StudentAdapter(List<Information> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_layout,parent,false);
        return new StudentAdapter.StudentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewAdapter holder, int position) {
        Information item = list.get(position);
            holder.rollNo.setText(item.getRollNo());
            holder.email.setText(item.getEmail());
            holder.department.setText(item.getDepartment());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentViewAdapter extends RecyclerView.ViewHolder {
        TextView rollNo,email,department;
        public StudentViewAdapter(@NonNull View itemView) {
            super(itemView);
            rollNo = itemView.findViewById(R.id.rollNo);
            email = itemView.findViewById(R.id.email);
            department = itemView.findViewById(R.id.department);
        }
    }
}
