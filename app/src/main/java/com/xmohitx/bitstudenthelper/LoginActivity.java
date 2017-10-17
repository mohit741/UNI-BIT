package com.xmohitx.bitstudenthelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    JSONObject resultJ;
    TextView textView;
    String URL="http://xmohit741x.000webhostapp.com/getdet.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    String[] details;
    Button logOut;
    public static final String mypreference = "mypref";
    public static final String UserName = "userid";
    public static final String PassWord= "password";
    public static final String login= "loggedin";
    SharedPreferences sharedpreferences;
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

        logOut=(Button)findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();

            }
        });

        /*ListView listView = (ListView) findViewById(R.id.listDetails);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,details);
        listView.setAdapter(itemsAdapter);*/

    }
    private void logOut()
    {
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(UserName);
        editor.remove(PassWord);
        editor.putBoolean(login, false);
        editor.apply();
        Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
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
