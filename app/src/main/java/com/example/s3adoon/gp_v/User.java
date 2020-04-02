package com.example.s3adoon.gp_v;

 public class User extends Person
{
        // Default constructor required for calls to
        // DataSnapshot.getValue(com.example.s3adoon.gp_v.User.class)
        public User()
        {

        }

        public User(String id,String username, String email,String firstname,String password,String lastname,String phone,String address,String type) {
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

}
