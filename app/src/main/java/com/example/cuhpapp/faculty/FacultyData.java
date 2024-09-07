package com.example.cuhpapp.faculty;

public class FacultyData {
    private String name,email,position,image,key;

    public FacultyData() {
    }

    public FacultyData(String name, String email, String position, String image, String key ){
        this.name = name;
        this.email = email;
        this.position = position;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

