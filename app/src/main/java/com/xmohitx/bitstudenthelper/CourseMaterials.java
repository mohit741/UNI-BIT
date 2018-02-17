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
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CourseMaterials extends AppCompatActivity {

    Button b1,b2,b3;
    String URL="http://xmohit741x.000webhostapp.com/getMaterials.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog progressDialog;
    String[] details;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Branch = "branch";
    public static final String Sem= "sem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_materials);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        b1=(Button)findViewById(R.id.booksURL);
        b2=(Button)findViewById(R.id.quesURL);
        b3=(Button)findViewById(R.id.syllabusURL);
        String branch=sharedpreferences.getString(Branch, "");
        Log.d("prn",branch);
        String sem=sharedpreferences.getString(Sem, "");
        Log.d("prn",sem);
        Materials m = new Materials();
        m.execute(branch,sem);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked",details[0]);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(details[0]));
                startActivity(browserIntent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(details[1]));
                startActivity(browserIntent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(details[2]));
                startActivity(browserIntent);
            }
        });


    }

    private class Materials extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(CourseMaterials.this);
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
                details[0]=result.getString("books");
                details[1]=result.getString("ques_papers");
                details[2]=result.getString("syllabus");
                Log.d("Clicked",details[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
