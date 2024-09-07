package com.example.cuhpapp.Teacher;

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
import com.example.cuhpapp.delete.PdfAdapter;
import com.example.cuhpapp.delete.PdfData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EbooksAdapter extends RecyclerView.Adapter<EbooksAdapter.EbooksViewAdapter>{
    private List<PdfData> list;
    private Context context ;
    DatabaseReference reference;

    public EbooksAdapter(List<PdfData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EbooksAdapter.EbooksViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_notice_request,parent,false);
        return new EbooksAdapter.EbooksViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbooksAdapter.EbooksViewAdapter holder, int position) {
        PdfData item = list.get(position);
        if(item.getFlag()) {
            if (item.getTitle().length() > 18)
                holder.noticeTitle.setText(item.getTitle().substring(0, 18) + "...");
            else
                holder.noticeTitle.setText(item.getTitle());
            if (item.getText().length() > 62)
                holder.text.setText(item.getText().substring(0, 62) + "...");
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
                    reference = FirebaseDatabase.getInstance().getReference().child("pdfHOD");
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
                    reference = FirebaseDatabase.getInstance().getReference().child("pdfFaculty");
                    reference.child(item.getKey()).removeValue();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbooksViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text;
        Button view,sendRequest;
        public EbooksViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            view = itemView.findViewById(R.id.view);
            sendRequest = itemView.findViewById(R.id.sendRequest);
        }
    }
}
