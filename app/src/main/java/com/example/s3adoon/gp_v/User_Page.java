package com.example.s3adoon.gp_v;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class User_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private LinearLayout userLinear;
    private ImageView add;
    BlankFragment f = new BlankFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        Firebase.setAndroidContext(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frag, f);
        tx.commit();



        setupToolbarMenu();
        setupNavigationDrawerMenu();
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
        mToolbar.setTitle("Welcome User ...");
    }


    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drower);
        userLinear = findViewById(R.id.Lineartest);
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
                Class fragmentclass1 = null;

                switch (item.getItemId()) {




                    case R.id.itm_Home:

                        fragmentclass1 = BlankFragment.class;
                        userLinear.setVisibility(View.INVISIBLE);


                        break;



                    case R.id.itm_Bookmark:

                    fragmentclass1 = User_Bockmark.class;
                    userLinear.setVisibility(View.INVISIBLE);

                    break;



                    default:
                        fragmentclass1 = User_Home.class;
                        userLinear.setVisibility(View.INVISIBLE);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {

        menuItem.setCheckable(true);
        menuItem.setChecked(true);  // This helps to know which Menu Item is Clicked

        Fragment fragment2 = null;
        Class fragmentclass = null;

        closeDrawer();
        switch (menuItem.getItemId())
        {


            case R.id.itm_about:
                fragmentclass = About_Us.class;
                userLinear.setVisibility(View.INVISIBLE);

                break;

            case R.id.itm_setting:
                fragmentclass = SettingUser.class;
                userLinear.setVisibility(View.INVISIBLE);
                break;


            case R.id.itm_contact:
                fragmentclass = Contact_Us_Doctor.class;
                userLinear.setVisibility(View.INVISIBLE);
                break;
            case R.id.itm_feedback:
                fragmentclass = App_Feedback.class;
                userLinear.setVisibility(View.INVISIBLE);
                break;


            case R.id.itm_Guidelines:
                fragmentclass = Guidlines.class;
                userLinear.setVisibility(View.INVISIBLE);
                break;

            case R.id.itm_logout:
                fragmentclass = Logout.class;
                userLinear.setVisibility(View.INVISIBLE);
                break;
            default:
                userLinear.setVisibility(View.INVISIBLE);
                fragmentclass = Logout.class;

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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
     /*   if(v == add)
        {

            Fragment fragment2 = null;
            Class fragmentclass = null;
            fragmentclass = Add_Clinic.class;
            userLinear.setVisibility(View.INVISIBLE);

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
}


