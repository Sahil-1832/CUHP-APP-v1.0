package com.example.cuhpapp.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cuhpapp.Information2;

import java.util.Map;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    private static final  String EMAIL="email",PASSWORD="password",DEPARTMENT="department",ROLE="role",VALUE="value",KEY="key";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Information2 data){
        editor.putString(EMAIL,data.getEmail());
        editor.putString(PASSWORD,data.getPassword());
        editor.putString(DEPARTMENT,data.getDepartment());
        editor.putString(ROLE,data.getRole());
        editor.putString(VALUE,data.getValue());
        editor.putString(KEY,data.getKey());
        editor.commit();
    }

    public Information2 getSession(){
        String email = sharedPreferences.getString(EMAIL,"");
        String password = sharedPreferences.getString(PASSWORD,"");
        String department = sharedPreferences.getString(DEPARTMENT,"");
        String role = sharedPreferences.getString(ROLE,"");
        String value = sharedPreferences.getString(VALUE,"");
        String key = sharedPreferences.getString(KEY,"");
        return new Information2(email,department,password,role,value,key);
    }

    public void removeSession(){
        editor.clear();
        editor.commit();
    }
}
