package com.example.s3adoon.gp_v;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Deleteunit extends AppCompatActivity implements View.OnClickListener {


    private Button delete;
    private EditText unit_delete;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_delete_unit);
        reference = FirebaseDatabase.getInstance().getReference("Unit");

        unit_delete = findViewById(R.id.unit_delete);
        delete = findViewById(R.id.btndelete);

        delete.setOnClickListener(this);
    }


    public void onClick(View v)

    {

        if (v == delete)
        {
            String unitname = unit_delete.getText().toString().trim();


            if (TextUtils.isEmpty(unitname)) {
                Toast.makeText(getApplication(), "Enter Unit Name To Delete  ", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!TextUtils.isEmpty(unitname))
            {

                Delete_Unit();

            }
        }
    }

    static  Unit u = new Unit();
    private void Delete_Unit( )
    {
        String unitname = unit_delete.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Unit");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);
                        snapshot1.getRef().removeValue();

                        Toast.makeText(Admin_Deleteunit.this, "Delete Successfully  ", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                }

                else
                {
                    Delete_Hospital();
                    Delete_Pharma();

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


    static  Hospital h = new Hospital();
    private Object Delete_Hospital()
    {

        String unitname = unit_delete.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Hospital");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        h = snapshot1.getValue(Hospital.class);
                        snapshot1.getRef().removeValue();
                        Toast.makeText(Admin_Deleteunit.this, "Delete Successfully  ", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }

                else
                { Toast.makeText(Admin_Deleteunit.this, "This Name is Not Exist ", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        return (Object) h ;
    }


    private void Delete_Pharma( )
    {
        String unitname = unit_delete.getText().toString().trim();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Pharmacist");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(unitname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);
                        snapshot1.getRef().removeValue();

                        Toast.makeText(Admin_Deleteunit.this, "Delete Successfully  ", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                }

                else
                {
                    Toast.makeText(Admin_Deleteunit.this, "This Name is Not Exist ", Toast.LENGTH_SHORT).show();}






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


}



