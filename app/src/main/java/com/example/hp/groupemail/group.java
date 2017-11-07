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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class group extends AppCompatActivity implements View.OnClickListener {
    Button b_add,b_view,b_del,b_del_all,b_email,b_srch;
    EditText e_id,e_group;
    CheckBox Cb;
    SQLiteDatabase db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_group);
        e_id=(EditText)findViewById(R.id.editText);
        e_group=(EditText) findViewById(R.id.editText2);
        b_add=(Button)findViewById(R.id.addg);
        b_add.setOnClickListener(this);
        Cb=(CheckBox)findViewById(R.id.checkBox);
        db1=openOrCreateDatabase("GROUP DATABASE", Context.MODE_PRIVATE,null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS grp(id VARCHAR,name VARCHAR);");
        b_view=(Button)findViewById(R.id.view);
        b_view.setOnClickListener(this);
        b_del=(Button)findViewById(R.id.del);
        b_del.setOnClickListener(this);
        b_del_all=(Button)findViewById(R.id.del_all);
        b_del_all.setOnClickListener(this);
        b_email=(Button)findViewById(R.id.email);
        b_email.setOnClickListener(this);
        b_srch=(Button)findViewById(R.id.srch);
        b_srch.setOnClickListener(this);
        e_id.requestFocus();
    }

        @Override
    public void onClick(View v) {
        if(v==b_add) {
            if (Cb.isChecked())
            {
                if (e_id.getText().toString().trim().length() == 0 || e_group.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter Group ID or Name");
                    return;
                }
                db1.execSQL("INSERT INTO grp VALUES('" + e_id.getText() + "','" + e_group.getText() + "');");
                showMessage("Success", "Group Added");
                clearText();
            }
            else
            {
                showMessage("Error","Check the Box");
            }
        }

        else if(v==b_view)
        {
            Cursor c=db1.rawQuery("SELECT*FROM grp",null);
            if (c.getCount()==0)
            {
                showMessage("Error","NoData");
                return;
            }
            StringBuilder sb=new StringBuilder();
            while (c.moveToNext()){
                sb.append("\n\nGroup: "+c.getString(0));
                sb.append("\nGroup Id: "+c.getString(1));
            }
            showMessage("Student Details:",sb.toString());
        }

        else if (v == b_srch) {

                Cursor c = db1.rawQuery("SELECT*FROM grp WHERE id='" + e_id.getText() + "'", null);
                Cursor d = db1.rawQuery("SELECT*FROM grp WHERE name='" + e_group.getText() + "'", null);


                if (c.moveToFirst()) {
                    StringBuffer sb = new StringBuffer();

                    sb.append("\n\nGroup Id: " + c.getString(0));
                    sb.append("\nGroup: " + c.getString(1));
                    showMessage("Success", sb.toString());
                    return;
                }
                if (d.moveToFirst()) {
                    StringBuffer sb = new StringBuffer();

                    sb.append("\n\nGroup Id: " + d.getString(0));
                    sb.append("\nGroup: " + d.getString(1));
                    showMessage("Success", sb.toString());
                    return;
                }
                else
                {
                    showMessage("Error", "Id not Found");
                }
            }
            else if (v == b_del) {
                if (e_id.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Specify Id");
                    return;
                }

                Cursor c = db1.rawQuery("SELECT*FROM grp WHERE id='" + e_id.getText() + "'", null);
                if (c.moveToFirst()) {
                    db1.execSQL("DELETE FROM grp WHERE id='" + e_id.getText() + "'");
                    showMessage("Success", "Group Deleted");
                } else if (e_id != c) {
                    showMessage("Error", "Id not Existing");
                }
                e_id.setText("");
                e_id.requestFocus();
            }
            else if (v == b_del_all) {
                Cursor c=db1.rawQuery("SELECT*FROM grp",null);
            if (c.getCount()==0)
            {
                showMessage("Error","No Groups,Enter Data");
                e_id.requestFocus();
                return;
            }
            AlertDialog.Builder build=new AlertDialog.Builder(this);
            build.setMessage("Are you Sure you want to Delete The Groups");
            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Cursor d = db1.rawQuery("DELETE FROM grp", null);
                    if (d.getCount() == 0) {
                        showMessage("Success", "All Groups Deleted");

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
            else if (v == b_email) {
            Cursor c=db1.rawQuery("SELECT*FROM grp",null);
            if (c.getCount()==0)
            {
                showMessage("Error","Group list is Empty to Send Mails");
                e_id.requestFocus();
                return;
            }
                Intent i = new Intent(getApplicationContext(), email.class);
                startActivity(i);
            }
        }

    private void clearText() {
        e_id.setText("");
        e_group.setText("");e_id.requestFocus();
    }

    private void showMessage(String error, String s) {
        AlertDialog.Builder b=new AlertDialog.Builder(this);b.setCancelable(true);
        b.setTitle(error);
        b.setMessage(s);
        b.show();
    }
}
