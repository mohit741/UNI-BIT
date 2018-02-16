package com.xmohitx.bitstudenthelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button b1,b2,b3,b4;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        b1=(Button)findViewById(R.id.courseMaterials);
        b2=(Button)findViewById(R.id.notices);
        b3=(Button)findViewById(R.id.actvity);
        b4=(Button)findViewById(R.id.logout);

        //Set OnClick Events
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainMenu.this,CourseMaterials.class);
                startActivity(in);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainMenu.this,Notices.class);
                startActivity(in);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainMenu.this,Activities.class);
                startActivity(in);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent in= new Intent(MainMenu.this,MainActivity.class);
                startActivity(in);
            }
        });


    }
}
