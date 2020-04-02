package com.example.s3adoon.gp_v;

public class Feedback_user

{
        String user_email ,doc_email , clinic_cname , mesaage ;

        public Feedback_user(String user_email,String doc_email ,  String clinic_cname, String mesaage ) {
            this.user_email = user_email;
            this.doc_email = doc_email;
            this.clinic_cname = clinic_cname;
            this.mesaage = mesaage;
        }

    public Feedback_user()
    {

    }
        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getClinic_cname() {
            return clinic_cname;
        }

        public void setClinic_cname(String clinic_cname) {
            this.clinic_cname = clinic_cname;
        }

        public String getMesaage() {
            return mesaage;
        }

        public void setMesaage(String mesaage) {
            this.mesaage = mesaage;
        }

        public String getDoc_email() {
            return doc_email;
        }

        public void setDoc_email(String doc_email) {
            this.doc_email = doc_email;
        }
    }


