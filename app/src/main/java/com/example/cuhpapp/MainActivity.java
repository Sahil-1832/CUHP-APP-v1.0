package com.example.cuhpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.view.contentcapture.DataShareRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuhpapp.DEO.DEOSide;
import com.example.cuhpapp.HOD.HODSide;
import com.example.cuhpapp.Session.SessionManagement;
import com.example.cuhpapp.Teacher.Faculty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView signUp, guest;
    EditText loginEmail, loginPassword;
    String email, password;
    Button login;
    Map<String, String> list;
    List<Information2> list2;
    boolean flag=false;
    DatabaseReference reference;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected  void onStart(){
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        Information2 pass = sessionManagement.getSession();
        if(pass!=null){
            if(pass.getRole().equals("Student")){
                Intent intent = new Intent(MainActivity.this,StudentSide.class);
                startActivity(intent);
            }else if(pass.getRole().equals("DEO")){
                Intent intent = new Intent(MainActivity.this,DEOSide.class);
                intent.putExtra("email",pass.getEmail());
                intent.putExtra("department",pass.getDepartment());
                intent.putExtra("role",pass.getRole());
                intent.putExtra("value",pass.getValue());
                startActivity(intent);
            }else if(pass.getRole().equals("Faculty")){
                Intent intent = new Intent(MainActivity.this,Faculty.class);
                intent.putExtra("email",pass.getEmail());
                intent.putExtra("department",pass.getDepartment());
                intent.putExtra("role",pass.getRole());
                intent.putExtra("value",pass.getValue());
                startActivity(intent);
            }else if(pass.getRole().equals("HOD")){
                Intent intent = new Intent(MainActivity.this,HODSide.class);
                intent.putExtra("email",pass.getEmail());
                intent.putExtra("department",pass.getDepartment());
                intent.putExtra("role",pass.getRole());
                intent.putExtra("value",pass.getValue());
                startActivity(intent);
            }else if(pass.getRole().equals("Super Admin")){
                Intent intent = new Intent(MainActivity.this,AdminSide.class);
                intent.putExtra("email",pass.getEmail());
                intent.putExtra("department",pass.getDepartment());
                intent.putExtra("role",pass.getRole());
                intent.putExtra("value",pass.getValue());
                startActivity(intent);
            }
        }else{

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        signUp = findViewById(R.id.signUp);
        guest = findViewById(R.id.guest);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login);
        reference = FirebaseDatabase.getInstance().getReference().child("Activated");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = loginEmail.getText().toString();
                password = loginPassword.getText().toString();
                reference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list = new HashMap<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Information data = snapshot1.getValue(Information.class);
                            list.put(data.getEmail(), data.getPassword());
                        }

                        if (list.containsKey(email) && list.size() != 0) {
                            String res = list.get(email);
                            if (res.equals(password)) {
                                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                Information2 data = new Information2(email,"",password,"Student","","");
                                sessionManagement.saveSession(data);
                                Intent intent = new Intent(MainActivity.this, StudentSide.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                loginPassword.setError("Incorrect Password");
                                loginPassword.requestFocus();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String[] childs = new String[]{"SuperAdmin","Faculty","DEO","HOD"};
                for (String str : childs) {
                    reference.child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list2 = new ArrayList<>();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Information2 data = snapshot1.getValue(Information2.class);
                                list2.add(data);
                            }

                            for (Information2 item : list2) {
                                if (item.getEmail().equals(email)) {
                                    if (item.getPassword().equals(password)) {
                                        if(item.getRole().equals("Faculty")){
                                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                            Information2 data = new Information2(item.getEmail(),item.getDepartment(),item.getPassword(),item.getRole(),item.getValue(),item.getKey());
                                            sessionManagement.saveSession(data);
                                            Intent intent = new Intent(MainActivity.this, Faculty.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            MainActivity.this.startActivity(intent);
                                            loginEmail.setText("");
                                            loginPassword.setText("");
                                        }else if(item.getRole().equals("Super Admin")){
                                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                            Information2 data = new Information2(item.getEmail(),item.getDepartment(),item.getPassword(),item.getRole(),item.getValue(),item.getKey());
                                            sessionManagement.saveSession(data);
                                            Intent intent = new Intent(MainActivity.this, AdminSide.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            MainActivity.this.startActivity(intent);
                                            loginEmail.setText("");
                                            loginPassword.setText("");
                                        }else if(item.getRole().equals("DEO")){
                                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                            Information2 data = new Information2(item.getEmail(),item.getDepartment(),item.getPassword(),item.getRole(),item.getValue(),item.getKey());
                                            sessionManagement.saveSession(data);
                                            Intent intent = new Intent(MainActivity.this, DEOSide.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            MainActivity.this.startActivity(intent);
                                            loginEmail.setText("");
                                            loginPassword.setText("");
                                        }else{
                                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                            Information2 data = new Information2(item.getEmail(),item.getDepartment(),item.getPassword(),item.getRole(),item.getValue(),item.getKey());
                                            sessionManagement.saveSession(data);
                                            Intent intent = new Intent(MainActivity.this, HODSide.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            MainActivity.this.startActivity(intent);
                                            loginEmail.setText("");
                                            loginPassword.setText("");
                                        }
                                    }else{
                                        loginPassword.setError("Incorrect Password");
                                        loginPassword.requestFocus();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if(flag)
                    Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Here.class);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentSide.class);
                startActivity(intent);
            }
        });
    }
}