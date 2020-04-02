package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Showmore extends AppCompatActivity implements View.OnClickListener
{
        private Button approve ;
        private TextView clcname , spook , email , loc , date  ,sdd  , start , end ;


    private ImageView imageView ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.showmore);

                clcname = findViewById(R.id.clcname);
                spook = findViewById(R.id.spec);
                email = findViewById(R.id.docemail);
                loc = findViewById(R.id.location);
                date = findViewById(R.id.date);
                sdd = findViewById(R.id.sdd);
                start = findViewById(R.id.start);
                end = findViewById(R.id.end);
                approve = findViewById(R.id.approve);
            imageView = findViewById(R.id.img9);

                approve.setOnClickListener(this);

                Search_Unit();


    }


        @Override
        public void onClick(View v)
        {
                if(v == approve)
                {
                    approval();
                }
        }




        static Clinic u = new Clinic();

        private void Search_Unit()
        {
                Intent intent = getIntent();
                final String name = intent.getStringExtra("name");

                //Toast.makeText(this, ""+doctoremail, Toast.LENGTH_SHORT).show();

                DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
                com.google.firebase.database.Query query = re.orderByChild("clinicname").equalTo(name);
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
                                    String url ="https://firebasestorage.googleapis.com/v0/b/medico-5bfea.appspot.com/o/images%2F"+u.getClinicname()+"?alt=media&token=f9fa18aa-4b27-45c9-afdc-5395d234b77a";
                                    Glide.with(getApplicationContext()).load(url).into(imageView);
                                }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                });


        }














        public void approval ()
        {


            final Intent intent = getIntent();
            final String name = intent.getStringExtra("name");


            final String email = intent.getStringExtra("email");

            final String[] id = new String[1];

            final String spec = intent.getStringExtra("spec");


            //Toast.makeText(this, ""+doctoremail, Toast.LENGTH_SHORT).show();

            DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
            com.google.firebase.database.Query query = re.orderByChild("clinicname").equalTo(name);
            query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                           // u = snapshot1.getValue(Clinic.class);
                              id[0] = snapshot1.getKey();

                        }
                        Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
                        Firebase objRef = m_objFireBaseRef.child("Clinic");
                        objRef.child(id[0]).child("status").setValue("complete");
                        objRef.child(id[0]).child("clcKey").setValue(email+"complete");
                        objRef.child(id[0]).child("spec_state").setValue(spec+"complete");

                        startActivity(new Intent(Showmore.this,Verify.class));


                        finish();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });







        }




}


