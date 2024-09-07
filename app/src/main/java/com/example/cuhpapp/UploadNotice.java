package com.example.cuhpapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class UploadNotice extends AppCompatActivity {

    private CardView addNotice;
    private final int REQ = 1;
    private EditText noticeTitle;
    private Uri noticeData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Button uploadNoticeBtn;
    private TextView noticeTextView;
    String noticeName ="",title="";
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        getSupportActionBar().setTitle("Upload Notice");

        addNotice=findViewById(R.id.addPdf);
        noticeTitle=findViewById(R.id.pdfTitle);
        uploadNoticeBtn=findViewById(R.id.uploadPdfBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        noticeTextView=findViewById(R.id.pdfTextView);
        pd = new ProgressDialog(this);

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=noticeTitle.getText().toString();
                if(title.isEmpty()){
                    noticeTitle.setError("Please add a title");
                    noticeTitle.requestFocus();
                }else if(noticeData==null){
                    Toast.makeText(UploadNotice.this, "Please select a file", Toast.LENGTH_SHORT).show();
                }else{
                    uploadPdf();
                    Handler handler=new Handler(Looper.getMainLooper());
                    Intent intent = new Intent (UploadNotice.this, AdminSide.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    },5000);
                }
            }
        });
    }

    private void uploadPdf() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading file...");
        pd.show();
        StorageReference reference= storageReference.child("Notice/"+noticeName+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(noticeData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String s) {
        String uniqueKey=databaseReference.child("Notice").push().getKey();
        DateTimeFormatter formatter=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now().format(formatter).toString();
        }
        NoticeData data = new NoticeData(noticeTitle.getText().toString(),s,uniqueKey,noticeName,date,true);
        databaseReference.child("NoticeDEO").child(uniqueKey).setValue(data);
        databaseReference.child("Notice").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "File Uploaded successfully", Toast.LENGTH_SHORT).show();
                noticeTitle.setText("");
                noticeTextView.setText("No file Selected");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf file"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            noticeData =data.getData();
            if(noticeData.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadNotice.this.getContentResolver().query(noticeData,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()) {
                        noticeName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(noticeData.toString().startsWith("file://")){
                noticeName=new File(noticeData.toString()).getName();
            }
            noticeTextView.setText(noticeName);
        }
    }
}