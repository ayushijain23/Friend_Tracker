package com.example.acer.friendstracker;

import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;

import java.util.ArrayList;
import java.util.List;

public class searchbydateandtime extends Fragment {


    EditText mobileno,date,timeone,timetwo;
    CustomAdapter adp;
    List<Trackings>LT=new ArrayList<Trackings>();
    Button B;
    ListView l;
    Context ctx;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = getActivity();

        View rootView = inflater.inflate(R.layout.trackbydaydate, container, false);
        LocationManager LOC = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        mobileno = (EditText) rootView.findViewById(R.id.mono);
        date = (EditText) rootView.findViewById(R.id.editdate);
        timeone = (EditText) rootView.findViewById(R.id.edittime1);
        timetwo = (EditText) rootView.findViewById(R.id.edittime2);
        l = (ListView) rootView.findViewById(R.id.list);
        B = (Button) rootView.findViewById(R.id.btnsearch);
        B.setOnClickListener(new click(getActivity()));
        adp = new CustomAdapter(ctx, android.R.layout.simple_list_item_1, LT);
        l.setAdapter(adp);
        return rootView;
    }
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackbydaydate);
        mobileno=(EditText) findViewById(R.id.mono);
        date=(EditText) findViewById(R.id.editdate);
        timeone=(EditText) findViewById(R.id.edittime1);
        timetwo=(EditText) findViewById(R.id.edittime2);
        l=(ListView)findViewById(R.id.list);
        B=(Button)findViewById(R.id.btnsearch);
        B.setOnClickListener(new click(this));
        adp=new CustomAdapter(this,android.R.layout.simple_list_item_1,LT);
        l.setAdapter(adp);

    } */
    class click implements android.view.View.OnClickListener
    {
        Context ctx;

        public click(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void onClick(View v) {
            String q="http://"+ctx.getString(R.string.ip)+":8080/FriendTracker/searchbydateandtime?MOBILENO="+mobileno.getText()+"&DATE="+date.getText()+"&TIMEONE="+timeone.getText()+"&TIMETWO="+timetwo.getText();
            String url[]={q};

            CallHttpRequest C=new CallHttpRequest(ctx,adp,LT,"searchbydateandtime");
            C.execute(url);
        }
    }
}