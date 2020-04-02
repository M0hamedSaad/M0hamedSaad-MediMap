package com.example.s3adoon.gp_v;


public class Person
{
    public String username , email , firstname , password , phone , lastname , address,type,id;

    // Default constructor required for calls to
    // DataSnapshot.getValue(com.example.s3adoon.gp_v.User.class)
    public Person()
    {

    }


    /*
    *   public Doctor(String id,String username, String email, String firstname, String password, String lastname, String phone,
                      String address,String spec,String uni,String age  ,  String type) {
    * */
    public Person (String id,String username, String email,String firstname,String password,String lastname,String phone
            ,String address,String type) {
        this.id=id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

