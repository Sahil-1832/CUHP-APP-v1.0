package com.example.cuhpapp.ui.home;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImageData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticeAdapter2 extends RecyclerView.Adapter<NoticeAdapter2.NoticeViewAdapter> {

    private List<NoticeData> list;
    private Context context ;


    public NoticeAdapter2(List<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        NoticeData item = list.get(position);
        if(item.getTitle().length()>40)
            holder.noticeTitle.setText(item.getTitle().substring(0,40)+"...");
        else
            holder.noticeTitle.setText(item.getTitle());
        if(item.getText().length()>70)
            holder.text.setText(item.getText().substring(0,70)+"...");
        else
            holder.text.setText(item.getText());
        holder.date.setText(item.getDate());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Want to download this notice ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(item.getUrl()), "*/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        Intent chooser = Intent.createChooser(intent, "Open PDF");
                        context.startActivity(chooser);
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

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView noticeTitle,text,date;
        private LinearLayout click;
        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            click = itemView.findViewById(R.id.click);
            noticeTitle=itemView.findViewById(R.id.noticeTitle);
            text=itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
        }
    }
}
