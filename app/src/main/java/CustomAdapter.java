package com.example.acer.friendstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ayushi on 7/13/2017.
 */
public class CustomAdapter extends ArrayAdapter<Trackings> {

    Context c;

    public CustomAdapter(Context context, int resource, List<Trackings> objects) {
        super(context, resource, objects);
        c=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.trackview, parent, false);
        Trackings T=getItem(position);
        TextView date=(TextView)convertView.findViewById(R.id.txtdate);
        TextView address=(TextView)convertView.findViewById(R.id.txtaddress);
        TextView latitude=(TextView)convertView.findViewById(R.id.txtlat);
        date.setText(T.getTracking_date()+" "+T.getTracking_time());
        address.setText(T.getAddress());
        latitude.setText(T.getLatitude()+","+T.getLongitude());
        return convertView;
    }
}
