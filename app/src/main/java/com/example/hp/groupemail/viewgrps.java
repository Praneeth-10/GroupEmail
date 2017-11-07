package com.example.hp.groupemail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class viewgrps extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    Button add;int i=0;
    Spinner spin;
    String name[]=new String[100];String sp[],sw;
    EditText e1,e2;
    SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgrps);

        e2=(EditText)findViewById(R.id.editText8);
        e1=(EditText)findViewById(R.id.editText7);
        add=(Button)findViewById(R.id.addgrp);
        add.setOnClickListener(this);
        spin=(Spinner)findViewById(R.id.spinner2);
        db=openOrCreateDatabase("GROUP DATABASE", Context.MODE_PRIVATE,null);
        c=db.rawQuery("SELECT*FROM grp",null);

        while (c.moveToNext())
        {
            name[i]=c.getString(1);
            i++;
        }
        sp=new String[i];
        for(int j=0;j<i;j++)
        {
            sp[j]=name[j];
        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sp);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            c=db.rawQuery("SELECT*FROM grp",null);
            if(e1.getText().toString().trim().length()==0||e2.getText().toString().trim().length()==0)
            {
                showMessage("Correction","fill Both Text Fields");
                return;
            }
            db.execSQL("INSERT INTO grp VALUES('"+e1.getText()+"','"+e2.getText()+"');");
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Group Added,Do you want to Continue");
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), viewgrps.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog al=builder.create();
            al.show();

        }
    }

    private void showMessage(String correction, String s) {
        AlertDialog.Builder b=new AlertDialog.Builder(this);b.setCancelable(true);
        b.setTitle(correction);
        b.setMessage(s);
        b.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),sp[position],Toast.LENGTH_SHORT).show();
        sw=sp[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
