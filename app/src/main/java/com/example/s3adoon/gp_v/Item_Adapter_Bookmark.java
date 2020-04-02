package com.example.s3adoon.gp_v;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Item_Adapter_Bookmark extends RecyclerView.Adapter<Item_Adapter_Bookmark.ViewHolder>

{
    private Context mcontext;
    ArrayList<ModelList> mlist;
    SwipeRefreshLayout swiper;
    User_Bockmark f = new User_Bockmark();

    Item_Adapter_Bookmark(Context context, ArrayList<ModelList> list, SwipeRefreshLayout swiper ) {
        mcontext = context;
        this.swiper=swiper;
        mlist = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.list_item_bookmark, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final ModelList modelList = mlist.get(position);


        final TextView clinicname = holder.clcname, showmore = holder.showmore;

        clinicname.setText(modelList.getClcname());

        showmore.setText(modelList.getShowmore());
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Delete_Unit(modelList.getClcname());


            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();

                FragmentTransaction transaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frag,f);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.commit();

            }
        });

    }

        private void refresh ()
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Item_Adapter_Bookmark.this.notifyDataSetChanged();


                    swiper.setRefreshing(false);

                }
            }, 3000);
        }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView showmore, clcname, docemail;

        public ViewHolder(View itemView) {
            super(itemView);

            showmore = itemView.findViewById(R.id.showmore);
            clcname = itemView.findViewById(R.id.clcname);

        }
    }




    private void Delete_Unit( String x )
    {

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Bookmark");
        com.google.firebase.database.Query query = re.orderByChild("clcname").equalTo(x);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                        snapshot1.getRef().removeValue();


                    }
                    Toast.makeText(mcontext, "Done", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }



}






