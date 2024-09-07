package com.example.cuhpapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cuhpapp.MainActivity;
import com.example.cuhpapp.NoticeData;
import com.example.cuhpapp.R;
import com.example.cuhpapp.UploadImage;
import com.example.cuhpapp.UploadImageData;
import com.example.cuhpapp.UploadNotice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Addteacher extends AppCompatActivity {

    private ImageView addFacultyImage;
    private EditText addFacultyName,addFacultyEmail,addFacultyPosition;
    private static final int REQ =1 ;
    private Bitmap bitmap=null;
    private Button addFacultyBtn;
    private String department="",name,email,position,downloadUrl="";
    ProgressDialog pd;
    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);
        getSupportActionBar().setTitle("Add Faculty");

        addFacultyImage=findViewById(R.id.addFacultyImage);
        addFacultyName=findViewById(R.id.addFacultyName);
        addFacultyEmail=findViewById(R.id.addFacultyEmail);
        addFacultyPosition=findViewById(R.id.addFacultyPosition);
        autoCompleteTextView=findViewById(R.id.department);
        addFacultyBtn=findViewById(R.id.addFacultyBtn);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);

        String[] items= new String[]{"Computer Science & Informatics",
                "Srinivasa Ramanujan Department of Mathematics","Department of Library & Information Sciences","Physical & Astronomical Science"};
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                department=parent.getItemAtPosition(position).toString();
            }
        });

        addFacultyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addFacultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        name = addFacultyName.getText().toString();
        email = addFacultyEmail.getText().toString();
        position= addFacultyPosition.getText().toString();
        if(name.isEmpty()){
            addFacultyName.setError("Empty");
            addFacultyName.requestFocus();
        }else if(email.isEmpty()){
            addFacultyEmail.setError("Empty");
            addFacultyEmail.requestFocus();
        }else if(position.isEmpty()){
            addFacultyPosition.setError("Empty");
            addFacultyPosition.requestFocus();
        }else if(department.equals("")){
            Toast.makeText(this, "Please select a department", Toast.LENGTH_SHORT).show();
        }else if(bitmap==null){
            Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
        }else{
            pd.setMessage("Uploading...");
            pd.show();
            insertImage();
        }
    }

    private void insertData() {
        dbRef=reference.child(department);
        final String uniqueKey= dbRef.push().getKey();
        FacultyData facultyData = new FacultyData(name,email,position,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(facultyData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(Addteacher.this, "Faculty Details Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Addteacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg= baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Faculty").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(Addteacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                    Intent intent = new Intent (Addteacher.this, UpdateFaculty.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(Addteacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            addFacultyImage.setImageBitmap(bitmap);
        }
    }
}