package com.xmohitx.bitstudenthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    Spinner spin1,spin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinners Initiated
        spin1=(Spinner)findViewById(R.id.BrSelector);
        ArrayAdapter<CharSequence> adp1=ArrayAdapter.createFromResource(this,R.array.branch,android.R.layout.simple_spinner_item);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adp1);
        spin2=(Spinner)findViewById(R.id.YrSelector);
        ArrayAdapter<CharSequence> adp2=ArrayAdapter.createFromResource(this,R.array.yr,android.R.layout.simple_spinner_item);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adp2);

        //Buttons Initiated
        btn1=(Button)findViewById(R.id.login);
        btn2=(Button)findViewById(R.id.reg);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainActivity.this,null);
                startActivity(in);
            }
        });


    }
}
