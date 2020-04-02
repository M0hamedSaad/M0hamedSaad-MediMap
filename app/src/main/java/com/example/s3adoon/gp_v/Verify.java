package com.example.s3adoon.gp_v;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Verify extends AppCompatActivity {

   final  List<Clinic> list1 = new ArrayList<Clinic>();

    RecyclerView recyclerView ;
    ArrayList<ModelList> modelLists ;
    SwipeRefreshLayout swiper;
    Item_Adapter adapter ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.verify);
        adapter=new Item_Adapter(this,modelLists,swiper);

         swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);




         /*swiper.setColorSchemeResources(R.color.bluedark,R.color.orange,R.color.backgroundcolor);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiper.setRefreshing(false);



                    }
                },3000);
            }
        });
*/
        recyclerView = findViewById(R.id.rv);

        Search_Unit(list1);







    }


    public void create_adapter()
    {


        modelLists = new ArrayList<>();


        for (int i=0;i<list1.size();i++)
        {
            modelLists.add(new ModelList(list1.get(i).getClinicname(),list1.get(i).getName(),"show more",list1.get(i).getSpec()));

        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvlayoutManager = layoutManager ;

        recyclerView.setLayoutManager(rvlayoutManager);

        Item_Adapter item_adapter  =  new Item_Adapter(this,modelLists,swiper);
        recyclerView.setAdapter(item_adapter);
        recyclerView.invalidate();

    }

    private List<Clinic> Search_Unit(final List<Clinic> list)
    {

        try {
            modelLists.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        final ProgressDialog pd = new ProgressDialog(Verify.this);

        pd.setMessage("Loading... Please wait");
        pd.show();



        // final List<Clinic> list = new ArrayList<Clinic>();

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Clinic");
        com.google.firebase.database.Query query = re.orderByChild("status").equalTo("pending");
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren())
                    {

                        list.add(snapshot1.getValue(Clinic.class));


                    }
                    pd.dismiss();

                    create_adapter();
                }



                else {
                    Toast.makeText(getApplication(), "Empty list ", Toast.LENGTH_SHORT).show();
                    try {
                        modelLists.clear();
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







}

