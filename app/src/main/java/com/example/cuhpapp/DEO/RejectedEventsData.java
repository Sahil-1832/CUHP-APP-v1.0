package com.example.cuhpapp.DEO;

public class RejectedEventsData {
    private String image,key,category,date,reason;

    public RejectedEventsData(String image, String key, String category, String date, String reason) {
        this.image = image;
        this.key = key;
        this.category = category;
        this.date = date;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RejectedEventsData() {
    }
}
