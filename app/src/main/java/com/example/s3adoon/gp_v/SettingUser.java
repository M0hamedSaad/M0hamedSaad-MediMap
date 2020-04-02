package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingUser extends Fragment {
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


    public SettingUser() {
        // Required empty public constructor
    }


    public static SettingUser newInstance(String param1, String param2) {
        SettingUser fragment = new SettingUser();
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


        query_get_UserType();

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
            update_user();

        }
        else
        {
            pass.setError("Password id to short");
        }
    }



    static User doc = new User();

    private void query_get_UserType() {
        UID = auth.getCurrentUser().getUid();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("User");
        com.google.firebase.database.Query query = re.orderByChild("id").equalTo(UID);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        doc = snapshot1.getValue(User.class);

                    }
                    ////////////
                    name.setText(doc.getFirstname() + " " + doc.getLastname());
                    email.setText(doc.getEmail());
                    phone.setText(doc.getPhone());
                    pass.setText(doc.getPassword());

                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





   public void update_user() {

        UID = auth.getCurrentUser().getUid();

        Firebase m_objFireBaseRef = new Firebase("https://medico-5bfea.firebaseio.com/");
        Firebase objRef = m_objFireBaseRef.child("User");
        objRef.child(UID).child("password").setValue(pass.getText().toString());

    }

}