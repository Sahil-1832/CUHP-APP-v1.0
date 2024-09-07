package com.example.cuhpapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cuhpapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateFacultyActivity extends AppCompatActivity {

    private ImageView updateFacultyImage;
    private EditText updateFacultyName,updateFacultyEmail,updateFacultyPosition;
    private Button updateFacultyBtn,deleteFacultyBtn;
    private String name,email,position,image,downloadUrl,uniqueKey,category;

    private final int REQ=1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Bitmap bitmap=null;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty2);
        getSupportActionBar().setTitle("Update Faculty Details");

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        position=getIntent().getStringExtra("position");
        image=getIntent().getStringExtra("image");
        uniqueKey=getIntent().getStringExtra("key");
        category =getIntent().getStringExtra("category");

        updateFacultyImage=findViewById(R.id.updateFacultyImage);
        updateFacultyName=findViewById(R.id.updateFacultyName);
        updateFacultyEmail=findViewById(R.id.updateFacultyEmail);
        updateFacultyPosition=findViewById(R.id.updateFacultyPosition);
        updateFacultyBtn=findViewById(R.id.updateFacultyBtn);
        deleteFacultyBtn=findViewById(R.id.deleteFacultyBtn);

        pd=new ProgressDialog(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();

        try {
            Picasso.get().load(image).into(updateFacultyImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateFacultyName.setText(name);
        updateFacultyEmail.setText(email);
        updateFacultyPosition.setText(position);

        updateFacultyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateFacultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=updateFacultyName.getText().toString();
                email=updateFacultyEmail.getText().toString();
                position=updateFacultyPosition.getText().toString();
                checkValidation();
            }
        });

        deleteFacultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        pd.setMessage("Deleting...");
        pd.show();
        databaseReference.child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateFacultyActivity.this, "Faculty Info Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (UpdateFacultyActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateFacultyActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        if(name.isEmpty()){
            updateFacultyName.setError("Empty");
            updateFacultyName.requestFocus();
        }else if(email.isEmpty()){
            updateFacultyEmail.setError("Empty");
            updateFacultyEmail.requestFocus();
        }else if(position.isEmpty()){
            updateFacultyPosition.setError("Empty");
            updateFacultyPosition.requestFocus();
        }else if(bitmap==null){
            updateData(image);
        }else{
            uploadImage();
        }
    }

    private void uploadImage() {
        pd.setMessage("Updating...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg= baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Faculty").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(UpdateFacultyActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pd.dismiss();
                                    downloadUrl=String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(UpdateFacultyActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("position",position);
        hp.put("image",s);


        databaseReference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateFacultyActivity.this, "Faculty Info Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (UpdateFacultyActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateFacultyActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
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
            updateFacultyImage.setImageBitmap(bitmap);
        }
    }
}