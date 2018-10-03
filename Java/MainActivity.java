package com.example.rashidalikhan.firebasedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity  extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;
    ProgressBar pg;
    CoordinatorLayout coordinatorLayout;
    private static MainActivity instance = new MainActivity();
    static Context context;
    ConnectivityManager connectivityManager;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static MainActivity getInstance(Context ctx)
    {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e)
        {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return connected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg=(ProgressBar)findViewById(R.id.pro);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);


//        Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        new BackgroundTask().execute();
    }


    private class BackgroundTask extends AsyncTask {
        Intent intent;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            intent = new Intent(MainActivity.this, Login.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent

//            intent.putExtra("data_key", "");
            if (MainActivity.getInstance(getApplicationContext()).isOnline()) {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),"You Are Online", Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.GREEN);
                snackbar.show();
                if (user != null)
                {
                    Intent i=new Intent(MainActivity.this,Home.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    startActivity(intent);
                    finish();
                }



            }
            else
                {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),"No Internet Connection !", Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.RED);
                snackbar.show();
                finish();
            }


        }
    }
}