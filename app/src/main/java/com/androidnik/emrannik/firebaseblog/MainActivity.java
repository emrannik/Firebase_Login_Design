package com.androidnik.emrannik.firebaseblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText confirmpass;
    private TextView tvsignup;
    private FirebaseAuth firebaseAuth;
    private TextView tv_signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

         firebaseAuth=FirebaseAuth.getInstance();
         email=(EditText)findViewById(R.id.input_email);
         password=(EditText)findViewById(R.id.input_password);
         confirmpass=(EditText)findViewById(R.id.input_confirm_password);
         tvsignup=(TextView) findViewById(R.id.btn_create_account);
         tv_signIn=(TextView)findViewById(R.id.textView_signin);


         tvsignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String emailTxt=email.getText().toString();
                 String pwd=password.getText().toString();
                 String con_pwd=confirmpass.getText().toString();

                 if(emailTxt.isEmpty()){
                     email.setError("Plase enter email ");
                     email.requestFocus();
                 }
                 else if(pwd.isEmpty()){
                     password.setError("Plase enter your password");
                     password.requestFocus();
                 }
                 else if(con_pwd.isEmpty()){
                     confirmpass.setError("Plase enter your password");
                     confirmpass.requestFocus();
                 }
                 else if(emailTxt.isEmpty() && pwd.isEmpty() && con_pwd.isEmpty()){
                     Toast.makeText(MainActivity.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                 }
                 else if(pwd.equals(con_pwd)){

                     firebaseAuth.createUserWithEmailAndPassword(emailTxt,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                                 if(task.isSuccessful()){
                                     startActivity(new Intent(MainActivity.this,AccountSetUpActivity.class));
                                     //HomeActivity instead of AccountSetUpActivity//
                                 }else {
                                     Toast.makeText(MainActivity.this,"Create Account unsuccesful, try again",Toast.LENGTH_LONG).show();
                                 }

                         }

                     });
                   } else{
                     Toast.makeText(MainActivity.this,"Password don't match",Toast.LENGTH_LONG).show();
                 }
             }
         });

         tv_signIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                 startActivity(intent);
                 finish();
             }
         });


    }
}
