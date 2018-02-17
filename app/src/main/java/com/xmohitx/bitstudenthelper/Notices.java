package com.xmohitx.bitstudenthelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Notices extends AppCompatActivity {

    String URL="http://xmohit741x.000webhostapp.com/getNotices.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    String[] details;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Branch = "branch";
    public static final String Sem= "sem";
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        t1=(TextView)findViewById(R.id.noticeTitle);
        t2=(TextView)findViewById(R.id.noticeDate);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String branch=sharedpreferences.getString(Branch, "");
        Log.d("prn",branch);
        String sem=sharedpreferences.getString(Sem, "");
        Log.d("prn",sem);
        GetNotices gn=new GetNotices();
        gn.execute(branch,sem);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(details[2]));
                startActivity(browserIntent);
            }
        });


    }

    private class GetNotices extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Notices.this);
            progressDialog.setMessage("Getting data...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String...args) {
            String br= args[0];
            String sem =args[1];
            HashMap<String,String> params = new HashMap<>();
            params.put("Branch", br);
            params.put("CurrSem", sem);
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            progressDialog.dismiss();
            try {
                details=new String[3];
                details[0]=result.getString("addedOn");
                details[1]=result.getString("title");
                details[2]=result.getString("link");
                String d = "Date Added : ";
                d = d+details[0];
                t1.setText(details[1]);
                t2.setText(d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
