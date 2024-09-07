package com.example.cuhpapp.DEO;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.Information;
import com.example.cuhpapp.Information2;
import com.example.cuhpapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewAdapter> {

    private List<Information> list;
    private Context context;
    private DatabaseReference reference,newReference;

    public InformationAdapter(List<Information> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InformationAdapter.InformationViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reques_layout,parent,false);
        return new InformationViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.InformationViewAdapter holder, int position) {
        Information item = list.get(position);
        holder.rollNo.setText(item.getRollNo());
        holder.email.setText(item.getEmail());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to activate this account ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newReference = FirebaseDatabase.getInstance().getReference().child("Activated").child("Student");
                        Information data = item;
                        newReference.child(item.getKey()).setValue(data);
                        reference = FirebaseDatabase.getInstance().getReference().child("SignUp").child("Student");
                        reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Account Activated", Toast.LENGTH_SHORT).show();
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

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to reject this request ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference = FirebaseDatabase.getInstance().getReference().child("SignUp").child("Student");
                        reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show();
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

    public class InformationViewAdapter extends RecyclerView.ViewHolder {

        TextView rollNo,email;
        Button accept,reject;
        public InformationViewAdapter(@NonNull View itemView) {
            super(itemView);

            rollNo = itemView.findViewById(R.id.rollNo);
            email = itemView.findViewById(R.id.email);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    }
}
