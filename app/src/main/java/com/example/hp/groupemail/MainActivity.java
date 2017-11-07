package com.example.hp.groupemail;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.addGroup);
        b2=(Button)findViewById(R.id.createMail);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3=(Button)findViewById(R.id.view);
        b3.setOnClickListener(this);
        b4=(Button)findViewById(R.id.addEmail);
        b4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v==b1){
            Intent intent=new Intent(getApplicationContext(),group.class);
            startActivity(intent);
        }
        if (v==b2){
            Intent intent=new Intent(getApplicationContext(),email.class);
            startActivity(intent);
        }
        if (v==b3){
            Intent intent=new Intent(getApplicationContext(),viewgrps.class);
            startActivity(intent);
        }
        if (v==b4) {
            Intent intent = new Intent(getApplicationContext(),addEmail.class);
            startActivity(intent);
        }
    }
}
