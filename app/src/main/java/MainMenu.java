package com.example.acer.friendstracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imv;
     TextView t1, t2;
    Context ctx;
    SharedPreferences pos;
    String fileName = "mystrings";
     String firstname;
     String lastname;
     String emailid;
     String mb;

    // private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nv = navigationView.inflateHeaderView(R.layout.nav_header_main_menu);
        // imageButton=(ImageButton) nv.findViewById(R.id.fetchedimage);

        // imageButton.setOnClickListener(new Image_Click(this));
        // Intent I=this.getIntent();
       /*  Bundle B=I.getExtras();
        String mb=B.getString("mobileno");
        */
        imv = (ImageView) nv.findViewById(R.id.fetchedimage);
        pos = getSharedPreferences(fileName, 0);
        firstname = pos.getString("Firstname", "");
        lastname = pos.getString("Lastname", "");
        emailid = pos.getString("emailid", "");
        mb = pos.getString("MobileNo", "");
        //  password=pos.getString("password","");


        String qm = "http://" + this.getString(R.string.ip) + ":8080/FriendTracker/Picture/" + mb + ".jpg";
        String urlm[] = {qm};

        CallHttpRequest C1 = new CallHttpRequest(this, imv, "SearchPic");
        C1.execute(urlm);


        t1 = (TextView) nv.findViewById(R.id.fetchedusername);
        t2 = (TextView) nv.findViewById(R.id.fetchedemailid);
        t1.setText(firstname + " " + lastname);
        t2.setText(emailid);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment F = null;
        int id = item.getItemId();
        if (id == R.id.nv) {
            F = new Tracking();

        } else if (id == R.id.nav_search) {
            F = new Searchbymobile();
        } else if (id == R.id.nav_searchbytime) {
            F = new searchbydateandtime();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        if (F != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_menu, F);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}