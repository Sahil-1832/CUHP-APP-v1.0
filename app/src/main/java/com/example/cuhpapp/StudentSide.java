package com.example.cuhpapp;
import java.io.File;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cuhpapp.Session.SessionManagement;
import com.example.cuhpapp.Teacher.Faculty;
import com.example.cuhpapp.getEbook.getEbook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class StudentSide extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_side);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if(menuItem.getItemId()== R.id.navWeb){
                String url = "https://www.cuhimachal.ac.in";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
            else if(menuItem.getItemId()==  R.id.navEvent ){
                startActivity(new Intent(this,Syllabus.class));
            }
            else if(menuItem.getItemId()== R.id.navHolidays){
                Intent intent = new Intent(this,Holidays.class);
                startActivity(intent);
            }
            else if(menuItem.getItemId()== R.id.navSamarth){
                String url = "https://cuhimachal.samarth.edu.in/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
            else if(menuItem.getItemId()== R.id.navDev){
                Intent intent = new Intent(this, Developer.class);
                startActivity(intent);
            }
            else if(menuItem.getItemId()== R.id.navEbook){
                Intent intent = new Intent(this, getEbook.class);
                startActivity(intent);
            }
            else if(menuItem.getItemId()== R.id.navShare){
                String url = "https://drive.google.com/file/d/1smrQYq52JZsD2ToEEUzNYOrbvuTubFSf/view?usp=drive_link";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check out this app ! ");
                intent.putExtra(Intent.EXTRA_TEXT,"Download this app from : " + url);

                startActivity(Intent.createChooser(intent,"Share Via"));

            }else if(menuItem.getItemId()==R.id.logOut){
                SessionManagement sessionManagement=new SessionManagement(StudentSide.this);
                sessionManagement.removeSession();
                Intent intent = new Intent(StudentSide.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}