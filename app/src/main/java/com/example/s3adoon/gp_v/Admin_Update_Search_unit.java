package com.example.s3adoon.gp_v;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class Admin_Update_Search_unit extends AppCompatActivity implements View.OnClickListener {


    private Button btnsearch;
    public String name, location, type, start, end;
    private DatabaseReference reference;
    private EditText unit_search;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_update_search_unit);

        unit_search = findViewById(R.id.unit_search);

        reference = FirebaseDatabase.getInstance().getReference("Unit");
        btnsearch = findViewById(R.id.btnsearch);


        myDialog = new Dialog(this);
        btnsearch.setOnClickListener(this);

    }

    public void onClick(View v)

    {
        // public Unit(String name ,String location,String start , String end ,String type )
        if (v == btnsearch) {
            Search_Unit();
        }

    }


    static Unit u = new Unit();

    private void Search_Unit() {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Unit");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);
                        String id = snapshot1.getKey();

                        String arr[] = {u.getName(), u.getLocation(), u.getType(), u.getStart(), u.getEnd(), id};

                        Intent intent = new Intent(getApplication(), Admin_Update_Add_unit.class);


                        intent.putExtra("key", arr);

                        startActivity(intent);


                    }
                } else {
                    Search_Pharma();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }


    public void ayhaga() {
        Toast.makeText(Admin_Update_Search_unit.this, "ggggggggggggggggggg", Toast.LENGTH_SHORT).show();

    }


    private void Search_Pharma() {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Pharmacist");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);
                        String id = snapshot1.getKey();

                        String arr[] = {u.getName(), u.getLocation(), u.getType(), u.getStart(), u.getEnd(), id};

                        Intent intent = new Intent(getApplication(), Admin_Update_Add_unit.class);


                        intent.putExtra("key", arr);

                        startActivity(intent);


                    }
                } else {
                    Search_Hospital();                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }


static Hospital hospital = new Hospital();
    private void Search_Hospital() {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Hospital");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        hospital = snapshot1.getValue(Hospital.class);
                        String id = snapshot1.getKey();

                        String arr[] = {hospital.getName(), hospital.getLocation(), hospital.getType(), hospital.getStart(), hospital.getEnd(), id};

                        Intent intent = new Intent(getApplication(), Admin_Update_Add_unit.class);


                        intent.putExtra("key", arr);

                        startActivity(intent);


                    }
                } else {
                    Toast.makeText(Admin_Update_Search_unit.this, "This Name is Not Exist ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }


}

