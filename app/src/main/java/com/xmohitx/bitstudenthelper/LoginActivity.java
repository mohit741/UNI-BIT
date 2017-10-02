package com.xmohitx.bitstudenthelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    JSONObject resultJ;
    TextView textView;
    String URL="http://10.0.2.2/BITStudentHelper/getdet.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    String[] details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView=(TextView)findViewById(R.id.txt) ;
        String profile = getIntent().getStringExtra("userid");
        Details det=new Details();
        det.execute(profile);


        /*ListView listView = (ListView) findViewById(R.id.listDetails);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,details);
        listView.setAdapter(itemsAdapter);*/





    }

    private class Details extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Getting data...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String...args) {
            String name= args[0];

            HashMap<String,String> params = new HashMap<>();
            params.put("UserID", name);
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            progressDialog.dismiss();
            try {
                details=new String[5];
                details[0]=result.getString("Name");
                details[1]=result.getString("Course")+"/"+result.getString("Roll")+"/"+result.getString("YoA");
                details[2]=result.getString("Email");
                details[3]=result.getString("Phone");
                details[4]=result.getString("CurrSem");
                ListView listView = (ListView) findViewById(R.id.list);
                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(LoginActivity.this, R.layout.textlayout,details);
                listView.setAdapter(itemsAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
