package com.example.cuhpapp.delete;

public class PdfData {
    String title,url,key,text;
    Boolean flag;

    public PdfData(String title, String url, String key, String text,Boolean flag) {
        this.title = title;
        this.url = url;
        this.key = key;
        this.text=text;
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getFlag() {
        return flag;
    }
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    public PdfData() {
    }
}
