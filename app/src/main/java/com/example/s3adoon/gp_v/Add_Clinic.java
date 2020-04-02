package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.location.places.*; // "Place" is not resolved
import com.google.android.gms.maps.model.LatLng;

import java.sql.Struct;

import static android.app.Activity.RESULT_OK;


public class Add_Clinic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText  clinicname  , location , date_of_birth , sdd   ,start,end ;
    private Button card , pic;
    int PLACE_PICKER_REQUEST = 1 ;
    TextView spec ;
    private Button send ;
    private RadioButton start_am  , end_am,start_pm,end_pm ;
    private DatabaseReference reference;
    private String status="pending";
    private static FirebaseAuth auth;
    FirebaseStorage storage ;
    private Spinner spinner ;
    StorageReference storageReference ;
    Uri pathurl , pathurl1;
    LatLng lat ;
    private static final String[]paths = {"","Eyes", "Heart", "Dentist" , "Bones"};
    double x ,y;
    ImageView img , setlocation ;
   // private Uri pathurl;


    private  final  int PICK_IMAGE_REQUEST = 71;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Add_Clinic()
    {
        // Required empty public constructor
    }


    public static Add_Clinic newInstance(String param1, String param2) {
        Add_Clinic fragment = new Add_Clinic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =inflater.inflate(R.layout.fragment_addclinic, container, false);

        getdocname();
        clinicname =  (EditText) view.findViewById(R.id.clinicname);
        location =  (EditText) view.findViewById(R.id.location);
        date_of_birth =  (EditText) view.findViewById(R.id.date);
        sdd =  (EditText) view.findViewById(R.id.sdd);
        spec =   view.findViewById(R.id.spec);
        card =   view.findViewById(R.id.card);

        setlocation =   view.findViewById(R.id.setlocation);
        //pic =   view.findViewById(R.id.pic);
        start = (EditText) view.findViewById(R.id.edstarttime);
        end = (EditText) view.findViewById(R.id.edlasttime);
        spinner = view.findViewById(R.id.spinner);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        start_am = view.findViewById(R.id.start_am);
        end_am = view.findViewById(R.id.end_am);
        start_pm = view.findViewById(R.id.start_pm);
        end_pm = view.findViewById(R.id.end_pm);
        img = view.findViewById(R.id.imgview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        send  =   view.findViewById(R.id.send);
        img.setVisibility(View.INVISIBLE);
        //imgpic.setVisibility(View.INVISIBLE);

        setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                BlankFragment llf = new BlankFragment();
                ft.replace(R.id.frag, llf);
                ft.commit();
*/
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading ...");
                progressDialog.show();
                catch_place();
                progressDialog.dismiss();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:

                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        spec.setText(paths[1]);
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        spec.setText(paths[2]);
                        break;
                    case 3:
                        spec.setText(paths[3]);
                        break;
                    case 4:
                        spec.setText(paths[4]);
                        break;
                        // Whatev

            }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        send.setVisibility(View.INVISIBLE);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (!TextUtils.isEmpty(clinicname.getText().toString().trim())
                        && !TextUtils.isEmpty(location.getText().toString().trim())
                        && !TextUtils.isEmpty(date_of_birth.getText().toString().trim())
                        && !TextUtils.isEmpty(sdd.getText().toString().trim())
                        && !TextUtils.isEmpty(spec.getText().toString().trim())
                        && !TextUtils.isEmpty(card.getText().toString().trim())
                        && !TextUtils.isEmpty(start.getText().toString().trim())
                        && !TextUtils.isEmpty(end.getText().toString().trim())
                        )

                {
                    choose();
                send.setVisibility(View.VISIBLE);

                }

                else
                {
                    Toast.makeText(getActivity(), "Enter Required Data!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    search_clinic();

            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    static  Clinic c = new Clinic();

    public  void search_clinic ()
    {
        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("clinicname").equalTo(clinicname.getText().toString().trim());
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        c = snapshot1.getValue(Clinic.class);

                        Toast.makeText(getContext(), "Name is Already Exist ", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {
                    add_clinic ();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }


    public  void add_clinic ()
    {


        if (!TextUtils.isEmpty(clinicname.getText().toString().trim())
                && !TextUtils.isEmpty(location.getText().toString().trim())
                && !TextUtils.isEmpty(date_of_birth.getText().toString().trim())
                && !TextUtils.isEmpty(sdd.getText().toString().trim())
                && !TextUtils.isEmpty(spec.getText().toString().trim())
                && !TextUtils.isEmpty(card.getText().toString().trim())
                && !TextUtils.isEmpty(start.getText().toString().trim())
                && !TextUtils.isEmpty(end.getText().toString().trim())
                )

        {


            reference = FirebaseDatabase.getInstance().getReference("Clinic");


    /*    public Clinic( String clinicname , String location , String date_of_birth ,String sdd ,
                   String spec , String card,String start,String end,String name,String status)
                   */

            if (start_am.isChecked() && end_am.isChecked()) {


                Clinic c = new Clinic(clinicname.getText().toString().trim(), location.getText().toString().trim(),
                        date_of_birth.getText().toString().trim(), sdd.getText().toString().trim(),
                        spec.getText().toString().trim(), "",
                        start.getText().toString().trim() + " Am", end.getText().toString().trim()
                        + " Am", getdocname(), status, getdocname() + status ,x,y, spec.getText().toString().trim()+ status);


                if (!reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Doctor_Page.class));
                    getActivity().getFragmentManager().popBackStack();
                } else if (reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }

            if (start_am.isChecked() && end_pm.isChecked()) {


                Clinic c = new Clinic(clinicname.getText().toString().trim(), location.getText().toString().trim(),
                        date_of_birth.getText().toString().trim(), sdd.getText().toString().trim(),
                        spec.getText().toString().trim(), "",
                        start.getText().toString().trim() + " Am", end.getText().toString().trim()
                        + " Pm", getdocname(), status, getdocname() + status , x,y,spec.getText().toString().trim()+ status);

                if (!reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Doctor_Page.class));
                    getActivity().getFragmentManager().popBackStack();
                } else if (reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


            if (start_pm.isChecked() && end_pm.isChecked()) {


                Clinic c = new Clinic(clinicname.getText().toString().trim(), location.getText().toString().trim(),
                        date_of_birth.getText().toString().trim(), sdd.getText().toString().trim(),
                        spec.getText().toString().trim(), "",
                        start.getText().toString().trim() + " Pm", end.getText().toString().trim()
                        + " Pm", getdocname(), status, getdocname() + status, x,y,spec.getText().toString().trim()+ status);


                if (!reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Doctor_Page.class));
                    getActivity().getFragmentManager().popBackStack();
                } else if (reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


            if (start_pm.isChecked() && end_am.isChecked()) {


                Clinic c = new Clinic(clinicname.getText().toString().trim(), location.getText().toString().trim(),
                        date_of_birth.getText().toString().trim(), sdd.getText().toString().trim(),
                        spec.getText().toString().trim(), "",
                        start.getText().toString().trim() + " Pm", end.getText().toString().trim()
                        + " Am", getdocname(), status, getdocname() + status, x,y,spec.getText().toString().trim()+ status);

                if (!reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Doctor_Page.class));
                    getActivity().getFragmentManager().popBackStack();
                } else if (reference.push().setValue(c).isSuccessful()) {
                    Toast.makeText(getContext(), "Added Not Successfully", Toast.LENGTH_SHORT).show();
                }


            }


        }

        else
        {
            Toast.makeText(getContext(), " Please Complete Required Data ", Toast.LENGTH_SHORT).show();
        }

    }



 static  Doctor u = new Doctor();

    private String getdocname() {


        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Doctor");
        auth = FirebaseAuth.getInstance();
        String key =auth.getCurrentUser().getUid();


        com.google.firebase.database.Query query = data.orderByChild("id").equalTo(key);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {
                        u = snapshot1.getValue(Doctor.class);


                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return u.getEmail();

    }


    private void choose ()
    {
        img.setVisibility(View.VISIBLE);

        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in,"Select Picture"),PICK_IMAGE_REQUEST);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null)
        {
            pathurl = data.getData();
            upload_img();





            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),pathurl);
                img.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

         if (requestCode == PLACE_PICKER_REQUEST)
         {
             if (resultCode == RESULT_OK)
             {
                 Place place = PlacePicker.getPlace(data,getContext());
                 String address = String.format("Place :%s",place.getAddress());
                 lat = place.getLatLng();
                 x=lat.latitude;
                  y=lat.longitude;



             }
         }


        }




    public  void upload_img ()
{

    final ProgressDialog progressDialog = new ProgressDialog(getContext());
    progressDialog.setTitle("Uploading...");
    progressDialog.show();
    StorageReference  ref = storageReference.child("images/"+clinicname.getText().toString().trim() );
//UUID.randomUUID().toString()

    ref.putFile(pathurl).addOnSuccessListener(getActivity(),new OnSuccessListener<UploadTask.TaskSnapshot>()
    {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
        }
    })
            .addOnFailureListener(getActivity(),new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(getActivity(),new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });

}


public void catch_place ()
{


    PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
    Intent intent ;
    try {
                intent = builder.build(getActivity());
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
