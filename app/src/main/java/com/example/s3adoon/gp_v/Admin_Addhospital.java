package com.example.s3adoon.gp_v;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.s3adoon.gp_v.Hospital;
import com.example.s3adoon.gp_v.Unit;
import com.example.s3adoon.gp_v.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Addhospital extends AppCompatActivity implements View.OnClickListener {


    private Button add;
    ImageView setlocation ;
    int PLACE_PICKER_REQUEST = 1 ;
    private EditText name , location ,start , end ,spec ;
    private DatabaseReference reference ;
    private RadioButton start_pm  , start_am , end_am , end_pm ;
    private  final  int PICK_IMAGE_REQUEST = 71;
    LatLng lat ;
    double x ,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_addhospital);
        reference = FirebaseDatabase.getInstance().getReference("Hospital");


        start_am = findViewById(R.id.start_am);
        start_pm = findViewById(R.id.start_pm);
        end_am = findViewById(R.id.end_am);
        end_pm = findViewById(R.id.end_pm);

        name = findViewById(R.id.hospitalname);
        start = findViewById(R.id.hospitalstart);
        end = findViewById(R.id.hospitalend);
        location = findViewById(R.id.hospitallocation);
        spec = findViewById(R.id.hospitalspec);
        add = findViewById(R.id.btnsubmitup);
        add.setOnClickListener(this);
        setlocation = findViewById(R.id.setlocation);
        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catch_place();
            }
        });

    }

    public void onClick(View v)

    {

        if (v == add)

        {
            search_unit();

        }
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

    static  Hospital u = new Hospital();

    public void search_unit ()
    {
        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Hospital");
        com.google.firebase.database.Query query = re.orderByChild("name").equalTo(name.getText().toString().trim());
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        u = snapshot1.getValue(Hospital.class);

                        Toast.makeText(Admin_Addhospital.this, "Name is Already Exist ", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    add_hospital ();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


    public void add_hospital ()
    {
        if(!TextUtils.isEmpty(name.getText().toString().trim())&&!TextUtils.isEmpty(location.getText().toString().trim())
                &&!TextUtils.isEmpty(start.getText().toString().trim())
                &&!TextUtils.isEmpty(end.getText().toString().trim())
                &&!TextUtils.isEmpty(spec.getText().toString().trim()))
        {


            if (start_am.isChecked() && end_am.isChecked()) {


                Hospital hospital = new Hospital(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim()
                                +" Am", end.getText().toString().trim()+" Am", spec.getText().toString().trim(),x,y);

                if (!reference.push().setValue(hospital).isSuccessful()) {

                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            if (start_am.isChecked() && end_pm.isChecked()) {


                Hospital hospital = new Hospital(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim()
                                +" Am", end.getText().toString().trim()+" Pm", spec.getText().toString().trim(),x,y);

                if (!reference.push().setValue(hospital).isSuccessful()) {

                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }
            }
            if (start_pm.isChecked() && end_pm.isChecked()) {


                Hospital hospital = new Hospital(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim()
                                +" Pm", end.getText().toString().trim()+" Pm", spec.getText().toString().trim(),x,y);

                if (!reference.push().setValue(hospital).isSuccessful()) {

                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else {
                    Toast.makeText(this, "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            if (start_pm.isChecked() && end_am.isChecked()) {


                Hospital hospital = new Hospital(name.getText().toString().trim(), location.getText().toString().trim(),
                        start.getText().toString().trim()
                                +" Pm", end.getText().toString().trim()+" Am", spec.getText().toString().trim(),x,y);

                if (!reference.push().setValue(hospital).isSuccessful()) {

                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Admin_Page.class));
                    finish();
                } else {
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



