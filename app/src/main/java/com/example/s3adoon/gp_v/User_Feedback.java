package com.example.s3adoon.gp_v;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Feedback extends AppCompatActivity
{
    private EditText feedback ;
    private Button submit ;
    String spec  ;
    private DatabaseReference reference;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_feedback);

        feedback = findViewById(R.id.feedback);
        submit = findViewById(R.id.btnsubmitfeedback);
        auth = FirebaseAuth.getInstance();
        final Intent intent = getIntent();
        spec = intent.getStringExtra("spec");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_feed(spec);
            }
        });


    }


    static Clinic u = new Clinic();
    public void user_feed(String x) {


        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("clinicname").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Clinic.class);

                    }

                    String UID = auth.getCurrentUser().getEmail();
                    reference = FirebaseDatabase.getInstance().getReference("User_feedback");
                    Feedback_user unit = new Feedback_user( UID , u.getName(),spec , feedback.getText().toString() );

                    if (!reference.push().setValue(unit).isSuccessful()) {
                        Toast.makeText(User_Feedback.this, "Message sent", Toast.LENGTH_SHORT).show();

                    } else if (reference.push().setValue(unit).isSuccessful()) {
                        Toast.makeText(User_Feedback.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }



                    //Toast.makeText(User_Feedback.this, ""+u.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        }


        );

    }



}








