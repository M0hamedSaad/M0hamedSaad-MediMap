package com.example.s3adoon.gp_v;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.breakStrategy;
import static android.R.attr.finishOnCloseSystemDialogs;
import static android.R.attr.y;


/*
*

* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String UID;


    EditText edusername, edpassword;
    Dialog myDialog;
    Button login;
    TextView textforget, textsiginup;
    Firebase mRef , user_auth;
    private static FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    User user = new User();
    Doctor doctor = new Doctor();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // finish();
        //System.exit(0);
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Firebase.setAndroidContext(this);
        //Get Firebase auth instance

        auth = FirebaseAuth.getInstance();
        mRef = new Firebase("https://medico-5bfea.firebaseio.com/");
        /*if (auth.getCurrentUser() != null) {

                startActivity(new Intent(MainActivity.this, User_Page.class));
                finish();
            }
*/


        setContentView(R.layout.activity_main);


        edusername = (EditText) findViewById(R.id.edusername);
        edpassword = (EditText) findViewById(R.id.edpassword);
        login = (Button) findViewById(R.id.btnlogin);
        textforget = (TextView) findViewById(R.id.textforget);
        textsiginup = (TextView) findViewById(R.id.textsiginup);
        edusername.setOnClickListener(this);
        edpassword.setOnClickListener(this);
        login.setOnClickListener(this);
        textforget.setOnClickListener(this);
        textsiginup.setOnClickListener(this);
        myDialog = new Dialog(this);


    }


    public void ShowPopup(View v) {
        myDialog.setContentView(R.layout.choose);

        LinearLayout imageuser, imagedoc;
        imageuser=findViewById(R.id.imageuser);
        imagedoc=findViewById(R.id.imagedoc);

        imageuser.setOnClickListener(this);
        imagedoc.setOnClickListener(this);

        imagedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Sign_Up_doc.class));

            }
        });


        imageuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Sign_Up.class));
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        myDialog.show();
    }

    public void onClick(View v)

    {

        if (v == login) {
        login ();

        } else if (v == textforget) {

            startActivity(new Intent(this, ForgetPass.class));


        } else if (v == textsiginup) {
            startActivity(new Intent(this, Choose.class));
        }

    }


    private boolean query_get_Usertype()
    {
         final ProgressDialog pd = new ProgressDialog(MainActivity.this);

        pd.setMessage("Loading... Please wait");
        pd.show();




        //
        UID = auth.getCurrentUser().getUid();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("User");
        com.google.firebase.database.Query query = re.orderByChild("id").equalTo(UID);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User u = snapshot.getValue(User.class);
                        startActivity(new Intent(getApplication(),User_Page.class));
                        finish();
                    }
                    pd.dismiss();
                }
                else
                {

                    query_get_Doctortype();




                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return  true ;
    }





    private boolean query_get_Doctortype()
    {
        UID = auth.getCurrentUser().getUid();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Doctor");
        com.google.firebase.database.Query query = re.orderByChild("id").equalTo(UID);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {       for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    Doctor d = snapshot1.getValue(Doctor.class);
                    startActivity(new Intent(getApplication(),Doctor_Page.class));
                    finishAffinity();
                }
                }
                else
                {
                    UID = auth.getCurrentUser().getUid();
                    if(UID.equals("PzAnmXQ5Pod0gTpPaqoC0uUjHo83"))
                    {
                        startActivity(new Intent(MainActivity.this,Admin_Page.class));
                        finishAffinity();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                    }

                    //
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

            }
        });
        return true;
    }

public void login()
{
    String email = edusername.getText().toString().trim();
    final String password = edpassword.getText().toString().trim();

    if (TextUtils.isEmpty(email)) {
        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
        return;
    }


    if (TextUtils.isEmpty(password)) {
        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
        return;
    }









    else {

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                edpassword.setError("Password is too Short ");
                            } else

                            {

                                Toast.makeText(MainActivity.this, "E@mail OR Password are wrong ", Toast.LENGTH_LONG).show();

                            }

                        } else if (task.isSuccessful())

                        {

                            //  String UID=auth.getCurrentUser().getUid();



                            query_get_Usertype();


                        }

                    }

                });

    }


}

}

