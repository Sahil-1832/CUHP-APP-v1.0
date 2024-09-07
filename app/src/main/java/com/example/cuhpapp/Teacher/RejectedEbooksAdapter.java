package com.example.cuhpapp.Teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.DEO.RejectedNoticesAdapter;
import com.example.cuhpapp.DEO.RejectedNoticesData;
import com.example.cuhpapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RejectedEbooksAdapter extends RecyclerView.Adapter<RejectedEbooksAdapter.RejectedEbooksViewAdapter>{

    private List<RejectedEbooksData> list;
    private Context context;
    DatabaseReference reference;

    public RejectedEbooksAdapter(List<RejectedEbooksData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RejectedEbooksAdapter.RejectedEbooksViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reject_notice_layout,parent,false);
        return new RejectedEbooksAdapter.RejectedEbooksViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectedEbooksAdapter.RejectedEbooksViewAdapter holder, int position) {
        RejectedEbooksData item = list.get(position);
        if (item.getTitle().length() > 18)
            holder.noticeTitle.setText(item.getTitle().substring(0, 18) + "...");
        else
            holder.noticeTitle.setText(item.getTitle());
        if (item.getText().length() > 26)
            holder.text.setText(item.getText().substring(0, 26) + "...");
        else
            holder.text.setText(item.getText());
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
                        reference = FirebaseDatabase.getInstance().getReference().child("RejectedEbooks");
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

    public class RejectedEbooksViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text,reason;
        Button ok;
        public RejectedEbooksViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle=itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            reason = itemView.findViewById(R.id.reason);
            ok = itemView.findViewById(R.id.ok);
        }
    }
}
