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

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    Spinner spin1,spin2;
    EditText uid,pass;
    StringBuilder st;
    String br=new String();
    String yr=new String();
    boolean f1=true,f2=true,f3=true;
    String URL="http://10.0.2.2/BITStudentHelper/LOGIN.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinners Initiated
        spin1=(Spinner)findViewById(R.id.BrSelector);
        final ArrayAdapter<CharSequence> adp1=ArrayAdapter.createFromResource(this,R.array.course,android.R.layout.simple_spinner_item);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adp1);
        spin2=(Spinner)findViewById(R.id.YrSelector);
        final ArrayAdapter<CharSequence> adp2=ArrayAdapter.createFromResource(this,R.array.yr,android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adp2);

        //TextFields initiated
        uid=(EditText)findViewById(R.id.roll);
        pass=(EditText)findViewById(R.id.pass);

        //Buttons Initiated
        btn1=(Button)findViewById(R.id.login);
        btn2=(Button)findViewById(R.id.reg);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid.getText().toString().length()<5) f3=false;
                else f1=true;
                if(f1&&f2&&f3)
                {
                    st=new StringBuilder();
                    st.append(br);
                    st.append(uid.getText().toString());
                    st.append(yr);
                    Log.d("prn",st.toString());
                    Log.d("prn",pass.getText().toString());
                    Login login=new Login();
                    login.execute(st.toString(),pass.getText().toString());
                }
                else
                {
                    if(!f1) Toast.makeText(getApplicationContext(),"Please select course",Toast.LENGTH_LONG).show();
                    else if(!f2) Toast.makeText(getApplicationContext(),"Please enter valid roll number",Toast.LENGTH_LONG).show();
                    else if(!f3) Toast.makeText(getApplicationContext(),"Please select year",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(in);
            }
        });

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f1=true;
                br=adp1.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                f1=false;

            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yr=adp2.getItem(position).toString();
                f3=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                f3=false;
            }
        });

    }
    private class Login extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Attempting login...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String...args) {
            String password = args[1];
            String name= args[0];

            HashMap<String,String> params = new HashMap<>();
            params.put("UserID", name);
            params.put("Pass", password);
            params.put("Email","");
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            progressDialog.dismiss();
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    if(result.getInt("success")==1)
                    {
                        Intent in= new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(in);
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
