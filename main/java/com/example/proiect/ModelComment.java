package com.example.proiect;

public class ModelComment {

    String comment;
    String ptime;
    String uname;
    String uid;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public ModelComment() {
    }

    public ModelComment( String comment, String ptime, String uid, String uname) {
        this.comment = comment;
        this.ptime = ptime;
        this.uid = uid;
        this.uname = uname;
    }
}