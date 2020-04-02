package com.example.s3adoon.gp_v;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class About_Us extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RelativeLayout read ;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public About_Us() {
        // Required empty public constructor
    }


    public static About_Us newInstance(String param1, String param2) {
        About_Us fragment = new About_Us();
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
        View view =inflater.inflate(R.layout.fragment_about__us, container, false);

        read=( RelativeLayout)view.findViewById(R.id.read);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogStyle);
                alertDialog.setTitle("About US");
                alertDialog.setMessage("Do you want to see more about App ?");
                alertDialog.setIcon(R.drawable.about);
                alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "As You Like", Toast.LENGTH_SHORT).show();
                        }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getContext(), "We are always at your service", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setNeutralButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        AlertDialog.Builder alert =   new AlertDialog.Builder(getContext(),R.style.AlertDialogStyle);
                        alert.setTitle("Description");
                        alert.setIcon(R.drawable.about);
                        alert.setMessage("1.1 Purpose of this document                          " +
                                "This document has been prepared to provide a medical department system. " +
                                "The system aims to help users to reach the nearest medical unit, whether a clinic, " +
                                "hospital or laboratory analysis.                      "+
                        "1.2 Scope of this document                                   "+
                        "This document will explain what the program is, how it is used, and who the users are."+
                         "The user has experienced problems due to the difficulty of accessing the nearest " +
                                "medical unit as soon as possible. , Has become difficult to access, especially in places unknown to the user. " +
                                "The purpose of this project is to solve these problems through the creation of database programs designed to find the most reliable medical units.                                                      "+
                                "1.3 Overview                                                       " +
                                        "This program will be used to access the nearest medical unit as soon as possible," +
                                        " it is easy for users to record their data. The doctor may add his own clinic and may add him to another clinic" +
                                        " if he has a clinic elsewhere and can also see feedback or reactions from users to improve his / her medical service. " +
                                        "The program will be used by doctors, managers and ordinary users.");


                                alert.setNeutralButton("OK",null);
                                alert.show();
                    }
                });

                alertDialog.show();



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
}

/*
  <LinearLayout
            android:layout_width="300dp"
            android:layout_height="200dp"
            android:orientation="vertical"
            android:background="@drawable/roundedlabel"
            android:layout_marginTop="10dp"
            android:layout_marginLeft="50dp"

            >
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="MEDICO"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="10dp"
                android:textSize="15dp"
                android:textStyle="bold"
                android:textColor="@color/colorblack"
                android:fontFamily="serif"
                />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="20dp"
                android:text="adapt-a-website "
                />


            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="45dp"
                android:text="+ 011 44 3 88 323"/>
            <ImageView
                android:layout_width="20dp"
                android:layout_height="20dp"
                android:background="@drawable/phone"
                android:layout_marginTop="-20dp"
                android:layout_marginLeft="20dp"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="45dp"
                android:text="s3adoOn.ms@gmail.com"
                />
            <ImageView
                android:layout_width="20dp"
                android:layout_height="20dp"
                android:background="@drawable/at"
                android:layout_marginTop="-20dp"
                android:layout_marginLeft="20dp"/>


            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="45dp"
                android:text="mohamedhegazi500@gmail.com"
                />
            <ImageView
                android:layout_width="20dp"
                android:layout_height="20dp"
                android:background="@drawable/at"
                android:layout_marginTop="-20dp"
                android:layout_marginLeft="20dp"/>


            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginLeft="45dp"
                android:text="Egypt"/>
            <ImageView
                android:layout_width="20dp"
                android:layout_height="20dp"
                android:background="@drawable/loc"
                android:layout_marginTop="-20dp"
                android:layout_marginLeft="20dp"/>


        </LinearLayout>


*/