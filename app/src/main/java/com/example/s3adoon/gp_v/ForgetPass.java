package com.example.s3adoon.gp_v;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity implements View.OnClickListener

{

    private EditText edemailforget;
    Button submitforget;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.forgetpass);
        edemailforget = (EditText) findViewById(R.id.edemailforget);

        submitforget = (Button) findViewById(R.id.btnsubmitforget);

        edemailforget.setOnClickListener(this);

        submitforget.setOnClickListener(this);

    }


    public void onClick(View v) {


        if (v == submitforget) {

            String email = edemailforget.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), "Enter your registered email ", Toast.LENGTH_SHORT).show();
                return;
            }


            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPass.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPass.this,MainActivity.class));

                            } else {
                                Toast.makeText(ForgetPass.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


        }

    }

}


















          /*  AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Save File...");
            alertDialog.setMessage("Do you want to save this file?");
            alertDialog.setIcon(R.drawable.bockmark);
            alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                            Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "You clicked on Yes",
                            Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.show();

*/

