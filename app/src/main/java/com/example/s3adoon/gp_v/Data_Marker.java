package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.system.StructTimespec;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.LogRecord;

public class Data_Marker extends AppCompatActivity implements View.OnClickListener {

    private TextView clcname, spook, email, loc, date, sdd, start, end;
    String spec  ;
    ProgressDialog progressDialog ;
    private DatabaseReference reference;
    FirebaseAuth auth ;
    private ImageView bookmark , starr , feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.data_marker);

        clcname = findViewById(R.id.clcname);
        spook = findViewById(R.id.spec);
        email = findViewById(R.id.docemail);
        loc = findViewById(R.id.location);
        date = findViewById(R.id.date);
        sdd = findViewById(R.id.sdd);
        feedback = findViewById(R.id.btnfeedback);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        starr = findViewById(R.id.starr);
        bookmark = findViewById(R.id.bookmark);
        auth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Data_Marker.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        final Intent intent = getIntent();
        spec = intent.getStringExtra("mark");


        starr.setVisibility(View.INVISIBLE);
        Search_Unit(spec);
        starr.setVisibility(View.INVISIBLE);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_spec_bockmark();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(Data_Marker.this, User_Feedback.class);
                in.putExtra("spec", spec);
                startActivity(in);


            }
        });
    }


    @Override
    public void onClick(View v) {
    }

    static Clinic u = new Clinic();

    public void Search_Unit(String x) {

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("clinicname").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Clinic.class);
                        // String id = snapshot1.getKey();


                        email.setText(u.getName());
                        loc.setText(u.getLocation());
                        date.setText(u.getDate_of_birth());
                        sdd.setText(u.getSdd());
                        start.setText(u.getStart());
                        end.setText(u.getEnd());
                        clcname.setText(u.getClinicname());
                        spook.setText(u.getSpec());


                    }
                 //   String url = "https://firebasestorage.googleapis.com/v0/b/medico-5bfea.appspot.com/o/images%2F" + u.getClinicname() + "?alt=media&token=f9fa18aa-4b27-45c9-afdc-5395d234b77a";
                  //  Glide.with(getApplicationContext()).load(url).into(imageView);
                }
                else
                {
                    finish();

                    Intent in = new Intent(Data_Marker.this, Data_Marker_Unit.class);
                    in.putExtra("mark1", spec);
                    startActivity(in);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    @Override
    public void onBackPressed()
    {

        startActivity(new Intent (Data_Marker.this,User_Page.class));
        finish();
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
                    Toast.makeText(Data_Marker.this, "already saved ", Toast.LENGTH_SHORT).show();
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








