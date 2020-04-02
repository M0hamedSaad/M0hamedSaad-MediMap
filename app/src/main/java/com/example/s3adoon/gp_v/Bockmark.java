package com.example.s3adoon.gp_v;


public class Bockmark
{
    String user_email , clcname ;
    public Bockmark()
        {

         }

    public Bockmark(String user_email, String clcname) {
        this.user_email = user_email;
        this.clcname = clcname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getClcname() {
        return clcname;
    }

    public void setClcname(String clcname) {
        this.clcname = clcname;
    }
}
