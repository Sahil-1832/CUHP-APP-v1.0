package com.example.cuhpapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.SyllabusViewAdapter>{
    private List<NoticeData> list;
    private Context context;

    public SyllabusAdapter(List<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SyllabusViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.syllabus_layout,parent,false);
        return new SyllabusViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusAdapter.SyllabusViewAdapter holder, int position) {
        NoticeData item = list.get(position);
        if(item.getTitle().length()>40)
            holder.noticeTitle.setText(item.getTitle().substring(0,40)+"...");
        else
            holder.noticeTitle.setText(item.getTitle());
        if(item.getText().length()>70)
            holder.text.setText(item.getText().substring(0,70)+"...");
        else
            holder.text.setText(item.getText());

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(item.getUrl()), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent chooser = Intent.createChooser(intent, "Open PDF");
                context.startActivity(chooser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SyllabusViewAdapter extends RecyclerView.ViewHolder {

        private TextView noticeTitle,text;
        private CardView click;
        public SyllabusViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            text = itemView.findViewById(R.id.text);
            click = itemView.findViewById(R.id.click);
        }
    }
}
