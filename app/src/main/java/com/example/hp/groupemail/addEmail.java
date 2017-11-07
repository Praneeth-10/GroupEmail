package com.example.hp.groupemail;

import android.content.Context;
import android.content.DialogInterface;
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

public class addEmail extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    SQLiteDatabase db;Spinner spin;
    int i=0;
    String ne[],qw;
    String name[]=new String[100];
    Button b1,b2,b3,b4,b5;
    EditText e1,e2;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        spin=(Spinner)findViewById(R.id.spinner3);
        spin.setOnItemSelectedListener(this);
        db=openOrCreateDatabase("GROUP DATABASE", Context.MODE_PRIVATE,null);

        Cursor c=db.rawQuery("SELECT*FROM grp",null);
        b1=(Button)findViewById(R.id.button5);
        b1.setOnClickListener(this);

        b2=(Button)findViewById(R.id.button6);
        b2.setOnClickListener(this);

        b3=(Button)findViewById(R.id.button7);
        b3.setOnClickListener(this);

        b4=(Button)findViewById(R.id.button8);
        b4.setOnClickListener(this);

        b5=(Button)findViewById(R.id.button9);
        b5.setOnClickListener(this);

        e2=(EditText)findViewById(R.id.editText4);
        e1=(EditText)findViewById(R.id.editText3);
        while (c.moveToNext())
        {
            name[i]=c.getString(1);
            i++;
        }
        ne = new String[i];
for(int j=0;j<i;j++)
{
    ne[j]=name[j];
}

        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ne);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        db.execSQL("CREATE TABLE IF NOT EXISTS grp1(grt VARCHAR,eml VARCHAR,mobile VARCHAR);");


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      Toast.makeText(getApplicationContext(),ne[position],Toast.LENGTH_SHORT).show();
         qw = ne[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

   @Override
    public void onClick(View v) {
        if (v==b1)
        {
            if (e1.getText().toString().trim().length()==0||e2.getText().toString().trim().length()==0)
            {
                showMessage("Error","Fill all spaces");
                return;
            }
            else
            {
                c=db.rawQuery("SELECT*FROM grp1",null);
                db.execSQL("INSERT INTO grp1 VALUES('"+qw+"','"+e1.getText()+"','"+e2.getText()+"');");
                showMessage("Success","Added Successfully");
                clearText();
                e1.requestFocus();
                return;
            }

        }
       if (v==b2) {
           c = db.rawQuery("SELECT*FROM grp1", null);
           if (e1.getText().toString().trim().length() == 0) {
               showMessage("Error", "Enter Full id");
               return;
           }
           if (c.getCount() == 0) {
               showMessage("Error", "Group is Empty");
               return;
           }
           if (c.moveToFirst()) {
               Cursor c = db.rawQuery("DELETE FROM grp1 WHERE eml='" + e1.getText() + "'", null);
               if (c.getCount() == 0) {
                   showMessage("Success", "Deleted");
                   clearText();
               }
           }
       }
        if (v==b3)
        {
            c=db.rawQuery("SELECT*FROM grp1",null);
            if (c.getCount()==0){
                showMessage("Error","No Records Found");
                return;
            }

            StringBuffer sb=new StringBuffer();
            while (c.moveToNext()) {
                if (c.getString(0).equals(qw)) {
                    sb.append("Group:" + c.getString(0) + "\n");
                    sb.append("Email:" + c.getString(1) + "\n");
                    sb.append("Phone:" + c.getString(2) + "\n\n");
                }
            }
            showMessage("Student Details",sb.toString());
            return;

        }
       if (v==b4)
        {
            c=db.rawQuery("SELECT*FROM grp1",null);
            if (c.getCount()==0)
            {
                showMessage("Error", "Nothing to Delete");
                return;
            }
            AlertDialog.Builder build=new AlertDialog.Builder(this);
            build.setMessage("Are you Sure you want to Delete The Groups");
            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Cursor d = db.rawQuery("DELETE FROM grp1", null);
                    if (d.getCount() == 0) {
                        showMessage("Success", "All Ids Deleted");
                        return;
                    }
                    else
                    {
                        showMessage("Error","No Groups To Delete");
                    }
                }
            });
            build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"NO",Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog dialog= build.create();
            dialog.show();
        }

         if (v==b5)
       {
           c=db.rawQuery("SELECT*FROM grp1",null);
           if (c.getCount()==0){
               showMessage("Error","No Records Found");
               return;
           }

           StringBuffer sb=new StringBuffer();
           while (c.moveToNext()){
               sb.append("Group:"+c.getString(0)+"\n");
               sb.append("Email:"+c.getString(1)+"\n");
               sb.append("Phone:"+c.getString(2)+"\n\n");
           }
           showMessage("Student Details",sb.toString());

       }

    }

    private void clearText() {
        e1.setText("");
        e2.setText("");
}

    private void showMessage(String success, String s) {
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        build.setTitle(success);
        build.setMessage(s);
        build.show();
    }
}