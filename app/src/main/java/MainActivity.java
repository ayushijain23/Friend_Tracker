package com.example.acer.friendstracker;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText email, fname, lname, mobileno, pass, cpass;
    TextView msg;
    Button B;
    ImageView Im;
    ImageButton B2;
    File destination;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        B = (Button) findViewById(R.id.btn_submit);
        Im = (ImageView) findViewById(R.id.userPic);
        B2 = (ImageButton) findViewById(R.id.btnPic);
        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        mobileno = (EditText) findViewById(R.id.mobile);
        pass = (EditText) findViewById(R.id.pwd);
        cpass = (EditText) findViewById(R.id.cpwd);
        msg = (TextView) findViewById(R.id.msg);
        B.setOnClickListener(new Btn_Click(this));
        B2.setOnClickListener(new Btn_Click(this));

    }



    class Btn_Click implements View.OnClickListener {
        Context ctx;

        Btn_Click(Context ctx) {
            this.ctx = ctx;

        }

        @Override
        public void onClick(View v) {
            if (v == B) {
               String qs = "http://" + ctx.getString(R.string.ip) + ":8080/FriendTracker/registrationsubmit?emailid=" + email.getText() + "&firstname=" + fname.getText() + "&lastname=" + lname.getText() + "&mobileno=" + mobileno.getText() + "&password=" + pass.getText() + "&picture="+(mobileno.getText()+".jpg");
            //   Toast.makeText(ctx,qs,Toast.LENGTH_LONG).show();

               String url[] = {qs};
                GrantPermission g = new GrantPermission(ctx, Manifest.permission.INTERNET, 1);
                CallHttpRequest req = new CallHttpRequest(ctx, msg, "Registration");


                req.execute(url);//call doInBackground

             String qsf="http://"+ ctx.getString(R.string.ip)+":8080/FriendTracker/picupload";
              // Toast.makeText(ctx,"jain",Toast.LENGTH_SHORT).show();
                String urlf[]={qsf};
                 destination = new File(Environment.getExternalStorageDirectory(),
                        mobileno.getText() + ".jpg");

                CallHttpRequest reqf=new CallHttpRequest(ctx,destination,"FileUpload");
                reqf.execute(urlf);



            } else if (v == B2) {
                try {
                    cameraIntent();
                } catch (Exception e) {
                    GrantPermission g = new GrantPermission(ctx, Manifest.permission.CAMERA, 2);
                }
            }
        }

    }


    private void cameraIntent() {
        GrantPermission g = new GrantPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0)
            saveImage(data);
    }

    private void saveImage(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                mobileno.getText() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Im.setImageBitmap(thumbnail);
    }

}


