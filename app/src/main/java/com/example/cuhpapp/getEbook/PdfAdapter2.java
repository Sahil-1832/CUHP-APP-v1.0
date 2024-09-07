package com.example.cuhpapp.getEbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuhpapp.R;
import com.example.cuhpapp.delete.PdfAdapter;
import com.example.cuhpapp.delete.PdfData;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class PdfAdapter2 extends RecyclerView.Adapter<PdfAdapter2.PdfViewAdapter> {
    private List<PdfData> list;
    private Context context;

    private DatabaseReference reference;

    public PdfAdapter2(List<PdfData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PdfAdapter2.PdfViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_ebook_layout,parent,false);
        return new PdfAdapter2.PdfViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter2.PdfViewAdapter holder, int position) {
        PdfData item = list.get(position);
        if(item.getTitle().length()>40)
            holder.ebookTitle.setText(item.getTitle().substring(0,40)+"...");
        else
            holder.ebookTitle.setText(item.getTitle());
        if(item.getText().length()>70)
            holder.text.setText(item.getText().substring(0,70)+"...");
        else
            holder.text.setText(item.getText());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(item.getUrl()), "*/*");
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

    public class PdfViewAdapter extends RecyclerView.ViewHolder {
        private TextView ebookTitle,text;
        private ImageButton download;
        public PdfViewAdapter(@NonNull View itemView) {
            super(itemView);
            ebookTitle = itemView.findViewById(R.id.ebookTitle);
            text = itemView.findViewById(R.id.text);
            download = itemView.findViewById(R.id.download);
        }
    }
}
