package com.xmohitx.bitstudenthelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


/*
User will be redirected here after logging in
 */
public class LoginActivity extends AppCompatActivity {

    JSONObject resultJ;
    String URL="http://xmohit741x.000webhostapp.com/getdet.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    String[] details;
    public static final String mypreference = "mypref";
    public static final String Branch = "branch";
    public static final String Sem= "sem";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String profile = getIntent().getStringExtra("userid");
        Log.d("prn",profile);
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
                details=new String[6];
                details[0]=result.getString("Name");
                details[1]=result.getString("Course")+"/"+result.getString("Roll")+"/"+result.getString("YoA");
                details[2]=result.getString("Email");
                details[3]=result.getString("Phone");
                details[4]=result.getString("CurrSem");
                details[5]=result.getString("Branch");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Branch, details[5]);
                editor.putString(Sem, details[4]);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                startActivity(intent);
                LoginActivity.this.finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
