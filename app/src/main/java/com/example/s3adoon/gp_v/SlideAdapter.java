package com.example.s3adoon.gp_v;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images
    public int[] lst_images = {
            R.drawable.index1,
            R.drawable.doctor,
            R.drawable.admoon,
            R.drawable.index1
    };
    // list of titles
    public String[] lst_title = {
            "USER",
            "DOCTOR",
            "ADMIN",
            "USER"
    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Represents any user will use the Application in order to search for any Medical Units .",
            "Represents the Medical units will be added manually by owners to the system after being checked and approved by Admin",
            "The central Controlling object in the Medical circuit system .. Admin control all other objects in the system",
            "Represents any user will use the Application in order to search for any Medical Units ."
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            Color.rgb(47,51,63),
            Color.rgb(21,189,178),
            Color.rgb(102,102,102),
            Color.rgb(1,188,212),


    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView)  view.findViewById(R.id.slideimg);
        TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
