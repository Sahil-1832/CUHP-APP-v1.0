package com.example.cuhpapp;

public class Information {
    String rollNo,email,password,department,key;

    public Information(String rollNo, String email,String department, String password,String key) {
        this.rollNo = rollNo;
        this.email = email;
        this.password = password;
        this.department = department;
        this.key = key;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Information() {
    }
}
