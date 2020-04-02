
package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up_doc extends AppCompatActivity implements View.OnClickListener {
    Firebase database;
    EditText edusernameup, edpasswordup, edemailup, edaddress, edphone, edfirstnameup, edlastnameup,
            edageup, edspecup, eduniup;
    Button submitup;
    CheckBox box;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();
    Doctor doctor = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.sign_up_doc);
        auth = FirebaseAuth.getInstance();
        database = new Firebase("https://medico-5bfea.firebaseio.com/");
        edusernameup = (EditText) findViewById(R.id.edusernameup);
        edpasswordup = (EditText) findViewById(R.id.edpasswordup);
        edemailup = (EditText) findViewById(R.id.edemailup);
        edfirstnameup = (EditText) findViewById(R.id.edfirstname);
        edlastnameup = (EditText) findViewById(R.id.edlastname);
        edphone = (EditText) findViewById(R.id.edphone);
        edaddress = (EditText) findViewById(R.id.edaddress);
        edageup = (EditText) findViewById(R.id.edage);
        edspecup = (EditText) findViewById(R.id.edspec);
        eduniup = (EditText) findViewById(R.id.eduni);


        submitup = (Button) findViewById(R.id.btnsubmitup);
        submitup.setOnClickListener(this);
        box = findViewById(R.id.chkBox1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        submitup.setVisibility(View.INVISIBLE);

    }


    public void itemClicked(View v) {
        submitup.setVisibility(View.VISIBLE);
        box.setEnabled(false);
    }

    public void onClick(View v) {
        if (v == submitup) {

            if (!edusernameup.getText().toString().equals("") && !edemailup.getText().toString().equals("") && !edfirstnameup.getText().toString().equals("")
                    && !edaddress.getText().toString().equals("") && !edlastnameup.getText().toString().equals("") &&
                    !edpasswordup.getText().toString().equals("") && !edphone.getText().toString().equals("") && !edageup.getText().toString().equals("")
                    && !edspecup.getText().toString().equals("") && !eduniup.getText().toString().equals("")

                    )

            {
                doctor.username = edusernameup.getText().toString().trim();
                doctor.spec = edspecup.getText().toString().trim();
                doctor.uni = eduniup.getText().toString().trim();
                doctor.age = edageup.getText().toString().trim();
                doctor.address = edaddress.getText().toString().trim();
                doctor.firstname = edfirstnameup.getText().toString().trim();
                doctor.lastname = edlastnameup.getText().toString().trim();
                doctor.email = edemailup.getText().toString().trim();
                doctor.password = edpasswordup.getText().toString().trim();
                doctor.phone = edphone.getText().toString().trim();


                if (doctor.password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(doctor.email, doctor.password)
                        .addOnCompleteListener(Sign_Up_doc.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Sign_Up_doc.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    ProgressDialog pd = new ProgressDialog(Sign_Up_doc.this);
                                    pd.setMessage("Loading... Please wait");
                                    //  pd.show();
                                    OnAuthSuccess(task.getResult().getUser());
                                }
                            }
                        });

            } else {
                if (edusernameup.getText().toString().equals("")) {
                    edusernameup.setError("Required");
                }
                if (edpasswordup.getText().toString().equals("")) {
                    edpasswordup.setError("Required");
                }
                if (edfirstnameup.getText().toString().equals("")) {
                    edfirstnameup.setError("Required");
                }
                if (edlastnameup.getText().toString().equals("")) {
                    edlastnameup.setError("Required");
                }
                if (edemailup.getText().toString().equals("")) {
                    edemailup.setError("Required");
                }
                if (edaddress.getText().toString().equals("")) {
                    edaddress.setError("Required");
                }
                if (edphone.getText().toString().equals("")) {
                    edphone.setError("Required");
                }

                if (edphone.length() < 11) {
                    edphone.setError("Enter Valid Number");
                }

                if (edageup.getText().toString().equals("")) {
                    edageup.setError("Required");
                }
                if (edspecup.getText().toString().equals("")) {
                    edspecup.setError("Required");
                }
                if (eduniup.getText().toString().equals("")) {
                    eduniup.setError("Required");
                }

                if (edageup.length() <= 2) {
                    edageup.setError("Enter Real Age");

                }
            }
        /*
        }*/
        }
    }


    /*
    *  public Doctor(String id,String username, String email, String firstname, String password, String lastname, String phone,
                      String address,  String type ,String spec,String uni,String age  ,) {
    * */
    private void OnAuthSuccess(FirebaseUser mUser) {
        saveNewUser(mUser.getUid(), doctor.getUsername(), doctor.getEmail(), doctor.getFirstname(), doctor.getPassword()
                , doctor.getLastname(), doctor.getPhone(), doctor.getAddress(),"doctor"
                , doctor.getSpec(), doctor.getUni(), doctor.getAge());
        startActivity(new Intent(Sign_Up_doc.this, MainActivity.class));
        finish();
    }

    private void saveNewUser(String id, String username, String email, String firstname, String password, String lastname, String phone,
                             String address, String type, String spec, String uni, String age) {
        {
            Doctor doctor = new Doctor(id, username, email, firstname, password, lastname, phone, address,type, spec, uni, age );
            DatabaseReference doctors = mRef.getReference("Doctor");
            doctors.child(id).setValue(doctor);
        }

    }
}



