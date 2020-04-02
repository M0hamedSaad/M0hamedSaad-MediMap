package com.example.s3adoon.gp_v;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Item_Adapter_Doctor extends RecyclerView.Adapter<Item_Adapter_Doctor.ViewHolder>

{
    private Context mcontext;
    ArrayList<ModelList> mlist;
  //  SwipeRefreshLayout swiper;


    Item_Adapter_Doctor(Context context, ArrayList<ModelList> list, SwipeRefreshLayout swiper ) {
        mcontext = context;
//        this.swiper=swiper;
        mlist = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.list_item_doctor, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final ModelList modelList = mlist.get(position);
        ImageView image = holder.imageView;

        final TextView clinicname = holder.clcname, showmore = holder.showmore, docemail = holder.docemail;


        clinicname.setText(modelList.getClcname());
        docemail.setText(modelList.getDocemail());
        showmore.setText(modelList.getShowmore());
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = modelList.getClcname();
                Intent in = new Intent(mcontext, Showmore_Doctor.class);
                in.putExtra("name", name);
                mcontext.startActivity(in);
                //((Activity)mcontext).finish();





            }
        });

    /*    swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
     //           refresh();
            //    ((Activity)mcontext).startActivity(new Intent(((Activity)mcontext),Doctor_Page.class));
             //  ((Activity)mcontext).startActivity(new Intent(((Activity)mcontext),Doctor_Home.class));
         //      ((Activity)mcontext).startActivity(new Intent(((Activity)mcontext),Doctor_Home.class));

               // ((Activity)mcontext).finish();




            }
        });
*/
    }

/*
        private void refresh ()
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Item_Adapter_Doctor.this.notifyDataSetChanged();


                    swiper.setRefreshing(false);

                }
            }, 3000);
        }


*/


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

          //  imageView = itemView.findViewById(R.id.img);
            docemail = itemView.findViewById(R.id.docemail);

            showmore = itemView.findViewById(R.id.showmore);
            clcname = itemView.findViewById(R.id.clcname);

        }
    }

}






