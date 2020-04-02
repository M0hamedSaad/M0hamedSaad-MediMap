package com.example.s3adoon.gp_v;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Update_Add_unit extends AppCompatActivity implements View.OnClickListener {
    private Button update;
    public EditText name, location, type, start, end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_update_add_unit);

        name = findViewById(R.id.unitname);
        location = findViewById(R.id.unitlocation);
        start = findViewById(R.id.unitstart);
        end = findViewById(R.id.unitend);
        type = findViewById(R.id.unittype);


        update = findViewById(R.id.updateunit);


        update.setOnClickListener(this);
        catch_data();

    }

    public void onClick(View v)

    {
        if (v == update)
        {
//https://medico-5bfea.firebaseio.com/
            //https://medico-5bfea.firebaseio.com/
            if (!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(location.getText().toString().trim())
                    && !TextUtils.isEmpty(start.getText().toString().trim()) && !TextUtils.isEmpty(end.getText().toString().trim())
                    && !TextUtils.isEmpty(type.getText().toString().trim()))

            {
                update_unit ();
                update_pharma ();
                update_Hospital ();


            }
            else
            {
                Toast.makeText(this, " Please Complete Required Data ", Toast.LENGTH_SHORT).show();
            }




        }
        // public Unit(String name ,String location,String start , String end ,String type )
    }




    public void catch_data ()
    {

        Bundle extras = getIntent().getExtras();
        String[] array = extras.getStringArray("key");
        name.setText(array[0]);
        location.setText(array[1]);
        type.setText(array[2]);
        start.setText(array[3]);
        end.setText(array[4]);

    }
public String catch_id ( )
{
    String id ;
    Bundle extras = getIntent().getExtras();
    String[] array = extras.getStringArray("key");
    id=array[5];
    return  id;
}


public void update_unit ()
{

    Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
    Firebase objRef = m_objFireBaseRef.child("Unit");
    objRef.child(catch_id()).child("name").setValue(name.getText().toString());
    objRef.child(catch_id()).child("location").setValue(location.getText().toString());
    objRef.child(catch_id()).child("type").setValue(type.getText().toString());
    objRef.child(catch_id()).child("start").setValue(start.getText().toString());
    objRef.child(catch_id()).child("end").setValue(end.getText().toString());
    Toast.makeText(this, "Save changed", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(getApplication(), Admin_Page.class));
    finish();

}

    public void update_Hospital ()
    {

        Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
        Firebase objRef = m_objFireBaseRef.child("Hospital");
        objRef.child(catch_id()).child("name").setValue(name.getText().toString());
        objRef.child(catch_id()).child("location").setValue(location.getText().toString());
        objRef.child(catch_id()).child("spec").setValue(type.getText().toString());
        objRef.child(catch_id()).child("start").setValue(start.getText().toString());
        objRef.child(catch_id()).child("end").setValue(end.getText().toString());
        Toast.makeText(this, "Save changed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplication(), Admin_Page.class));
        finish();


    }

    public void update_pharma ()
    {

        Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
        Firebase objRef = m_objFireBaseRef.child("Pharmacist");
        objRef.child(catch_id()).child("name").setValue(name.getText().toString());
        objRef.child(catch_id()).child("location").setValue(location.getText().toString());
        objRef.child(catch_id()).child("type").setValue(type.getText().toString());
        objRef.child(catch_id()).child("start").setValue(start.getText().toString());
        objRef.child(catch_id()).child("end").setValue(end.getText().toString());
        Toast.makeText(this, "Save changed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplication(), Admin_Page.class));
        finish();


    }


}