package com.example.s3adoon.gp_v;

import android.app.Activity;
 import android.content.DialogInterface;
 import android.content.SharedPreferences; import
         android.content.SharedPreferences.Editor;
import android.view.View;

public class Jfp1 extends Activity implements View.OnClickListener {

         SharedPreferences prefs;
         SharedPreferences.Editor editor;
         @Override
         protected void onPause() {
             super.onPause();

       SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
       Editor editor = prefs.edit();
       editor.putString("lastActivity", getClass().getName());
       editor.commit();
         }




    @Override
    public void onClick(View v) {

    }
}