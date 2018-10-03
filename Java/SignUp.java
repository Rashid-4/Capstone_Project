package com.example.rashidalikhan.firebasedemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import butterknife.ButterKnife;
import butterknife.BindView;

public class SignUp extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    ProgressBar pb;
    LinearLayout linearLayout;
    @BindView(R.id.input_name) EditText name;
    @BindView(R.id.input_email) EditText email;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.c_pass) EditText cpass;
    @BindView(R.id.signUp) Button signUp;
    @BindView(R.id.link_login) TextView loginLink;



    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if(!hasFocus)
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        else
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        pb=(ProgressBar)findViewById(R.id.ProgressBar);
        pb.setVisibility(View.INVISIBLE);
        linearLayout=(LinearLayout)findViewById(R.id.myLay);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
                // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup()
    {
        if (!validate())
        {
            onSignupFailed();
            return;
        }

        signUp.setEnabled(false);
        name.setEnabled(false);
        email.setEnabled(false);
        pass.setEnabled(false);
        cpass.setEnabled(false);

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
                        onSignupSuccess(Email,Pass);
                        pb.setVisibility(View.INVISIBLE);
                        signUp.setEnabled(true);
                        name.setEnabled(true);
                        email.setEnabled(true);
                        pass.setEnabled(true);
                        cpass.setEnabled(true);

                    }
                }, 3000);

    }


    public void onSignupSuccess(String Email,String Pass)
    {
        //setResult(RESULT_OK, null);
        mAuth.createUserWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2),"User Already Exits !", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.YELLOW);
                            snackbar.show();

                            //Toast.makeText(getApplicationContext(),"User Already Exits !",Toast.LENGTH_LONG).show();

                        }
                        else if(task.isComplete())
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2),"User Registered Successfully...", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.GREEN);
                            snackbar.show();
                            name.setText("");
                            cpass.setText("");
                            pass.setText("");
                            email.setText("");


                            //Toast.makeText(getApplicationContext(),"User Registered Successfully...",Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Mylayout2),"User Registration Failed !", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.RED);
                            snackbar.show();
                            //Toast.makeText(getApplicationContext(),"User Registration Failed !",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void onSignupFailed()
    {
        signUp.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = pass.getText().toString();
        String RePassword = cpass.getText().toString();

        if (Name.isEmpty() || Name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }



        if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
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

        if (RePassword.isEmpty() || RePassword.length() < 4 || RePassword.length() > 10 || !(RePassword.equals(Password))) {
            cpass.setError("Password Do not match");
            valid = false;
        } else {
            cpass.setError(null);
        }

        return valid;
    }
}
