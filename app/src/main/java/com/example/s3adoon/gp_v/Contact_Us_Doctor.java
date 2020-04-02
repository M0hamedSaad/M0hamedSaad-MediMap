package com.example.s3adoon.gp_v;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Contact_Us_Doctor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference reference;

    private Button submit ;
    private static FirebaseAuth auth;
    private EditText text ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Contact_Us_Doctor() {
        // Required empty public constructor
    }


    public static Contact_Us_Doctor newInstance(String param1, String param2) {
        Contact_Us_Doctor fragment = new Contact_Us_Doctor();
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

        View view = inflater.inflate(R.layout.fragment_contact__us, container, false);

        submit = view.findViewById(R.id.btnsubmitcontact);
        text = view.findViewById(R.id.contact);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdocname();
                getusername();
            }
        });




        return view ;
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


    static  Doctor u = new Doctor();

    public void getdocname() {


        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Doctor");
        auth = FirebaseAuth.getInstance();
        String key =auth.getCurrentUser().getUid();


        com.google.firebase.database.Query query = data.orderByChild("id").equalTo(key);
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener()
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
                    addfeedback(u.getEmail());
                    //     Toast.makeText(getActivity(), ""+u.getEmail(), Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    static  User x  = new User();

    public void getusername() {


        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("User");
        auth = FirebaseAuth.getInstance();
        String key =auth.getCurrentUser().getUid();


        com.google.firebase.database.Query query = data.orderByChild("id").equalTo(key);
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
            {

                if (dataSnapshot.exists())
                {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {
                        x = snapshot1.getValue(User.class);


                    }
                    addfeedbackuser(x.getEmail());
                    //     Toast.makeText(getActivity(), ""+u.getEmail(), Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }








    public  void addfeedback (String e)
    {

        if (!TextUtils.isEmpty(text.getText().toString().trim()))
        {


            reference = FirebaseDatabase.getInstance().getReference("Contact_Us");

            Feedback unit = new Feedback(e, text.getText().toString(),"Doctor");


            if (!reference.push().setValue(unit).isSuccessful()) {
                Toast.makeText(getContext(), "Sent Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Doctor_Page.class));
                getActivity().finish();
            } else if (reference.push().setValue(unit).isSuccessful()) {
                Toast.makeText(getContext(), "Sent Not Successfully", Toast.LENGTH_SHORT).show();
            }

        }

        else
        {
            Toast.makeText(getContext(), "Your Message is Empty", Toast.LENGTH_SHORT).show();
        }


    }





    public  void addfeedbackuser (String e)
    {

        if (!TextUtils.isEmpty(text.getText().toString().trim()))
        {


            reference = FirebaseDatabase.getInstance().getReference("Contact_Us");

            Feedback unit = new Feedback(e, text.getText().toString(),"User");


            if (!reference.push().setValue(unit).isSuccessful()) {
                Toast.makeText(getContext(), "Sent Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), User_Page.class));
                getActivity().finish();
            } else if (reference.push().setValue(unit).isSuccessful()) {
                Toast.makeText(getContext(), "Sent Not Successfully", Toast.LENGTH_SHORT).show();
            }

        }

        else
        {
            Toast.makeText(getContext(), "Your Meesage is Empty", Toast.LENGTH_SHORT).show();
        }


    }








}


