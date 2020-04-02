package com.example.s3adoon.gp_v;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Searchunit extends AppCompatActivity implements View.OnClickListener {


    private Button search;
    private EditText unit_search;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_search_unit);
        reference = FirebaseDatabase.getInstance().getReference("Unit");

        unit_search = findViewById(R.id.unit_search);
        search = findViewById(R.id.btnsearch);

        search.setOnClickListener(this);
    }


    public void onClick(View v)

    {

        if (v == search)
        {
            String unitname = unit_search.getText().toString().trim();
            Unit u = new Unit();

            if (TextUtils.isEmpty(unitname)) {
                Toast.makeText(getApplication(), "Enter Unit Name To Search  ", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!TextUtils.isEmpty(unitname))
            {

                Search_Unit();

            }
        }
    }

    static  Unit u = new Unit();
    private Object Search_Unit()
    {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Unit");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_Searchunit.this, R.style.AlertDialogStyle);
                        alertDialog.setTitle("Unit Information ");
                        alertDialog.setIcon(R.drawable.about);
                        alertDialog.setMessage("\n"+
                                "Name           : " +u.getName()+"\n"
                                +"Type             : " +u.getType()+"\n"
                                +"Location      : " +u.getLocation()+"\n"
                                +"Start Time   : " +u.getStart()+"\n"
                                +"End Time     : " +u.getEnd()+"\n"+"\n"

                        );
                        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();

                            }
                        });
                        alertDialog.show();

                    }

                }

                else
                    {
                        Search_Hospital();
                        Search_Pharama();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        return (Object) u ;
    }








    static  Hospital h = new Hospital();
    private Object Search_Hospital()
    {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Hospital");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        h = snapshot1.getValue(Hospital.class);

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_Searchunit.this, R.style.AlertDialogStyle);
                        alertDialog.setTitle("Hospital Information ");
                        alertDialog.setIcon(R.drawable.about);
                        alertDialog.setMessage("\n"+
                                "Name               : " +h.getName()+"\n"
                                +"Type                 : " +h.getType()+"\n"
                                +"Location          : " +h.getLocation()+"\n"
                                +"Specialization : " +h.getSpec()+"\n"
                                +"Start Time       : " +h.getStart()+"\n"
                                +"End Time         : " +h.getEnd()+"\n"+"\n"

                        );

                        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
                        alertDialog.show();


                        alertDialog.show();

                    }

                }
                else
                { Toast.makeText(Admin_Searchunit.this, "This Name is Not Exist ", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return (Object) h ;
    }



    private Object Search_Pharama()
    {
        String unitname = unit_search.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Pharmacist");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Hospital.class);

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_Searchunit.this, R.style.AlertDialogStyle);
                        alertDialog.setTitle("Pharmacist Information ");
                        alertDialog.setIcon(R.drawable.about);
                        alertDialog.setMessage("\n"+
                                "Name           : " +u.getName()+"\n"
                                +"Type             : " +u.getType()+"\n"
                                +"Location      : " +u.getLocation()+"\n"
                                +"Start Time   : " +u.getStart()+"\n"
                                +"End Time     : " +u.getEnd()+"\n"+"\n"

                        );
                        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();

                            }
                        });
                        alertDialog.show();

                    }

                }

                else
                { Toast.makeText(Admin_Searchunit.this, "This Name is Not Exist ", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return (Object) h ;
    }



}



