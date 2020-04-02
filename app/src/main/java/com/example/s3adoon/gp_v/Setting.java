package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static FirebaseAuth auth;
    private TextView name, email, phone, save;
    private EditText pass;
    String UID;


    public Setting() {
        // Required empty public constructor
    }


    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        auth = FirebaseAuth.getInstance();

        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        save = view.findViewById(R.id.save);


        query_get_Doctortype();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                update();
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


    public void update()
    {
        if(pass.getText().length()>=6)
        {

            UID = auth.getCurrentUser().getUid();
            auth.getCurrentUser().updatePassword(pass.getText().toString());
            Toast.makeText(getContext(), "Save changed", Toast.LENGTH_SHORT).show();
            update_doctor();

        }
        else
        {
            pass.setError("Password id to short");
        }
    }


    static Doctor doc1 = new Doctor();

    private void query_get_Doctortype() {
        UID = auth.getCurrentUser().getUid();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Doctor");
        com.google.firebase.database.Query query = re.orderByChild("id").equalTo(UID);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        doc1 = snapshot1.getValue(Doctor.class);

                    }
                    ////////////
                    name.setText(doc1.getFirstname() + " " + doc1.getLastname());
                    email.setText(doc1.getEmail());
                    phone.setText(doc1.getPhone());
                    pass.setText(doc1.getPassword());


                    progressDialog.dismiss();
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void update_doctor() {

                    UID = auth.getCurrentUser().getUid();

                    Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
                    Firebase objRef = m_objFireBaseRef.child("Doctor");
                    objRef.child(UID).child("password").setValue(pass.getText().toString());

    }


}