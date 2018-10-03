package com.example.rashidalikhan.firebasedemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser user;
    AlertDialog.Builder  builder;
    AlertDialog.Builder builder2;
    ProgressDialog progressDialog;
    TextInputLayout name,course,branch,phone,att;
    GridView gridView;
    MySqlLiteHelper  mySqlLiteHelper;

    public static String[] Options = {
            "Add Student",
            "View Students",
            "Make Outbound Call",
            "Call History"


    };
    public static int[] OptionsImages= {
            R.drawable.add,
            R.drawable.view,
            R.drawable.call,
            R.drawable.history

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.grid);
        builder2 = new AlertDialog.Builder(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Adding Student Data...");
        progressDialog.setIcon(R.drawable.lpu);
        mySqlLiteHelper=new MySqlLiteHelper(this);

        gridView.setAdapter(new CustomAndroidGridViewAdapter(this,Options,OptionsImages));
        builder2.setTitle("Add Student");
        final View v = LayoutInflater.from(this).inflate(R.layout.customform,null,false);
        builder2.setView(v);
        builder2.setPositiveButton("Save Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                 name=(TextInputLayout) v.findViewById(R.id.NameInput);
                 course=(TextInputLayout) v.findViewById(R.id.CourseInput);
                 branch=(TextInputLayout) v.findViewById(R.id.BranchInput);
                 phone=(TextInputLayout) v.findViewById(R.id.PhoneInput);
                 att=(TextInputLayout) v.findViewById(R.id.AttInput);

                final String Name,Course,Branch,Phone,Att;
                Name=name.getEditText().getText().toString();
                Course=course.getEditText().getText().toString();
                Branch=branch.getEditText().getText().toString();
                Phone=phone.getEditText().getText().toString();
                 Att=att.getEditText().getText().toString();
               //String att=Integer.toString(Att);
                if (!validate(Name,Course,Branch,Phone,Att))
                {
                    onSaveDataFailed();

                }
                else
                {
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    int att=Integer.parseInt(Att);
                                    mySqlLiteHelper.AddStudent(Name,Course,Branch,Phone,att);
                                    progressDialog.dismiss();


                                }
                            }, 3000);
                }
            }
        });
        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String OptionItemSelected=Options[i].toString();
                switch(OptionItemSelected)
                {
                    case "Add Student":if(v.getParent()==null)
                    {
                        builder2.setView(R.layout.customform);
                    }
                        builder2.create();
                                        builder2.show();
                                         break;
                    case "View Students":
                        Intent intent=new Intent(getApplicationContext(),Students.class);
                        startActivity(intent);
                                             break;
                    case "Make Outbound Call":
                        Toast.makeText(getApplicationContext(),"Available Soon !",Toast.LENGTH_SHORT).show();
                                             break;
                    case "Call History":
                        Toast.makeText(getApplicationContext(),"Available Soon !",Toast.LENGTH_SHORT).show();
                                             break;
                }
            }
        });



        user= FirebaseAuth.getInstance().getCurrentUser();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        AnimationDrawable animationDrawable = (AnimationDrawable) navigationView.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);

        animationDrawable.start();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.NavName);
        TextView email = (TextView)header.findViewById(R.id.NavEmail);

        if(user!=null)
        {
            String Email=user.getEmail();

            name.setText("Admin");
            email.setText(Email);
        }
        Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), "Login Successfully...", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.GREEN);
        snackbar.show();


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
                Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout),"Sign Out Successfully...", Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.GREEN);
                snackbar.show();
                Intent i=new Intent(Home.this,Login.class);
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

    private void onSaveDataFailed()
    {
    }

    private boolean validate(String Name,String Course,String Branch,String Phone,String Att)
    {
        boolean valid = true;

        if (Name.isEmpty() || Name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        }
        else
        {
            name.setError(null);
        }

        if (Course.isEmpty() || Course.length() < 3) {
            course.setError("at least 3 characters");
            valid = false;
        }
        else
        {
            course.setError(null);
        }


        if (Branch.isEmpty() || Branch.length() < 3) {
            branch.setError("at least 3 characters");
            valid = false;
        }
        else
        {
            branch.setError(null);
        }

        if (Phone.isEmpty() || Phone.length() < 10 || Phone.length() > 10) {
            phone.setError("Phone Number Must Be 10 Digit");
            valid = false;
        } else {
            phone.setError(null);
        }

        if (Att.isEmpty() || Att.length() < 3 || Att.length() > 3) {
            att.setError("Attendance Must Be 10 Digit");
            valid = false;
        } else {
            att.setError(null);
        }



        return valid;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus)
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (!hasFocus)
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        else
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.LogOut)
        {
            builder.create();
            builder.setCancelable(false);
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.Profile) {
            // Handle the camera action
        } else if (id == R.id.Gallery) {

        } else if (id == R.id.Setting) {

        } else if (id == R.id.Invite) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
