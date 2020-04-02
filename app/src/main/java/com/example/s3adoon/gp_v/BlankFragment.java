package com.example.s3adoon.gp_v;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements OnMapReadyCallback  , GoogleMap.OnMarkerClickListener {

    SupportMapFragment supportMapFragment;

    public BlankFragment() {
        // Required empty public constructor
    }
    TextView btn1 , btn2 ;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    int userLocationChoice;



    private GoogleApiClient googleApiClient;
    List< Double> arrlat = new ArrayList<Double>();
    List< Double> arrlng = new ArrayList<Double>();
    List< String> clcname = new ArrayList<String>();

    private Spinner unit_spinner, spec_spinner;
    private static final String[] medical_unit = {"Medical Units", "Pharmacies", "Hospitals", "Labs"};
    private static final String[] spec = {"Doctors", "Eyes", "Heart", "Dentist", "Bones"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, supportMapFragment).commit();







        }
        supportMapFragment.getMapAsync(this);

        unit_spinner = view.findViewById(R.id.unit_spinner);
        spec_spinner = view.findViewById(R.id.spec_spinner);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Direct to Map page
                userLocationChoice =0;
                Intent intent = new Intent(getActivity(), MapPsActivity.class);
                Bundle extra = new Bundle();
                extra.putInt("userCurrentLocation",userLocationChoice);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });




        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Direct to Map page
                userLocationChoice = 1;
                Intent intent = new Intent(getActivity(), MapPsActivity.class);
                Bundle extra = new Bundle();
                extra.putInt("userLocationChoice",userLocationChoice);
                intent.putExtras(extra);
                startActivity(intent);

            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, medical_unit);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter(adapter);



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, spec);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spec_spinner.setAdapter(adapter2);


        unit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:

                        getallunit ("Pharmacist");
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();

                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        getallHospital();
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        break;
                    case 3:
                        getallunit("Unit");

                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        break;

                    // Whatev

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spec_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:

                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        search_clinic(spec[1]);
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        search_clinic(spec[2]);
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        break;
                    case 3:
                        search_clinic(spec[3]);
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        break;
                    case 4:
                        search_clinic(spec[4]);
                        mMap.clear();
                        arrlat.clear();
                        arrlng.clear();
                        clcname.clear();
                        break;
                    // Whatev

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            if(!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if(myLocation!=null){
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());


             //   googleMap.addMarker(new MarkerOptions().position(userLocation).title("It's me."));
                CameraPosition myPosition = new CameraPosition.Builder()
                        .target(userLocation).zoom(17).bearing(90).tilt(30).build();
                mMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(myPosition));


            }
        }

        mMap.setOnMarkerClickListener(this);


    }
    @Override
    public boolean onMarkerClick(final Marker marker)
    {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(),R.style.AlertDialogStyle);
        alertDialog.setTitle("Content");
        alertDialog.setMessage("Are you Sure to Show more Details ?");
        alertDialog.setIcon(R.drawable.global);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String mark = marker.getTitle();
                Intent in = new Intent(getContext(), Data_Marker.class);
                in.putExtra("mark", mark);

                getActivity().startActivity(in);


            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

        return false;
    }


    static Hospital hospital = new Hospital();

public void getallHospital ()
{

    mMap.clear();
    arrlat.clear();
    arrlng.clear();
    clcname.clear();

   FirebaseDatabase.getInstance().getReference().child("Hospital").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren())
            {
                hospital = snapshot.getValue(Hospital.class);
                   arrlat.add(hospital.getLat());
                    arrlng.add(hospital.getLng());
                    clcname.add(hospital.getName());

        }
        for (int i=0;i<arrlat.size();i++)
        {
            LatLng add=new LatLng(arrlat.get(i),arrlng.get(i));
            mMap.addMarker(new MarkerOptions()
                    .position(add)
                    .draggable(true)
                    .title(clcname.get(i)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(add));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }

           // Toast.makeText(getActivity(), " "+arrlat.get(0)+" "+arrlng.get(0), Toast.LENGTH_SHORT).show();

               }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


}

    static Unit unit = new Unit();

    public void getallunit (String x)
    {

        mMap.clear();
        arrlat.clear();
        arrlng.clear();
        clcname.clear();

        FirebaseDatabase.getInstance().getReference().child(x).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    unit = snapshot.getValue(Hospital.class);
                    arrlat.add(unit.getLat());
                    arrlng.add(unit.getLng());
                    clcname.add(unit.getName());


                }
                for (int i=0;i<arrlat.size();i++)
                {
                    LatLng add=new LatLng(arrlat.get(i),arrlng.get(i));
                    mMap.addMarker(new MarkerOptions()
                            .position(add)
                            .draggable(true)
                            .title(clcname.get(i)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(add));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    mMap.getUiSettings().setZoomControlsEnabled(true);}
              //  Toast.makeText(getActivity(), " "+arrlat.get(0)+" "+arrlng.get(0), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    //************************************************

    static  Clinic c = new Clinic();

    public  void search_clinic ( String x)
    {
        mMap.clear();
        arrlat.clear();
        arrlng.clear();
        clcname.clear();


        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("spec_state").equalTo(x+"complete");
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        c = snapshot1.getValue(Clinic.class);
                        arrlat.add(c.getLat());
                        arrlng.add(c.getLng());
                        clcname.add(c.getClinicname());

                    }


                    for (int i=0;i<arrlat.size();i++)
                    {
                        LatLng add=new LatLng(arrlat.get(i),arrlng.get(i));
                        mMap.addMarker(new MarkerOptions()
                                .position(add)
                                .draggable(true)
                                .title(clcname.get(i)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(add));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        mMap.getUiSettings().setZoomControlsEnabled(true);}
                    //  Toast.makeText(getActivity(), " "+arrlat.get(0)+" "+arrlng.get(0), Toast.LENGTH_SHORT).show();
                }

                else
                {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



    }


}



