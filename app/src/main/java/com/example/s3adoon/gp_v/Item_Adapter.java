package com.example.s3adoon.gp_v;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.ViewHolder>

{
    private Context mcontext;
    ArrayList<ModelList> mlist;
    SwipeRefreshLayout swiper;


    Item_Adapter(Context context, ArrayList<ModelList> list, SwipeRefreshLayout swiper ) {
        mcontext = context;
        this.swiper=swiper;
        mlist = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final ModelList modelList = mlist.get(position);
   //     ImageView image = holder.imageView;

        final TextView clinicname = holder.clcname, showmore = holder.showmore, docemail = holder.docemail;


        clinicname.setText(modelList.getClcname());
        docemail.setText(modelList.getDocemail());
        showmore.setText(modelList.getShowmore());
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = modelList.getDocemail();
                String name = modelList.getClcname();
                String spec = modelList.getSpec();

                Intent in = new Intent(mcontext, Showmore.class);
                in.putExtra("email", email);
                in.putExtra("name", name);
                in.putExtra("spec", spec);

                mcontext.startActivity(in);



                ((Activity)mcontext).finish();



            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                ((Activity)mcontext).startActivity(new Intent(((Activity)mcontext),Admin_Page.class));
                ((Activity)mcontext).startActivity(new Intent(((Activity)mcontext),Verify.class));


                ((Activity)mcontext).finish();
            }
        });

    }


        private void refresh ()
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Item_Adapter.this.notifyDataSetChanged();


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

            //نوووش

      //      imageView = itemView.findViewById(R.id.img);
            docemail = itemView.findViewById(R.id.docemail);

            showmore = itemView.findViewById(R.id.showmore);
            clcname = itemView.findViewById(R.id.clcname);

        }
    }

}






