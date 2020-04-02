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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s3adoon.gp_v.Unit;
import com.example.s3adoon.gp_v.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Addunit extends AppCompatActivity implements View.OnClickListener {


    private Button add;
    private EditText name, location, type, start, end;
    private DatabaseReference reference;
    private RadioButton start_pm  , start_am , end_am , end_pm ;
    ImageView  setlocation ;
    int PLACE_PICKER_REQUEST = 1 ;
    private  final  int PICK_IMAGE_REQUEST = 71;
    LatLng lat ;
    double x ,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_addunit);

        name = findViewById(R.id.unitname);
        location = findViewById(R.id.unitlocation);
        start = findViewById(R.id.unitstart);
        end = findViewById(R.id.unitend);
        type = findViewById(R.id.unittype);
        setlocation = findViewById(R.id.setlocation);
        start_am = findViewById(R.id.start_am);
        start_pm = findViewById(R.id.start_pm);
        end_am = findViewById(R.id.end_am);
        end_pm = findViewById(R.id.end_pm);

        add = findViewById(R.id.addunit);


        add.setOnClickListener(this);
        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catch_place();
            }
        });
    }

    public void onClick(View v)

    {
        // public Unit(String name ,String location,String start , String end ,String type )
        if (v == add)

        {
            search_unit();

        }
    }


    static  Unit u = new Unit();

    public void search_unit ()
    {

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Unit");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(name.getText().toString().trim());
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Unit.class);

                        Toast.makeText(Admin_Addunit.this, "Name is Already Exist ", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    add_unit ();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("Place :%s", place.getAddress());
                lat = place.getLatLng();
                x = lat.latitude;
                y = lat.longitude;


            }
        }
    }

    public void add_unit ()
    {
        if (!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(location.getText().toString().trim())
                && !TextUtils.isEmpty(start.getText().toString().trim()) && !TextUtils.isEmpty(end.getText().toString().trim())
                && !TextUtils.isEmpty(type.getText().toString().trim()))

        {


            reference = FirebaseDatabase.getInstance().getReference("Unit");


            if (start_am.isChecked() && end_am.isChecked()) {


                Unit unit = new Unit(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim() + " Am", end.getText().toString().trim() + " Am", type.getText().toString().trim(),x,y);


                if (!reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else if (reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }

            if (start_am.isChecked() && end_pm.isChecked()) {


                Unit unit = new Unit(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim() + " Am", end.getText().toString().trim() + " Pm", type.getText().toString().trim(),x,y);


                if (!reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else if (reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


            if (start_pm.isChecked() && end_pm.isChecked()) {


                Unit unit = new Unit(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim() + " Pm", end.getText().toString().trim() + " Pm", type.getText().toString().trim(),x,y);


                if (!reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else if (reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


            if (start_pm.isChecked() && end_am.isChecked()) {


                Unit unit = new Unit(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim() + " Pm", end.getText().toString().trim() + " Am", type.getText().toString().trim(),x,y);


                if (!reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else if (reference.push().setValue(unit).isSuccessful()) {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


        }


            else
        {
            Toast.makeText(this, " Please Complete Required Data ", Toast.LENGTH_SHORT).show();
        }

    }



    public void catch_place ()
    {

        PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
        Intent intent ;
        try {
            intent = builder.build(this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);

        }
        catch (GooglePlayServicesRepairableException e)
        {
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e)

        {
            e.printStackTrace();
        }


    }

}























