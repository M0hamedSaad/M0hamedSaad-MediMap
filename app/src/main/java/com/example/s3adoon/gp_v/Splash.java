package com.example.s3adoon.gp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Splash   extends AppCompatActivity {

        private final int SPLASH_DISPLAY_LENGTH = 3000;
        private ImageView image ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.splash);
            image = (ImageView) findViewById(R.id.image);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytranstion);
            image.startAnimation(animation);
            final Intent in = new Intent(Splash.this, MainActivity.class);
            Thread timer = new Thread()
            {
                public void run()
                {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(in);
                        finish();
                    }
                }
            };
            timer.start();
        }
}