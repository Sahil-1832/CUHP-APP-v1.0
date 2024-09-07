package com.example.cuhpapp;

public class UploadImageData {
    private String image,key,category,date;
    Boolean flag;

    public UploadImageData() {
    }

    public UploadImageData(String image, String key, String category, String date,Boolean flag) {
        this.image = image;
        this.key = key;
        this.category=category;
        this.date = date;
        this.flag = flag;
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

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
