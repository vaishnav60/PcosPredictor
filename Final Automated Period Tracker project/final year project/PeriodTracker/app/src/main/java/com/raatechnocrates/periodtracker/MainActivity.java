package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_fetch_date = Common.SERVER_URL + "/App/getNextPeriodDate.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    TextView tv_warning, tv_nextdate, tv_noofdays;
    LinearLayout ll_warning, ll_date;

    String strPreviousDate;
    String strNextDate;
    String strNoOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_warning = (TextView)findViewById(R.id.txtWarning);
        tv_nextdate = (TextView)findViewById(R.id.txtNextDate);
        tv_noofdays = (TextView)findViewById(R.id.txtNoOfDays);

        ll_warning = (LinearLayout)findViewById(R.id.displayWarning);
        ll_date = (LinearLayout)findViewById(R.id.displayDate);
        new fetchDate().execute();
    }

    public void track_click(View v)
    {
        if(strNoOfDays.length()>0)
        {
            Intent i=new Intent();
            i.setClass(MainActivity.this,TrackCycle.class);
            Bundle bundle = new Bundle();
            bundle.putString("previous", strPreviousDate);
            bundle.putString("next", strNextDate);
            bundle.putString("days", strNoOfDays);
            i.putExtras(bundle);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"No previous dates found",Toast.LENGTH_SHORT).show();
        }
    }

    public void symptom_check_click(View v)
    {
        Intent i=new Intent();
        i.setClass(MainActivity.this,SymptomChecker.class);
        startActivity(i);
    }

    public void log_history_click(View v)
    {
        Intent i=new Intent();
        i.setClass(MainActivity.this,ViewLog.class);
        startActivity(i);
    }

    public void logout_click(View v)
    {
        Intent i=new Intent();
        i.setClass(MainActivity.this,Login.class);
        startActivity(i);
        finish();
    }

    class fetchDate extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Calculating next period date.. Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Username", Common.username));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_fetch_date, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString("message");
                if (success == 1) {

                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            ll_date.setVisibility(View.VISIBLE);
                            ll_warning.setVisibility(View.GONE);

                            String prev_dt = message;  // Prev Start date
                            strPreviousDate = prev_dt;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(prev_dt));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 28);  // number of days to add
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            String next_date = sdf1.format(c.getTime());
                            strNextDate = next_date;

                            Date cur_date = new Date();
                            String today_date = sdf1.format(cur_date.getTime());

                            long days_diff = daysBetween(today_date,next_date,"yyyy-MM-dd");
                            strNoOfDays = "" + days_diff;

                            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
                            String disp_date = sdf2.format(c.getTime());
                            tv_nextdate.setText(disp_date);
                            tv_noofdays.setText("" + days_diff);
                        }
                    });

                } else {

                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            ll_date.setVisibility(View.GONE);
                            ll_warning.setVisibility(View.VISIBLE);
                            tv_warning.setText(message);
                            strNoOfDays = "";
                            strNextDate = "";
                            strPreviousDate = "";
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    public long daysBetween(String date1,String date2,String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null,Date2 = null;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i=new Intent();
                i.setClass(MainActivity.this,Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}