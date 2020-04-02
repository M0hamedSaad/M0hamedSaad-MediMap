
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

public class Sign_Up extends AppCompatActivity implements View.OnClickListener
{
    Firebase database;
    private FirebaseDatabase mRef= FirebaseDatabase.getInstance();
    EditText edusernameup, edpasswordup , edemailup , edaddress ,edphone , edfirstnameup , edlastnameup ;
    Button submitup;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    User user = new User();
    CheckBox box ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.sign_up);
        auth = FirebaseAuth.getInstance();
        database = new Firebase("https://medico-5bfea.firebaseio.com/");

        edusernameup = (EditText) findViewById(R.id.edusernameup);
        edpasswordup = (EditText) findViewById(R.id.edpasswordup);
        edemailup = (EditText) findViewById(R.id.edemailup);
        edfirstnameup = (EditText) findViewById(R.id.edfirstname);
        edlastnameup = (EditText) findViewById(R.id.edlastname);
        edphone = (EditText) findViewById(R.id.edphone);
        edaddress = (EditText) findViewById(R.id.edaddress);
        submitup = (Button) findViewById(R.id.btnsubmitup);
        submitup.setOnClickListener( this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        submitup.setVisibility(View.INVISIBLE);
        box = findViewById(R.id.chkBox1);
    }



    public void itemClicked(View v)
    {
        submitup.setVisibility(View.VISIBLE);
        box.setEnabled(false);
    }

    public void onClick(View v) {
        if (v == submitup) {

            if (!edusernameup.getText().toString().equals("") && !edemailup.getText().toString().equals("") && !edfirstnameup.getText().toString().equals("")
                    && !edaddress.getText().toString().equals("") && !edlastnameup.getText().toString().equals("") &&
                    !edpasswordup.getText().toString().equals("") && !edphone.getText().toString().equals("")) {


                user.address = edaddress.getText().toString().trim();
                user.firstname = edfirstnameup.getText().toString().trim();
                user.lastname = edlastnameup.getText().toString().trim();
                user.email = edemailup.getText().toString().trim();
                user.password = edpasswordup.getText().toString().trim();
                user.phone = edphone.getText().toString().trim();

                if (user.password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Sign_Up.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    ProgressDialog pd = new ProgressDialog(Sign_Up.this);
                                    pd.setMessage("Loading... Please wait");
                                    pd.show();
                                    OnAuthSuccess(task.getResult().getUser());
                                }
                            }
                        });

            }
            else
            {


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

                if (edphone.length() < 11 ) {
                    edphone.setError("Enter Valid Number");
                }


            }
        }

    }




    private void  OnAuthSuccess(FirebaseUser mUser)
    {
        saveNewUser(mUser.getUid(),user.getUsername(),user.getEmail(),user.getFirstname(),user.getLastname(),
                user.getPhone(),user.getAddress(),user.getPassword(),"user");
        startActivity(new Intent(Sign_Up.this, MainActivity.class));
        finish();
    }


    private void saveNewUser(String userId,String userName,String email,String fName,String lName,String phone, String address,String password,String type)
    {
        User user= new User(userId,userName, email,fName,password,lName,phone,address,type);
        DatabaseReference users = mRef.getReference("User");
        users.child(userId).setValue(user);
    }



}
