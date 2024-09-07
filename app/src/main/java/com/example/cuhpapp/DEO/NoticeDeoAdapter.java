package com.example.cuhpapp.DEO;

import android.content.Context;
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
import com.example.cuhpapp.delete.NoticeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NoticeDeoAdapter extends RecyclerView.Adapter<NoticeDeoAdapter.NoticeDeoViewAdapter>{
    private List<NoticeData> list;
    private Context context ;
    DatabaseReference reference;

    public NoticeDeoAdapter(List<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeDeoAdapter.NoticeDeoViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_notice_request,parent,false);
        return new NoticeDeoAdapter.NoticeDeoViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeDeoAdapter.NoticeDeoViewAdapter holder, int position) {
        NoticeData item = list.get(position);
            if (item.getTitle().length() > 18)
                holder.noticeTitle.setText(item.getTitle().substring(0, 18) + "...");
            else
                holder.noticeTitle.setText(item.getTitle());
            if (item.getText().length() > 26)
                holder.text.setText(item.getText().substring(0, 26) + "...");
            else
                holder.text.setText(item.getText());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(item.getUrl()), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Intent chooser = Intent.createChooser(intent, "Open PDF");
                    context.startActivity(chooser);
                }
            });
            holder.sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference = FirebaseDatabase.getInstance().getReference().child("NoticeHOD");
                    reference.child(item.getKey()).setValue(item);
                    reference = FirebaseDatabase.getInstance().getReference().child("NoticeDEO");
                    reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeDeoViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text;
        Button view,sendRequest;
        public NoticeDeoViewAdapter(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            view = itemView.findViewById(R.id.view);
            sendRequest = itemView.findViewById(R.id.sendRequest);
        }
    }
}
