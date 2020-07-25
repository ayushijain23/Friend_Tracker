package com.example.acer.friendstracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class loginactivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private View mLoginFormView;
    Button B1,B2;
    TextView T;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        T= (TextView) findViewById(R.id.Lmsg);



        B1 = (Button) findViewById(R.id.email_sign_in_button);
        B1.setOnClickListener(new Btn_Click(this));

        B2 = (Button) findViewById(R.id.button_sign_up);
        B2.setOnClickListener(new Btn_Click(this));
        mLoginFormView = findViewById(R.id.login_form);

    }

    class Btn_Click implements android.view.View.OnClickListener
    {Context ctx;
        Btn_Click(Context ctx)
        {this.ctx=ctx;

        }
        @Override
        public void onClick(View v) {
            if(v==B1){
                //  Intent I=new Intent(ctx,MainActivity.class);
                // ctx.startActivity(I);

              CallHttpRequest C=new CallHttpRequest(ctx,T,"CheckLogin");
                String qs="http://"+ctx.getString(R.string.ip)+":8080/FriendTracker/Checklogin?userid="+mEmailView.getText()+"&userpassword="+ mPasswordView.getText();
                String url[]={qs};
               C.execute(url);
            }

            else if(v==B2)
            {Intent I=new Intent(ctx,MainActivity.class);
                ctx.startActivity(I);

            }
        }
    }


}

