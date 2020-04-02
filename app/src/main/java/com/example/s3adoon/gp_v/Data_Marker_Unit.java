package com.example.s3adoon.gp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Data_Marker_Unit extends AppCompatActivity implements View.OnClickListener {

    private TextView unitname, unittype, unitloc, unitstart, unitend;
    String spec ;
    private DatabaseReference reference;
    FirebaseAuth auth ;
    ImageView starr , bookmark ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.data_marker_unit);

        unitname = findViewById(R.id.unitname);
        unittype = findViewById(R.id.unittype);
        unitloc = findViewById(R.id.unitlocation);
        unitstart = findViewById(R.id.unitstart);
        unitend = findViewById(R.id.unitend);
        auth = FirebaseAuth.getInstance();
        final Intent intent = getIntent();
        spec = intent.getStringExtra("mark1");
        starr = findViewById(R.id.starr);
        bookmark = findViewById(R.id.bookmark);
        Search_Unit(spec);
        starr.setVisibility(View.INVISIBLE);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_spec_bockmark();
            }
        });

    }


    @Override
    public void onClick(View v) {
    }


    static Unit u = new Unit();

    private void Search_Unit(String x) {









        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Unit");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);
                        // String id = snapshot1.getKey();


                        unitname.setText(u.getName());
                        unitloc.setText(u.getLocation());
                        unittype.setText(u.getType());
                        unitstart.setText(u.getStart());
                        unitend.setText(u.getEnd());



                    }

                }
                else
                {
                    Search_Pharma(spec);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }




    public void boookmark()
    {
        starr.setVisibility(View.VISIBLE);
        String UID = auth.getCurrentUser().getEmail();

        reference = FirebaseDatabase.getInstance().getReference("Bookmark");

        Bockmark unit = new Bockmark(UID , spec);


        if (!reference.push().setValue(unit).isSuccessful()) {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

        } else if (reference.push().setValue(unit).isSuccessful()) {
            Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show();
        }


    }

    static Unit d = new Unit();

    private void Search_Pharma(String x) {


        //Toast.makeText(this, ""+doctoremail, Toast.LENGTH_SHORT).show();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Pharmacist");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        d = snapshot1.getValue(Unit.class);
                        // String id = snapshot1.getKey();


                        unitname.setText(d.getName());
                        unitloc.setText(d.getLocation());
                        unittype.setText(d.getType());
                        unitstart.setText(d.getStart());
                        unitend.setText(d.getEnd());



                    }

                }
                else
                {
                    Search_hospital(spec);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }






    static Hospital hospital = new Hospital();

    private void Search_hospital(String x) {



        //Toast.makeText(this, ""+doctoremail, Toast.LENGTH_SHORT).show();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Hospital");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        hospital = snapshot1.getValue(Hospital.class);
                        // String id = snapshot1.getKey();


                        unitname.setText(hospital.getName());
                        unitloc.setText(hospital.getLocation());
                        unittype.setText(hospital.getSpec());
                        unitstart.setText(hospital.getStart());
                        unitend.setText(hospital.getEnd());



                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }




    private void Search_spec_bockmark()
    {

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Bookmark");
        com.google.firebase.database.Query query = re.orderByChild("clcname").equalTo(spec);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {


                    }

                    starr.setVisibility(View.VISIBLE);
                    Toast.makeText(Data_Marker_Unit.this, "already saved ", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    boookmark();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }




}
