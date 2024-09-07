package com.example.cuhpapp.DEO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RejectedNoticesAdapter extends RecyclerView.Adapter<RejectedNoticesAdapter.RejectedNoticesViewAdapter>{
    private List<RejectedNoticesData> list;
    private Context context;
    DatabaseReference reference;
    public RejectedNoticesAdapter(List<RejectedNoticesData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RejectedNoticesAdapter.RejectedNoticesViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reject_notice_layout,parent,false);
        return new RejectedNoticesAdapter.RejectedNoticesViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectedNoticesAdapter.RejectedNoticesViewAdapter holder, int position) {
        RejectedNoticesData item = list.get(position);
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
                        reference = FirebaseDatabase.getInstance().getReference().child("RejectedNotices");
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

    public class RejectedNoticesViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text,reason;
        Button ok;
        public RejectedNoticesViewAdapter(@NonNull View itemView) {
            super(itemView);
            noticeTitle=itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            reason = itemView.findViewById(R.id.reason);
            ok = itemView.findViewById(R.id.ok);
        }
    }
}
