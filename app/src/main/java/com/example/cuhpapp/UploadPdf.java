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

import com.example.cuhpapp.Teacher.Faculty;
import com.example.cuhpapp.delete.PdfData;
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
import java.util.HashMap;

public class UploadPdf extends AppCompatActivity {

    private CardView addPdf;
    private final int REQ = 1;
    private EditText pdfTitle;
    private Uri pdfData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Button uploadPdfBtn;
    private TextView pdfTextView;
    String pdfName="",title="";
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        getSupportActionBar().setTitle("Upload Ebooks");

        addPdf=findViewById(R.id.addPdf);
        pdfTitle=findViewById(R.id.pdfTitle);
        uploadPdfBtn=findViewById(R.id.uploadPdfBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        pdfTextView=findViewById(R.id.pdfTextView);
        pd = new ProgressDialog(this);

        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=pdfTitle.getText().toString();
                if(title.isEmpty()){
                    pdfTitle.setError("Please add a title");
                    pdfTitle.requestFocus();
                }else if(pdfData==null){
                    Toast.makeText(UploadPdf.this, "Please upload a file", Toast.LENGTH_SHORT).show();
                }else{
                    uploadPdf();
                    Handler handler=new Handler(Looper.getMainLooper());
                    Intent intent = new Intent (UploadPdf.this, Faculty.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    },3000);
                }
            }
        });
    }

    private void uploadPdf() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading file...");
        pd.show();
        StorageReference reference = storageReference.child("pdf/" + pdfName + "-" + System.currentTimeMillis() + ".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadData(String s) {
        String uniqueKey=databaseReference.child("pdf").push().getKey();
        PdfData data = new PdfData(title,s,uniqueKey,pdfName,true);
        databaseReference.child("pdfFaculty").child(uniqueKey).setValue(data);
        databaseReference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "File Uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("*/*");
        String[] types={"application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,types);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf file"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
           pdfData=data.getData();
           if(pdfData.toString().startsWith("content://")){
               Cursor cursor=null;
               try {
                   cursor=UploadPdf.this.getContentResolver().query(pdfData,null,null,null,null);
                   if(cursor!=null && cursor.moveToFirst()) {
                       pdfName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                   }
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
           }else if(pdfData.toString().startsWith("file://")){
               pdfName=new File(pdfData.toString()).getName();
           }
           pdfTextView.setText(pdfName);
        }
    }
}