package com.androidnik.emrannik.firebaseblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
private Button btnLogout;
private FirebaseAuth firebaseAuth;
private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Photo Blog");

        /*btnLogout=(Button)findViewById(R.id.btn_logout) ;

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
           sentToMain();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                Toast.makeText(getApplicationContext(),"search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_account_setting:
                Toast.makeText(getApplicationContext(),"Account Setting",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomeActivity.this, AccountSetUpActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        firebaseAuth.getInstance().signOut();
        sentToMain();
    }
    private void sentToMain() {
        Intent intent = new Intent(HomeActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
    }

}
