package com.example.rashidalikhan.firebasedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    TextView Wel;
    AlertDialog.Builder  builder;
    FirebaseUser user;
    int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Wel=(TextView)findViewById(R.id.WelcomeMessage);
        user= FirebaseAuth.getInstance().getCurrentUser();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.MyWelcomelayout), "Login Successfully...", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.GREEN);
        snackbar.show();

        if(user!=null)
        {
            String Email=user.getEmail();

            Wel.setText(Email);
        }

        builder=new AlertDialog.Builder(this);
                builder.setTitle("Sign Out Confirmation");
                builder.setMessage("Are You Sure You Want to Sign Out !");
                builder.setIcon(R.drawable.lpu);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        FirebaseAuth.getInstance().signOut();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.MyWelcomelayout),"Sign Out Successfully...", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.GREEN);
                        snackbar.show();
                        Intent i=new Intent(Welcome.this,Login.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

    }

    public void Cancel()
    {

    }

    @Override
    public void onBackPressed()
    {
        k++;
        if(k == 1)
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.MyWelcomelayout),"Press Onces Again to Exit !", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
        else
        {

            finish();
            System.exit(0);
            moveTaskToBack(false);
        }


    }
    public void SignOut(View v)
    {
        CallCheck();

    }
    public void CallCheck()
    {
        builder.create();
        builder.setCancelable(false);
        builder.show();
    }
}
