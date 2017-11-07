package com.example.hp.groupemail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class email extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    int i=0;
    Spinner spin;//spin2;
   EditText tv;
    SQLiteDatabase db1;
    String name[]=new String[100],sp[],sw;
    String name1[]=new String[100],sp2[],sw2;
    Button b1,b2,b3;
    EditText e1,e2;
    Cursor c,d;
    CheckBox cb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email);

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        tv=(EditText) findViewById(R.id.editText11);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        db1=openOrCreateDatabase("GROUP DATABASE", Context.MODE_PRIVATE,null);
        b3.setOnClickListener(this);
        c=db1.rawQuery("SELECT*FROM grp",null);
        cb1=(CheckBox)findViewById(R.id.checkBox4);
        e1=(EditText)findViewById(R.id.editText5);
        e2=(EditText)findViewById(R.id.editText6);
        spin=(Spinner)findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        e1.requestFocus();



        while(c.moveToNext())
        {
            name[i]=c.getString(1);
            i++;
        }
        sp=new String[i];
        for (int j=0;j<i;j++)
        {
            sp[j]=name[j];
        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sp);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);



    }

    @Override
    public void onClick(View v) {
        if (v==b1)
        {
            if(cb1.isChecked())
            {
                Intent intent = new Intent(getApplicationContext(), addEmail.class);
                startActivity(intent);
            }
            else
            {
                showMessage("Error","Please Check the Check Box\nIf You Have to Add Email");
            }
        }

        if (v==b2)
        {
            if (e1.getText().toString().trim().length()==0)
            {
                e1.setText("");
                e2.setText("");
                e1.requestFocus();

            }
            else
            {
                e1.setText("");
                e2.setText("");
                showMessage("Done","Successfully Cleared the email data");
            }
        }

        if (v==b3)
        {
            String to=tv.getText().toString();
            String subject=e1.getText().toString();
            String msg=e2.getText().toString();

            Intent email=new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
            email.putExtra(Intent.EXTRA_SUBJECT,subject);
            email.putExtra(Intent.EXTRA_TEXT,msg);

            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email,"Choose an Email Client:"));
        }

    }

    private void showMessage(String error, String s) {
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(error);
        build.setMessage(s);
        build.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),sp[position],Toast.LENGTH_LONG).show();
        int k=0;
        if(sw!=sp[position])

        {
            tv.setText("");
        }
        sw=sp[position];

        d=db1.rawQuery("SELECT*FROM grp1 WHERE grt='"+sw+"'",null);
        while(d.moveToNext())
        {
            name1[k]=d.getString(1);
            k++;
        }
        sp2=new String[k];
        for(int j=0;j<k;j++)
        {
            sp2[j]=name1[j];
            tv.append(sp2[j]+";");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}