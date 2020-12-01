package com.androidnik.emrannik.firebaseblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView tvSign_in;
    private FirebaseAuth firebaseAuth;
    private TextView tv_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        tvSign_in=(TextView) findViewById(R.id.tvSign);
        tv_signUp=(TextView)findViewById(R.id.link_signup);

        /*mAuthStateListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user !=null){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_LONG).show();

                    Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_LONG).show();

                }
            }
        };*/
        tvSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt=email.getText().toString();
                String pwd=password.getText().toString();

                if(emailTxt.isEmpty()){
                    email.setError("Plase enter email ");
                    email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Plase enter your password");
                    password.requestFocus();
                }
                else if(emailTxt.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(emailTxt.isEmpty() && pwd.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(emailTxt,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error Try again",Toast.LENGTH_LONG).show();

                            }else {
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            sentToMain();
        }
    }

    private void sentToMain() {
        Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_LONG).show();

        Intent i=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(i);
    }
}
