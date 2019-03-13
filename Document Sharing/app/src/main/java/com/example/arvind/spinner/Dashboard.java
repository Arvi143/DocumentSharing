package com.example.arvind.spinner;

public class Dashboard {
    private String id;
    private String semesterName;
    private String url;
    public Dashboard(String id,String semesterName,String url){
        this.id=id;
        this.semesterName=semesterName;
        this.url=url;


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}


