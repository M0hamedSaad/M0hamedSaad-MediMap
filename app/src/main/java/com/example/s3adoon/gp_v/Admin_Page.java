package com.example.s3adoon.gp_v;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class Admin_Page extends AppCompatActivity implements View.OnClickListener {


   private ImageView addunit, updateunit, deleteunit, searchunit, verify ,logout ;
    private TextView ok;

    final int[] count = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.admin_page);
        addunit = findViewById(R.id.addunit);
        updateunit = findViewById(R.id.updateunit);
        deleteunit = findViewById(R.id.deleteunit);
        searchunit = findViewById(R.id.searchunit);
        ok = findViewById(R.id.ok);
        verify = findViewById(R.id.verify);
        logout = findViewById(R.id.logout);
        addunit.setOnClickListener(this);
        updateunit.setOnClickListener(this);
        deleteunit.setOnClickListener(this);
        searchunit.setOnClickListener(this);
        verify.setOnClickListener(this);
        logout.setOnClickListener(this);
        notification();
        ok.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // finish();
        //System.exit(0);
        finishAffinity();
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    public void onClick(View v)

    {
         if (v == addunit)
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Add Unit");
            alertDialog.setMessage("Please Choose Unit To Add ...");
            alertDialog.setIcon(R.drawable.aadpo);

            alertDialog.setPositiveButton("Hospital", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplication(),Admin_Addhospital.class));
                }
            });
            alertDialog.setNegativeButton("Pharmacy", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplication(),Admin_Addpharma.class));
                }
            });
            alertDialog.setNeutralButton("Lab", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplication(),Admin_Addunit.class));
                }
            });

            alertDialog.show();

        }

        else if (v == updateunit)
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Update Unit");
            alertDialog.setMessage("You Can use Unit Name To Update ...");
            alertDialog.setIcon(R.drawable.updateo);
            alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(getApplication(),Admin_Update_Search_unit.class));

                }
            });
            alertDialog.setPositiveButton("Cancel",null);
            alertDialog.show();

        }
          else if (v == deleteunit)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Delete Unit");
            alertDialog.setMessage("You Can use Unit Name To Delete ...");
            alertDialog.setIcon(R.drawable.delo);
            alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                     startActivity(new Intent(getApplication(),Admin_Deleteunit.class));

                }
            });
            alertDialog.setPositiveButton("Cancel",null);
            alertDialog.show();



    }


        else if (v == searchunit)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Search Unit");
            alertDialog.setMessage("You Can use Unit Name To Search ...");
            alertDialog.setIcon(R.drawable.searcho);
            alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(getApplication(),Admin_Searchunit.class));

                }
            });
            alertDialog.setPositiveButton("Cancel",null);
            alertDialog.show();

        }

        else if (v == verify)
        {
            startActivity(new Intent(this,Verify.class));
        }

        else if (v == logout)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            alertDialog.setTitle("Log Out");
            alertDialog.setMessage("Are you Sure to Logout ?");
            alertDialog.setIcon(R.drawable.logo);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //action code
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Admin_Page.this,MainActivity.class));
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //action code

                    Toast.makeText(getBaseContext(), "As You Like", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.show();

        }


    }

    public void notification()
    {

        final ProgressDialog pd = new ProgressDialog(Admin_Page.this);

        pd.setMessage("Loading... Please wait");
        pd.show();


        // final List<Clinic> list = new ArrayList<Clinic>();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("status").equalTo("pending");
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {


                count[0]++;


                    }

                    pd.dismiss();

                    ok.setVisibility(View.VISIBLE);
                    ok.setText(Integer.toString(count[0]));
                    sound();
                }



                else {



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }




    public void sound()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.verfiy)
                        .setContentTitle("Notifications")
                        .setContentText( count[0]+" Clinic waited for your Verification.")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true);



        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(0, mBuilder.build());
    }

}