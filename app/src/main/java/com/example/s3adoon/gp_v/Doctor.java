package com.example.s3adoon.gp_v;

public class Doctor extends Person
{
        public String  spec,uni , age;


        // Default constructor required for calls to
        // DataSnapshot.getValue(com.example.s3adoon.gp_v.User.class)
        public Doctor()
        {

        }


        public Doctor(String id,String username, String email, String firstname, String password, String lastname, String phone,
                      String address, String type,String spec,String uni,String age  ) {
            this.username = username;
            this.id=id;
            this.email = email;
            this.password = password;
            this.firstname = firstname;
            this.lastname = lastname;
            this.phone = phone;
            this.address = address;
            this.spec = spec;
            this.age = age;

            this.uni = uni;
            this.type = type;
        }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}
