package com.example.s3adoon.gp_v;

public class ModelList
{
    private int image ;
    private String clcname , docemail , showmore , spec ;

    public ModelList( String clcname, String docemail,String showmore, String spec) {

        this.clcname = clcname;
        this.spec = spec ;
        this.docemail = docemail;
        this.showmore = showmore ;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getShowmore() {
        return showmore;
    }

    public void setShowmore(String showmore) {
        this.showmore = showmore;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getClcname() {
        return clcname;
    }

    public void setClcname(String clcname) {
        this.clcname = clcname;
    }

    public String getDocemail() {
        return docemail;
    }

    public void setDocemail(String docemail) {
        this.docemail = docemail;
    }
}
