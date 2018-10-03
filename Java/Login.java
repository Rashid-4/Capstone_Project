package com.example.rashidalikhan.firebasedemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @BindView(R.id.Email) EditText email;
    @BindView(R.id.Pass) EditText pass;
    int k = 0;
    @BindView(R.id.link_Reg) TextView loginReg;
    @BindView(R.id.Login) Button Login;
    ProgressBar pb;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        pb = (ProgressBar) findViewById(R.id.ProgressBar);
        pb.setVisibility(View.INVISIBLE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.Mylayout2);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        loginReg.setVisibility(View.INVISIBLE);

        animationDrawable.start();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        loginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
                // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        k++;
        if (k == 1) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2), "Press Onces Again to Exit !", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        } else {

            finish();
            System.exit(0);
            moveTaskToBack(false);
        }
    }


    public void Login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        Login.setEnabled(false);
        email.setEnabled(false);
        pass.setEnabled(false);

        // String Name = name.getText().toString();
        final String Email = email.getText().toString();
        final String Pass = pass.getText().toString();
        //String CPass = cpass.getText().toString();
        pb.setVisibility(View.VISIBLE);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onLoginSuccess(Email, Pass);
                    }
                }, 3000);

    }


    public void onLoginSuccess(String Email, String Pass)
    {

        mAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            /*Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2), "Login Successfully...", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.GREEN);
                            snackbar.show();*/
                            pb.setVisibility(View.INVISIBLE);
                            moveTaskToBack(false);
                            Intent i=new Intent(getApplicationContext(),Home.class);
                            startActivity(i);
                            finish();
                            System.exit(0);
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2), "Login Failed or User Not Found!", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.RED);
                            snackbar.show();
                            pb.setVisibility(View.INVISIBLE);
                            email.setEnabled(true);
                            pass.setEnabled(true);
                            Login.setEnabled(true);

                        }


                    }
                });
    }

    public void onLoginFailed() {
        Login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String Email = email.getText().toString();
        String Password = pass.getText().toString();

        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pass.setError(null);
        }

        return valid;
    }
}