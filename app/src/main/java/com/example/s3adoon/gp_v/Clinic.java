package com.example.s3adoon.gp_v;


import com.google.android.gms.maps.model.LatLng;

public class Clinic
{

    public String  clinicname  , location , date_of_birth , sdd  , spec ,doctor_id  , card ,start,end,name,status , clcKey , spec_state;
    public double lat ,lng;
    public Clinic()
    {

    }

    public String getClcKey() {
        return clcKey;
    }

    public void setClcKey(String clcKey) {
        this.clcKey = clcKey;
    }

    public Clinic(String clinicname , String location , String date_of_birth , String sdd ,
                  String spec , String card, String start, String end, String name, String status, String clcKey , double lat, double lng , String spec_state)
    {

        //this.doctor_id = doctor_id;

        this.clinicname = clinicname;
        this.location= location;
        this.date_of_birth = date_of_birth;
        this.sdd= sdd;
        this.lat = lat ;
        this.lng = lng ;
        this.spec= spec;
        this.card= card;

        this.start= start;
        this.end= end;
        this.spec_state = spec_state ;
        this.name= name;
        this.status= status;
        this.clcKey=clcKey;

    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;

    }

    public String getSpec_state() {
        return spec_state;
    }

    public void setSpec_state(String spec_state) {
        this.spec_state = spec_state;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSdd() {
        return sdd;
    }

    public void setSdd(String sdd) {
        this.sdd = sdd;
    }


    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
/*
* <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/forgetpass"
            android:layout_width="match_parent"
            android:layout_height="500dp"
            android:orientation="vertical"
            android:layout_marginTop="60dp"
            android:background="@drawable/proff"

            >

            <TextView
                android:id="@+id/img_profile"
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:layout_marginLeft="140dp"
                android:layout_marginTop="110dp"
                />

            <ScrollView
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="350dp"
                    android:orientation="vertical">


                    <LinearLayout
                        android:layout_width="205dp"
                        android:layout_height="match_parent"
                        android:layout_marginLeft="200dp"
                        android:orientation="vertical">


                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Clinic Name"
                                android:id="@+id/clinicname"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Specilization"
                                android:id="@+id/spec"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Location"
                                android:id="@+id/location"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Working Hours"
                                android:inputType="number"
                                android:id="@+id/workinghour"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                    </LinearLayout>

                    <LinearLayout
                        android:layout_width="205dp"
                        android:layout_height="match_parent"
                        android:orientation="vertical"
                        android:layout_marginTop="-240dp"
                        android:layout_marginLeft="25dp"
                        >

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Doctor Name"
                                android:id="@+id/doctorname"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Date Of Birth"
                                android:id="@+id/date"
                                android:inputType="datetime"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="SSD"
                                android:id="@+id/sdd"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                        <android.support.design.widget.TextInputLayout
                            android:layout_width="match_parent"
                            android:layout_height="60dp">

                            <android.support.design.widget.TextInputEditText
                                android:layout_width="180dp"
                                android:layout_height="60dp"
                                android:hint="Syndication Card"
                                android:id="@+id/card"
                                android:singleLine="true" />

                        </android.support.design.widget.TextInputLayout>

                    </LinearLayout>


                    <RelativeLayout
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:orientation="vertical"

                        android:layout_marginLeft="30dp"
                        android:layout_marginTop="10dp">

                        <TextView
                            android:id="@+id/gender_textview"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:paddingRight="15dp"
                            android:text="I'm .. "
                            android:textSize="20dp"
                            android:layout_marginTop="7dp"/>

                        <RadioGroup
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_toRightOf="@+id/gender_textview"
                            android:orientation="horizontal"
                            android:layout_marginTop="7dp">

                            <RadioButton
                                android:id="@+id/female"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"

                                android:text="Female" />

                            <RadioButton
                                android:id="@+id/male"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:text="Male" />
                        </RadioGroup>



                        <Button
                            android:id="@+id/send"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:orientation="horizontal"
                            android:text="Send"
                            android:fontFamily="serif"
                            android:textColor="@color/colorblack"
                            android:textSize="12dp"
                            android:background="@drawable/roundedittexts"
                            android:layout_marginLeft="240dp"
                            android:backgroundTint="@color/searchcolor"

                            />
                    </RelativeLayout>

                </LinearLayout>
            </ScrollView>

        </LinearLayout>


    </FrameLayout>

*/