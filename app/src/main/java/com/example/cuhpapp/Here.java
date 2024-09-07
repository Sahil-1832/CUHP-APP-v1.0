package com.example.cuhpapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Here extends AppCompatActivity {
    CardView student,staff;
    private String department="",role="Faculty",value="Student";
    RadioButton faculty,deo,hod;
    private DatabaseReference reference;
    private static final String ROLL_NUMBER_REGEX = "^[a-zA-Z0-9]+$";

    public static boolean isValidRollNumber(String rollNumber) {
        Pattern pattern = Pattern.compile(ROLL_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(rollNumber);
        return matcher.matches();
    }
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_here);

        staff = findViewById(R.id.staff);
        student = findViewById(R.id.student);
        reference = FirebaseDatabase.getInstance().getReference();

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });

    }

    private void showDialog1() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.student_sheet_layout);

        ArrayAdapter<String> adapterItems;
        AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.department);
        TextInputLayout textInputLayout = dialog.findViewById(R.id.textInputLayout);

        String[] items= new String[]{"Computer Science & Informatics",
                "Srinivasa Ramanujan Department of Mathematics","Department of Library & Information Sciences"};
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnClickListener(v -> {
            if (autoCompleteTextView.isPopupShowing()) {
                textInputLayout.setHintEnabled(false);
            } else {
                textInputLayout.setHintEnabled(false);
                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnDismissListener(() -> textInputLayout.setHintEnabled(false));

        EditText signUpRollNo = dialog.findViewById(R.id.signUpRollNo);
        EditText signUpEmail = dialog.findViewById(R.id.signUpEmail);
        EditText signUpPassword = dialog.findViewById(R.id.signUpPassword);
        EditText signUpConfirmPassword = dialog.findViewById(R.id.signUpConfirmPassword);
        Button signUp = dialog.findViewById(R.id.signUp);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollNo = signUpRollNo.getText().toString();
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                String confirmPassword = signUpConfirmPassword.getText().toString();
                if(rollNo.isEmpty()){
                    signUpRollNo.setError(rollNo);
                    signUpRollNo.requestFocus();
                }else if(email.isEmpty()){
                    signUpEmail.setError("Enter a email");
                    signUpEmail.requestFocus();
                }else if(department.isEmpty()){
                    Toast.makeText(Here.this, "Select a department", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    signUpPassword.setError("Enter a Roll NO.");
                    signUpPassword.requestFocus();
                }else if(confirmPassword.isEmpty()){
                    signUpConfirmPassword.setError("Enter a Roll NO.");
                    signUpConfirmPassword.requestFocus();
                }else if(!isValidRollNumber(rollNo)){
                    signUpRollNo.setError("Invalid Roll No. format");
                    signUpRollNo.requestFocus();
                }else if(!isValidEmail(email)){
                    signUpEmail.setError("Invalid email format");
                    signUpEmail.requestFocus();
                }else if(!password.equals(confirmPassword)){
                    signUpConfirmPassword.setError("Password does not match");
                    signUpConfirmPassword.requestFocus();
                }else{

                    String uniqueKey = reference.child("SignUp").child("Student").push().getKey();
                    Information data = new Information(rollNo,email,department,password,uniqueKey);
                    reference.child("SignUp").child("Student").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Here.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Here.this,MainActivity.class);
                            startActivity(intent);;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Here.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialog2() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.staff_sheet_layout);

        ArrayAdapter<String> adapterItems;
        AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.department);
        TextInputLayout textInputLayout = dialog.findViewById(R.id.textInputLayout);
        faculty = dialog.findViewById(R.id.facultyRadioButton);
        deo = dialog.findViewById(R.id.deoRadioButton);
        hod = dialog.findViewById(R.id.hodRadioButton);

        String[] items= new String[]{"Computer Science & Informatics",
                "Srinivasa Ramanujan Department of Mathematics","Department of Library & Information Sciences"};
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnClickListener(v -> {
            if (autoCompleteTextView.isPopupShowing()) {
                textInputLayout.setHintEnabled(false);
            } else {
                textInputLayout.setHintEnabled(false);
                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnDismissListener(() -> textInputLayout.setHintEnabled(false));

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Faculty";
                value = "Student";
                faculty.setChecked(true);
                deo.setChecked(false);
                hod.setChecked(false);
            }
        });

        deo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "DEO";
                value = "Student";
                faculty.setChecked(false);
                deo.setChecked(true);
                hod.setChecked(false);
            }
        });

        hod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "HOD";
                value = "Faculty";
                faculty.setChecked(false);
                deo.setChecked(false);
                hod.setChecked(true);
            }
        });

        EditText signUpEmail = dialog.findViewById(R.id.signUpEmail);
        EditText signUpPassword = dialog.findViewById(R.id.signUpPassword);
        EditText signUpConfirmPassword = dialog.findViewById(R.id.signUpConfirmPassword);
        Button signUp = dialog.findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                String confirmPassword = signUpConfirmPassword.getText().toString();

                if(email.isEmpty()){
                    signUpEmail.setError("Enter a email");
                    signUpEmail.requestFocus();
                }else if(department.isEmpty()){
                    Toast.makeText(Here.this, "Select a department", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    signUpPassword.setError("Enter a Roll NO.");
                    signUpPassword.requestFocus();
                }else if(confirmPassword.isEmpty()){
                    signUpConfirmPassword.setError("Enter a Roll NO.");
                    signUpConfirmPassword.requestFocus();
                }else if(!isValidEmail(email)){
                    signUpEmail.setError("Invalid email format");
                    signUpEmail.requestFocus();
                }else if(!password.equals(confirmPassword)){
                    signUpConfirmPassword.setError("Password does not match");
                    signUpConfirmPassword.requestFocus();
                }else{

                    String uniqueKey = reference.child("SignUp").push().getKey();
                    Information2 data = new Information2(email,department,password,role,value,uniqueKey);
                    if(role.equals("DEO"))
                        role="Faculty";
                    reference.child("SignUp").child(role).child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Here.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Here.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Here.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}