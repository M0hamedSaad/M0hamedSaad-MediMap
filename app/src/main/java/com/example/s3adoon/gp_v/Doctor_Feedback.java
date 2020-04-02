package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Doctor_Feedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final List<Feedback_user> list1 = new ArrayList<Feedback_user>();

    RecyclerView recyclerView ;
    ArrayList<ModelList> modelLists ;
    SwipeRefreshLayout swiper;
    Item_Adapter_Feedback adapter ;
    private OnFragmentInteractionListener mListener;

    public Doctor_Feedback() {
        // Required empty public constructor
    }


    public static Doctor_Feedback newInstance(String param1, String param2) {
        Doctor_Feedback fragment = new Doctor_Feedback();
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
        View view = inflater.inflate(R.layout.fragment_doctor_feedback, container, false);


        adapter= new Item_Adapter_Feedback(getActivity(),modelLists,swiper);

        swiper = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        Intent intent = getActivity().getIntent();
        recyclerView = view.findViewById(R.id.rv);
        refresh ();
        Search_Unit(list1);
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

    public void create_adapter()
    {


        modelLists = new ArrayList<>();


        for (int i=0;i<list1.size();i++)
        {
            modelLists.add(new ModelList(list1.get(i).getClinic_cname(),list1.get(i).getUser_email(),list1.get(i).getMesaage(),""));

        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rvlayoutManager = layoutManager ;

        recyclerView.setLayoutManager(rvlayoutManager);

        Item_Adapter_Feedback item_adapter  = new Item_Adapter_Feedback(getActivity(),modelLists,swiper);
        recyclerView.setAdapter(item_adapter);
        recyclerView.invalidate();

    }

    private List<Feedback_user> Search_Unit(final List<Feedback_user> list)
    {


        final ProgressDialog pd = new ProgressDialog(getActivity());

        pd.setMessage("Loading... Please wait");
        pd.show();



        // final List<Clinic> list = new ArrayList<Clinic>();


        FirebaseAuth auth ;
        auth =FirebaseAuth.getInstance();
        String email=auth.getCurrentUser().getEmail();
        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("User_feedback");
        com.google.firebase.database.Query query = re.orderByChild("doc_email").equalTo(email);
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener()

        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {

                        list.add(snapshot1.getValue(Feedback_user.class));


                    }
                    pd.dismiss();

                    create_adapter();
                }



                else {
                    try {
                        modelLists.clear();
                        pd.dismiss();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        return list;
    }


    public  void refresh ()
    {



        swiper.setColorSchemeResources(R.color.bluedark,R.color.orange,R.color.backgroundcolor);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiper.setRefreshing(false);

                    }
                },2000);
            }
        });





    }


}


