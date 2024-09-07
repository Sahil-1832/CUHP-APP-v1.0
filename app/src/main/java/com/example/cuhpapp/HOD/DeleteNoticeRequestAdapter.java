package com.example.cuhpapp.HOD;

import android.content.Context;
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
import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DeleteNoticeRequestAdapter extends RecyclerView.Adapter<DeleteNoticeRequestAdapter.DeleteNoticeRequestViewAdapter>{

    private List<NoticeData> list;
    private Context context ;
    DatabaseReference reference;
    Button reject2,back;
    public DeleteNoticeRequestAdapter(List<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DeleteNoticeRequestAdapter.DeleteNoticeRequestViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_notice_request_hod,parent,false);
        return new DeleteNoticeRequestAdapter.DeleteNoticeRequestViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteNoticeRequestAdapter.DeleteNoticeRequestViewAdapter holder, int position) {
        NoticeData item = list.get(position);
            if (item.getTitle().length() > 18)
                holder.noticeTitle.setText(item.getTitle().substring(0, 18) + "...");
            else
                holder.noticeTitle.setText(item.getTitle());
            if (item.getText().length() > 26)
                holder.text.setText(item.getText().substring(0, 26) + "...");
            else
                holder.text.setText(item.getText());

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference = FirebaseDatabase.getInstance().getReference().child("NoticeHOD");
                    reference.child(item.getKey()).removeValue();
                    reference= FirebaseDatabase.getInstance().getReference().child("Deleted").child("DeletedNotices");
                    reference.child(item.getKey()).setValue(item);
                    reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                    reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                reference = FirebaseDatabase.getInstance().getReference().child("NoticeDEO");
                                reference.child(item.getKey()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                RejectedNoticesData data = new RejectedNoticesData(item.getTitle(),item.getUrl(),item.getKey(),item.getText(),item.getDate(),reason,true);
                                reference = FirebaseDatabase.getInstance().getReference().child("RejectedNotices");
                                reference.child(item.getKey()).setValue(data);
                                reference = FirebaseDatabase.getInstance().getReference().child("NoticeHOD");
                                reference.child(item.getKey()).removeValue();

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

    public class DeleteNoticeRequestViewAdapter extends RecyclerView.ViewHolder {
        TextView noticeTitle,text;
        Button accept,reject;
        public DeleteNoticeRequestViewAdapter(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    }
}
