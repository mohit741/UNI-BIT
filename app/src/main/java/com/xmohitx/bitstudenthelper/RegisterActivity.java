package com.xmohitx.bitstudenthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {
    Spinner spin1,spin2;
    EditText roll,pass,repass,name,curr,yoa,phone,email;
    Button btn;
    StringBuilder str= new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Spinners
        spin1=(Spinner)findViewById(R.id.course);
        final ArrayAdapter<CharSequence> adp1=ArrayAdapter.createFromResource(this,R.array.course,android.R.layout.simple_spinner_item);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adp1);

        spin2=(Spinner)findViewById(R.id.branch);
        final ArrayAdapter<CharSequence> adp2=ArrayAdapter.createFromResource(this,R.array.branch,android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adp2);

        //EditTexts
        roll=(EditText)findViewById(R.id.rollno);
        pass=(EditText)findViewById(R.id.pass);
        repass=(EditText)findViewById(R.id.repass);
        name=(EditText)findViewById(R.id.name);
        curr=(EditText)findViewById(R.id.currsem);
        yoa=(EditText)findViewById(R.id.yoa);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);

        //Button
        btn=(Button)findViewById(R.id.reg);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str.append(adp1.getItem(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        str.append(roll.getText().toString());

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str.append(adp2.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

       /* btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                RegisterUser reg=new RegisterUser();
                reg.execute(str.toString(), pass.getText().toString(), roll.getText().toString(), name.getText().toString() ,email.getText().toString(), bran, phone.getText().toString(), cours,yoa.getText().toString(), Integer.parseInt(curr.getText().toString()) );
            }
        });
*/
    }
}
