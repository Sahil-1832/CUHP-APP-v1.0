package com.example.cuhpapp.HOD;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.DEO.RejectedNoticesData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.Teacher.EbooksAdapter;
import com.example.cuhpapp.Teacher.RejectedEbooksData;
import com.example.cuhpapp.delete.PdfData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DeleteEbooksRequestAdapter extends RecyclerView.Adapter<DeleteEbooksRequestAdapter.DeleteEbooksRequestViewAdapter>{
    private List<PdfData> list;
    private Context context ;
    DatabaseReference databaseReference;

    public DeleteEbooksRequestAdapter(List<PdfData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DeleteEbooksRequestAdapter.DeleteEbooksRequestViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_notice_request_hod,parent,false);
        return new DeleteEbooksRequestAdapter.DeleteEbooksRequestViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteEbooksRequestAdapter.DeleteEbooksRequestViewAdapter holder, int position) {
        PdfData item = list.get(position);
            if (item.getTitle().length() > 18)
                holder.noticeTitle.setText(item.getTitle().substring(0, 18) + "...");
            else
                holder.noticeTitle.setText(item.getTitle());
            if (item.getText().length() > 62)
                holder.text.setText(item.getText().substring(0, 62) + "...");
            else
                holder.text.setText(item.getText());

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfHOD");
                    databaseReference.child(item.getKey()).removeValue();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Deleted").child("DeletedEbooks");
                    databaseReference.child(item.getKey()).setValue(item);
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("pdf");
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
            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.dialog,null);

                    final EditText getReason = (EditText)view.findViewById(R.id.reason);
                    Button reject2 = (Button)view.findViewById(R.id.reject2);
                    Button back = (Button)view.findViewById(R.id.back);

                    alert.setView(view);
                    AlertDialog alertDialog;
                    try {
                        alertDialog = alert.create();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    alertDialog.show();

                    reject2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String reason = getReason.getText().toString();
                            if(reason.equals("")){
                                getReason.setError("Can't be empty");
                                getReason.requestFocus();
                            }else{
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfHOD");
                                databaseReference.child(item.getKey()).removeValue();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfFaculty");
                                databaseReference.child(item.getKey()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("RejectedEbooks");
                                RejectedEbooksData data = new RejectedEbooksData(item.getTitle(),item.getUrl(),item.getKey(),item.getText(),reason);
                                databaseReference.child(item.getKey()).setValue(data);
                                alertDialog.dismiss();
                            }
                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeleteEbooksRequestViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text;
        Button accept,reject;
        public DeleteEbooksRequestViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    }
}
