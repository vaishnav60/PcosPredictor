package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewLog extends AppCompatActivity {

    JSONParser jsonParser;
    JSONArray jarr;
    ListView lv;

    String date[];
    String A1[];
    String A2[];
    String status[];

    ProgressDialog progressDialog;
    String url_log = Common.SERVER_URL + "/App/getLog.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        getSupportActionBar().setTitle("Log my History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        jsonParser=new JSONParser();
        lv=(ListView)findViewById(R.id.lstView);
        new getLog().execute();
    }

    class getLog extends AsyncTask<String,String,String> {
        @Override
        public void onPreExecute(){
            super.onPreExecute();
            progressDialog=new ProgressDialog(ViewLog.this);
            progressDialog.setMessage("Fetching details...Please Wait");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        public String doInBackground(String...args){


            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("username",Common.username));

            JSONObject json=jsonParser.makeHttpRequest(url_log,"POST",params);

            try{
                JSONArray jsonArray=json.getJSONArray("response");
                date=new String[jsonArray.length()];
                A1=new String[jsonArray.length()];
                A2=new String[jsonArray.length()];
                status=new String[jsonArray.length()];

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject c=jsonArray.getJSONObject(i);

                    date[i]=c.getString("Date");
                    A1[i]=c.getString("A1");
                    A2[i]=c.getString("A2");
                    status[i]=c.getString("status");
                }


            }
            catch (Exception ex){

            }

            return null;
        }

        @Override
        public void onPostExecute(String result){
            progressDialog.dismiss();
            if(date.length>0){
                CustomLogDetails obj=new CustomLogDetails(ViewLog.this,date,A1,A2,status);
                lv.setAdapter(obj);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_view_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i=new Intent();
                i.setClass(ViewLog.this,Log_History.class);
                startActivity(i);
                return true;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}