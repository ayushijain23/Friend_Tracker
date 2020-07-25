package com.example.acer.friendstracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Tracking extends Fragment {
    String mb = "";
    TextView T;
    Context ctx;
    SharedPreferences pos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = getActivity();

        View rootView = inflater.inflate(R.layout.activity_tracking, container, false);
        LocationManager LOC = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        T = (TextView) rootView.findViewById(R.id.trkMsg);
        Intent I = getActivity().getIntent();
      //  Bundle B = I.getExtras();
     //   mb = B.getString("mobileno");
      pos=ctx.getSharedPreferences("myprofile",0);
       mb=pos.getString("mobileno","");
        try {
            GrantPermission G3 = new GrantPermission(ctx, Manifest.permission.INTERNET, 5);
            GrantPermission G2 = new GrantPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION, 4);
            GrantPermission G1 = new GrantPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION, 3);
            GrantPermission G4 = new GrantPermission(ctx, Manifest.permission.CALL_PHONE, 2);

            LOC.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new Loc_Update());
        } catch (Exception e) {
            Toast.makeText(ctx, e + "", Toast.LENGTH_LONG).show();
            GrantPermission G1 = new GrantPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION, 3);
            GrantPermission G2 = new GrantPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION, 4);
            GrantPermission G4 = new GrantPermission(ctx, Manifest.permission.CALL_PHONE, 2);
        }


    return rootView;
}

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tracking);
    LocationManager LOC=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    T=(TextView)findViewById(R.id.trkMsg);
    Intent I=getIntent();
    Bundle B=I.getExtras();
    mb=B.getString("mobileno");
    try {GrantPermission G3=new GrantPermission(this, Manifest.permission.INTERNET,5);
        GrantPermission G2=new GrantPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,4);
        GrantPermission G1=new GrantPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION,3);
        GrantPermission G4=new GrantPermission(this, Manifest.permission.CALL_PHONE,2);

        LOC.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new Loc_Update());
    }
    catch (Exception e)
    {  Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
        GrantPermission G1=new GrantPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION,3);
        GrantPermission G2=new GrantPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,4);
        GrantPermission G4=new GrantPermission(this, Manifest.permission.CALL_PHONE,2);
    }
} */


    class Loc_Update implements LocationListener {

        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            T.setText(lat + "," + lon);
            getaddress(lat, lon);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }
    }

    void getaddress(double lat, double lon) {
        try {
            Geocoder GC = new Geocoder(ctx, Locale.getDefault());
            List<Address> A = GC.getFromLocation(lat, lon, 1);
            StringBuffer sb = new StringBuffer();
            if(A.size()>0) {
                Address add = A.get(0);


                for (int i = 0; i < add.getMaxAddressLineIndex(); i++) {
                    sb.append(add.getAddressLine(i) + " ");
                }
                String addr = sb.toString().replace(" ", "+");
                Calendar C = Calendar.getInstance();
                String cd = C.get(Calendar.YEAR) + "-" + (C.get(Calendar.MONTH) + 1) + "-" + C.get(Calendar.DATE);
                String ct = C.get(Calendar.HOUR) + ":" + C.get(Calendar.MINUTE) + ":" + C.get(Calendar.SECOND);
                String q = "http://"+this.getString(R.string.ip)+":8080/FriendTracker/ayutracking?mobileno="+mb+"&latitude=" + lat + "&longitude=" + lon + "&cd=" + cd + "&ct=" + ct + "&address=" + addr;
                T.setText(q);
                CallHttpRequest tr = new CallHttpRequest(ctx, "Tracking");
                String url[] = {q};
                tr.execute(url);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, e + "", Toast.LENGTH_LONG).show();
        }

    }
}