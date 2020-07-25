package com.example.acer.friendstracker;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 7/6/2017.
 */
public class CallHttpRequest extends AsyncTask<String,Void,String> {
    Context ctx;
    TextView T;
    Bitmap bit;
    ImageView im;
    File fileUpload;
    String activity;
    List<Trackings> LT = new ArrayList<Trackings>();
    CustomAdapter adp;


    CallHttpRequest(Context ctx, TextView T, String activity) {
        this.ctx = ctx;
        this.T = T;
        this.activity = activity;

    }
    CallHttpRequest(Context ctx, ImageView im , String activity) {
        this.ctx = ctx;
        this.im=im;
        this.activity = activity;

    }

    CallHttpRequest(Context ctx, String activity) {
        this.ctx = ctx;

        this.activity = activity;

    }

    CallHttpRequest(Context ctx, File fileUpload, String activity) {
        this.ctx = ctx;
        this.fileUpload = fileUpload;
        this.activity = activity;

    }

    CallHttpRequest(Context ctx, CustomAdapter adp, List<Trackings> LT, String activity) {
        this.ctx = ctx;
        this.LT = LT;
        this.adp = adp;
        this.activity = activity;

    }

    @Override
    protected String doInBackground(String... url) {
        if (activity.equals("FileUpload")) {
            String res = fileUpload(url[0], fileUpload);
            return res;
        }
        else if (activity.equals("SearchPic"))
        {
           fetchPicture(url[0]);
            return "PicLoaded";
        }
        else
         {
            String res = callRequest(url[0]);
            return res;
        }
    }


    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (activity.equals("Registration")) {
            if (s.equals("1"))
                T.setText("Record Submitted...");
            else
                T.setText("Fail to Submit Record....");
        } else if (activity.equals("CheckLogin")) {
            //  if (s.equals("0")) {
            //  T.setText("Invalid UserID/Password");
            //  } else
            //    if (s.equals("-1")) {
            //          T.setText("Error On Server...");
            //     } else {

           GrantPermission Loc1 = new GrantPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE, 3);
             SharedPreferences pos;
                String fileName = "mystrings";
                pos = ctx.getSharedPreferences(fileName, 0);
                SharedPreferences.Editor editor = pos.edit();

               try {
                   JSONArray records = new JSONArray(s);
                   JSONObject rec = records.optJSONObject(0);
                   editor.putString("MobileNo", rec.optString("MOBILNO"));
                   editor.putString("Fisrtname", rec.optString("FIRSTNAME"));
                   editor.putString("Lastname", rec.optString("LASTNAME"));
                   editor.putString("emailid", rec.optString("EMAILID"));
                   editor.commit();

                   Intent I = new Intent(ctx, MainMenu.class);
                   //       I.putExtra("mobileno",rec.optString("MOBILNO"));
                   //Intent I = new Intent(ctx, MainActivity.class);
                   ctx.startActivity(I);
               }
                   catch(Exception e){
                       Toast.makeText(ctx, e + "", Toast.LENGTH_LONG);
                   }
               }

        else if(activity.equals("SearchPic")) {
            im.setImageBitmap(bit);
        }
        else if (activity.equals("SearchMobile")) {
            try {
                LT.clear();
               JSONArray records = new JSONArray(s);

             for (int i = 0; i < records.length(); i++) {
                    JSONObject rec = records.optJSONObject(i);
                    Trackings T = new Trackings();
                    T.setTrackingId(Integer.parseInt(rec.optString("ID")));
                    T.setMobileno(rec.optString("MOBILENO"));
                    T.setLatitude(rec.optString("LATITUDE"));
                    T.setLongitude(rec.optString("LONGITUDE"));
                    T.setTracking_time(rec.optString("TRACKING_TIME"));
                    T.setTracking_date(rec.optString("TRACKING_DATE"));
                    T.setAddress(rec.optString("ADDRESS"));
                    LT.add(T);


                }
                adp.notifyDataSetChanged();


            } catch (Exception e) {

Toast.makeText(ctx,e+"",Toast.LENGTH_LONG);
            }
        }
        else if (activity.equals("searchbydateandtime")) {
            try {
                LT.clear();
                JSONArray records = new JSONArray(s);
               // Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
                for (int i = 0; i < records.length(); i++) {
                    JSONObject rec = records.optJSONObject(i);
                    Trackings T = new Trackings();
                    T.setTrackingId(Integer.parseInt(rec.optString("ID")));
                    T.setMobileno(rec.optString("MOBILENO"));
                    T.setLatitude(rec.optString("LATITUDE"));
                    T.setLongitude(rec.optString("LONGITUDE"));
                    T.setTracking_time(rec.optString("TRACKING_TIME"));
                    T.setTracking_date(rec.optString("TRACKING_DATE"));
                    T.setAddress(rec.optString("ADDRESS"));
                    LT.add(T);


                }
                adp.notifyDataSetChanged();


            } catch (Exception e) {

                Toast.makeText(ctx,e+"",Toast.LENGTH_LONG);
            }
        }

    }



    String callRequest(String Url) {
        try {
            //Toast.makeText(ctx,Url,Toast.LENGTH_LONG).show();
            URL url = new URL(Url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            DataInputStream in = new DataInputStream(con.getInputStream());

            StringBuffer output = new StringBuffer();
            String str;
            while ((str = in.readLine()) != null) {
                output.append(str);
            }
            in.close();
            return (output.toString());


        } catch (Exception e) {
             Toast.makeText(ctx, "Error:" + e, Toast.LENGTH_SHORT).show();
        }
        return (null);
    }

    String fileUpload(String requestURL,File uploadFile)
    {

        String charset = "UTF-8";
        //  File uploadFile1 = new File("/sdcard/+917415804138112441_2016-7-9.3gp");



        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);



            multipart.addFilePart("file", uploadFile);


            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
            //  Toast.makeText(ctx,"upupupup",Toast.LENGTH_SHORT).show();
            //  uploadFile.delete();
            return("Uploaded");
        } catch (IOException ex) {
            //Toast.makeText(ctx,"xxxxxxxxxxxxxxxxxxxxxx",Toast.LENGTH_SHORT).show();
            return ("Fail");

        }


    }

    void fetchPicture(String Url)
    {
        try {


            URL imageURL=new URL(Url);
            HttpURLConnection connection= (HttpURLConnection)imageURL.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            bit = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e) {
Toast.makeText(ctx,"ayu",Toast.LENGTH_LONG);
            e.printStackTrace();
        }



    }

}