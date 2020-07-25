package com.example.acer.friendstracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class Searchbymobile extends Fragment {
    ListView L;
    EditText mobileno;
    ImageButton B, B1;
    ImageView im;
    CustomAdapter adp;
    List<Trackings> LT = new ArrayList<Trackings>();
    String number;
    Context ctx;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = getActivity();

        View rootView = inflater.inflate(R.layout.tracklist, container, false);
        LocationManager LOC = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
       L = (ListView) rootView.findViewById(R.id.listview);
        L.setOnItemClickListener(new listclick());
        im = (ImageView)rootView.findViewById(R.id.fdpic);
        B = (ImageButton) rootView.findViewById(R.id.btnsearch);
        B1 = (ImageButton)rootView.findViewById(R.id.btncontact);
       B1.setOnClickListener(new btn1_click(getActivity()));

        mobileno = (EditText)rootView.findViewById(R.id.mobilenoid);
        B.setOnClickListener(new btn_click(getActivity()));
        adp = new CustomAdapter(ctx, android.R.layout.simple_list_item_1, LT);
        L.setAdapter(adp);
        return rootView;
    }

   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracklist);
        L = (ListView) findViewById(R.id.listview);
        L.setOnItemClickListener(new listclick());
        im=(ImageView)findViewById(R.id.fdpic);
        B = (ImageButton) findViewById(R.id.btnsearch);
        B1 = (ImageButton) findViewById(R.id.btncontact);
        B1.setOnClickListener(new btn1_click(this));

        mobileno = (EditText) findViewById(R.id.mobilenoid);
        B.setOnClickListener(new btn_click(this));
        adp = new CustomAdapter(this, android.R.layout.simple_list_item_1, LT);
        L.setAdapter(adp);
    } */
  class btn1_click implements android.view.View.OnClickListener {

       Context ctx;

       public btn1_click(Context ctx) {
           this.ctx = ctx;
       }

       public void onClick(View arg0) {
           // TODO Auto-generated method stub


           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
           startActivityForResult(intent, 1);
       }
   }

    class btn_click implements android.view.View.OnClickListener {
        Context ctx;

        public btn_click(Context ctx) {
            this.ctx = ctx;
        }

        @Override

        public void onClick(View v) {
            String q = "http://" + ctx.getString(R.string.ip) + ":8080/FriendTracker/searchbymobilenoJSON?MOBILENO=" + mobileno.getText().toString();
            String url[] = {q};

            CallHttpRequest C = new CallHttpRequest(ctx, adp, LT, "SearchMobile");
            C.execute(url);
            String qm="http://" + ctx.getString(R.string.ip) +":8080/FriendTracker/Picture/"+mobileno.getText()+".jpg";
            String urlm[]={qm};
            CallHttpRequest CC=new CallHttpRequest(ctx,im,"SearchPic");
            CC.execute(urlm);


        }


    }

    class listclick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Trackings T = LT.get(position);
            try {
                displayMap(Double.parseDouble(T.getLatitude()), Double.parseDouble(T.getLongitude()));
            } catch (Exception e) {
            }

        }
    }


    private void displayMap(double lat, double lng) throws Exception {
        String uriString = "geo:" + lat + "," + lng;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uriString));
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri contactData = data.getData();
                String[]p={ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getActivity().getContentResolver().query(contactData,p,null,null,null);

                cursor.moveToFirst();

                number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
               mobileno.setText(number);
                //contactName.setText(name);

                //contactEmail.setText(email);
            }
        }

    }
}
