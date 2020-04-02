package com.example.s3adoon.gp_v;

public class Contact_Us
{
    String email , meesage , type ;

    public Contact_Us(String email , String message , String type)
    {
        this.email=email;
        this.meesage=message;
        this.type=type;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMeesage() {
        return meesage;
    }


    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }
}
