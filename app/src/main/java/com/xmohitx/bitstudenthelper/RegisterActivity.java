package com.xmohitx.bitstudenthelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Spinner spin1,spin2;
    EditText roll,pass,repass,name,curr,yoa,phone,email;
    Button btn;
    StringBuilder str;
    String bran,cours;
    JSONParser jsParser=new JSONParser();
    String URL="http://10.0.2.2/BITStudentHelper/REG.php";
    String T="Print";
    ProgressDialog progressDialog;
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
                if(adp1.getItem(position)!=null)
                {
                    cours=adp1.getItem(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adp2.getItem(position)!=null)
                {
                    bran=adp2.getItem(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

       btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                str=new StringBuilder();
                str.append(cours);
                str.append(roll.getText().toString());
                str.append(yoa.getText().toString());
                Log.d(T,str.toString());
                Log.d(T,pass.getText().toString());
                Log.d(T,roll.getText().toString());
                Log.d(T,name.getText().toString());
                Log.d(T,email.getText().toString());
                Log.d( T,bran);
                Log.d(T,phone.getText().toString());
                Log.d(T,cours);
                Log.d(T,yoa.getText().toString());
                Log.d(T,curr.getText().toString());
                RegisterUser reg=new RegisterUser();
                reg.execute(str.toString(), pass.getText().toString(), roll.getText().toString(), name.getText().toString() ,email.getText().toString(), bran, phone.getText().toString(), cours,yoa.getText().toString(), curr.getText().toString());
            }
        });

    }
    private class RegisterUser extends AsyncTask<String,Void,JSONObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String password = args[1];
            String uname= args[0];
            String roll =args[2];
            String name =args[3];
            String email =args[4];
            String branch =args[5];
            String phone =args[6];
            String course =args[7];
            String yoa =args[8];
            String currSem =args[9];

            HashMap<String,String> params = new HashMap<>();
            params.put("UserID", uname);
            params.put("Pass", password);
            params.put("Email", email);
            params.put("Roll", roll);
            params.put("Name", name);
            params.put("Branch", branch);
            params.put("Phone", phone);
            params.put("Course", course);
            params.put("YoA", yoa);
            params.put("CurrSem", currSem);

            JSONObject js = jsParser.makeHttpRequest(URL, "POST", params);

            return js;

        }

        @Override
        protected void onPostExecute(JSONObject result)
        {
            progressDialog.dismiss();
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    if(result.getInt("success")==1){
                        //TODO
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
