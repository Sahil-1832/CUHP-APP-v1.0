package com.example.cuhpapp.HOD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.DEO.EventsDeoAdapter;
import com.example.cuhpapp.DEO.RejectedEventsData;
import com.example.cuhpapp.DEO.RejectedNoticesData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImageData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DeleteImageRequestAdapter extends RecyclerView.Adapter<DeleteImageRequestAdapter.DeleteImageRequestViewAdapter>{
    private List<UploadImageData> list;
    private Context context ;

    DatabaseReference databaseReference;

    public DeleteImageRequestAdapter(List<UploadImageData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DeleteImageRequestAdapter.DeleteImageRequestViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.delete_image_request_hod,parent,false);
        return new DeleteImageRequestAdapter.DeleteImageRequestViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteImageRequestAdapter.DeleteImageRequestViewAdapter holder, int position) {
        UploadImageData item = list.get(position);
            holder.category.setText(item.getCategory());
            try {
                Picasso.get().load(item.getImage()).into(holder.deleteImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Deleted").child("DeletedEvents");
                    databaseReference.child(item.getKey()).setValue(item);
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("gallery");
                    databaseReference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, item.getCategory()+" image deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("galleryHOD");
                    databaseReference.child(item.getKey()).removeValue();
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
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("galleryDEO");
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
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("RejectedEvents");
                                RejectedEventsData data = new RejectedEventsData(item.getImage(),item.getKey(),item.getCategory(),item.getDate(),reason);
                                databaseReference.child(item.getKey()).setValue(data);
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("galleryHOD");
                                databaseReference.child(item.getKey()).removeValue();

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

    public class DeleteImageRequestViewAdapter extends RecyclerView.ViewHolder {
        private TextView category;
        private Button accept,reject;
        private ImageView deleteImage;
        public DeleteImageRequestViewAdapter(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }
}
