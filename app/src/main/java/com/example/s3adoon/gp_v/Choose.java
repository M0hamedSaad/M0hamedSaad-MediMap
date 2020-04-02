package com.example.s3adoon.gp_v;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Choose extends AppCompatActivity  implements View.OnClickListener
{

    private LinearLayout imageuser, imagedoc;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.choose);

        imageuser=findViewById(R.id.imageuser);
        imagedoc=findViewById(R.id.imagedoc);

        imageuser.setOnClickListener(this);
        imagedoc.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == imageuser)
        {
            startActivity(new Intent(this,Sign_Up.class));
        }
        else if (view == imagedoc)
        {
            startActivity(new Intent(this,Sign_Up_doc.class));
        }
    }
}


// https://www.codeproject.com/Articles/1121102/Google-Maps-Search-Nearby-Displaying-Nearby-Places