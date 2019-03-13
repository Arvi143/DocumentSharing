package com.example.arvind.spinner;

public class ShowUploadFile {

    private String id;
    private String url;
    private String semestername;
    private String semesterid;
    private String adminid;
    private String adminname;
    private String remarks;
    private String subjectid;

    public ShowUploadFile(String id, String url, String semestername, String semesterid, String adminid, String adminname, String remarks, String subjectid) {
        this.id = id;
        this.url = url;
        this.semestername = semestername;
        this.semesterid = semesterid;
        this.adminid = adminid;
        this.adminname = adminname;
        this.remarks = remarks;
        this.subjectid = subjectid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSemestername() {
        return semestername;
    }

    public void setSemestername(String semestername) {
        this.semestername = semestername;
    }

    public String getSemesterid() {
        return semesterid;
    }

    public void setSemesterid(String semesterid) {
        this.semesterid = semesterid;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }
}
