package com.example.cuhpapp.delete;

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

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewAdapter> {


    private List<PdfData> list;
    private Context context ;

    private DatabaseReference databaseReference;

    public PdfAdapter(List<PdfData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public PdfViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item_layout,parent,false);
        return new PdfViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.PdfViewAdapter holder, int position) {
        PdfData item = list.get(position);
        if(item.getTitle().length()>13)
            holder.noticeTitle.setText(item.getTitle().substring(0,13)+"...");
        else
            holder.noticeTitle.setText(item.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(item.getUrl()),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent chooser = Intent.createChooser(intent, "Open File");
                context.startActivity(chooser);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this Ebook ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("pdf");
                        databaseReference.child(item.getKey()).removeValue();
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("pdfHOD");
                        databaseReference.child(item.getKey()).removeValue();
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("Deleted").child("DeletedEbooks");
                        databaseReference.child(item.getKey()).setValue(item);
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("pdfDEO");
                        databaseReference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, item.getTitle()+" deleted successfully", Toast.LENGTH_SHORT).show();
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

    public class PdfViewAdapter extends RecyclerView.ViewHolder {

        private TextView noticeTitle;
        private Button delete,view;
        public PdfViewAdapter(@NonNull View itemView) {

            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            delete = itemView.findViewById(R.id.delete);
            view = itemView.findViewById(R.id.view);
        }
    }
}
