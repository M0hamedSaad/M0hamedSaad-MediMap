package com.example.s3adoon.gp_v;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Doctor_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private LinearLayout Lineartest;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri pathurl;
    Firebase mRef;
    FirebaseAuth auth;
    String UID;
    private final int PICK_IMAGE_REQUEST = 71;
    Fragment f = new Doctor_Home();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_page);
        Firebase.setAndroidContext(this);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        tx.replace(R.id.frag, f);
        tx.commit();
        auth = FirebaseAuth.getInstance();
        //session();
        setupToolbarMenu();
        setupNavigationDrawerMenu();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        UID = auth.getCurrentUser().getEmail();
        //  camera.setOnClickListener(this );

    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }


    private void setupToolbarMenu() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Welcome Doctor ...");
    }

    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drower);
        Lineartest = findViewById(R.id.Lineartest);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drower_open,
                R.string.drower_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment1 = null;
                Class fragmentclass1;

                switch (item.getItemId()) {


                    case R.id.itm_Home:
                        //       Lineartest.setVisibility(View.INVISIBLE);

                        fragmentclass1 = Doctor_Home.class;

                        break;


                    case R.id.itm_add_clinic:

                        Lineartest.setVisibility(View.INVISIBLE);
                        fragmentclass1 = Add_Clinic.class;

                        break;


                    case R.id.itm_feedback:
                        Lineartest.setVisibility(View.INVISIBLE);

                        fragmentclass1 = Doctor_Feedback.class;

                        break;


                    default:
                        Lineartest.setVisibility(View.INVISIBLE);
                        fragmentclass1 = Add_Clinic.class;

                }

                try {
                    fragment1 = (Fragment) fragmentclass1.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.frag, fragment1).commit();
                item.setChecked(true);
                setTitle(item.getTitle());
                return true;
            }

        });


    }

    @Override // Called when Any Navigation Item is Clicked
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setCheckable(true);
        menuItem.setChecked(true);  // This helps to know which Menu Item is Clicked

        Fragment fragment2 = null;
        Class fragmentclass = null;

        closeDrawer();
        switch (menuItem.getItemId()) {


            case R.id.itm_about:
                fragmentclass = About_Us.class;
                Lineartest.setVisibility(View.INVISIBLE);

                break;

            case R.id.itm_setting:
                fragmentclass = Setting.class;
                Lineartest.setVisibility(View.INVISIBLE);
                break;


            case R.id.itm_contact:
                fragmentclass = Contact_Us_Doctor.class;
                Lineartest.setVisibility(View.INVISIBLE);

                break;
            case R.id.itm_feedback:
                fragmentclass = App_Feedback.class;
                Lineartest.setVisibility(View.INVISIBLE);
                break;

            case R.id.itm_Guidelines:
                fragmentclass = Guidlines.class;
                Lineartest.setVisibility(View.INVISIBLE);
                break;

            case R.id.itm_logout:
                fragmentclass = Logout.class;
                Lineartest.setVisibility(View.INVISIBLE);
                break;
            default:

                fragmentclass = Add_Clinic.class;
                Lineartest.setVisibility(View.INVISIBLE);
        }

        try {
            fragment2 = (Fragment) fragmentclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frag, fragment2).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        return true;

    }

    // Close the Drawer
    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // Open the Drawer
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
            finishAffinity();
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }

  /*      if(view == add)
        {

            Fragment fragment2 = null;
            Class fragmentclass = null;
            fragmentclass = Add_Clinic.class;
            Lineartest.setVisibility(View.INVISIBLE);

            try {
                fragment2 = (Fragment) fragmentclass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frag, fragment2).commit();
        }
    */

    }

