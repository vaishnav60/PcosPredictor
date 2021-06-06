package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Log_History extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_add_log_history = Common.SERVER_URL + "/App/addLog.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    Calendar cal  = Calendar.getInstance();

    EditText etStartDate, etEndDate;

    String strStartDate, strEndDate;

    RadioButton A1,A2;
    RadioGroup G1,G2;

    String ans1,ans2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__history);

        getSupportActionBar().setTitle("Add Log History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etStartDate = (EditText)findViewById(R.id.txtStartDate);
        etEndDate = (EditText)findViewById(R.id.txtEndDate);
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });

        G1 = (RadioGroup)findViewById(R.id.LHG1);
        G2 = (RadioGroup)findViewById(R.id.LHG2);
    }

    public void openDatePickerDialog(final View v) {
        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    switch (v.getId()) {
                        case R.id.txtStartDate:
                            ((EditText)v).setText(selectedDate);
                            break;
                        case R.id.txtEndDate:
                            ((EditText)v).setText(selectedDate);
                            break;
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public void save_click(View v) {
        strStartDate = etStartDate.getText().toString().trim();
        strEndDate = etEndDate.getText().toString().trim();

        int selectedId1 = G1.getCheckedRadioButtonId();
        int selectedId2 = G2.getCheckedRadioButtonId();

        A1 = (RadioButton) findViewById(selectedId1);
        A2 = (RadioButton) findViewById(selectedId2);

        if (strStartDate.length()<=0)
        {
            Toast.makeText(this,"Please select Start Date",Toast.LENGTH_SHORT).show();
        }
        else if (strEndDate.length()<=0)
        {
            Toast.makeText(this,"Please select End Date",Toast.LENGTH_SHORT).show();
        }
        else if(selectedId1==-1){
            Toast.makeText(this,"Please answer question 1", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId2==-1){
            Toast.makeText(this,"Please answer question 2", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ans1 = A1.getText().toString();
            ans2 = A2.getText().toString();
            new saveLog().execute();
        }
    }

    class saveLog extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Log_History.this);
            pDialog.setMessage("Saving data.. Please Wait..");
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
            params.add(new BasicNameValuePair("StartDate", strStartDate));
            params.add(new BasicNameValuePair("EndDate", strEndDate));
            params.add(new BasicNameValuePair("A1", ans1));
            params.add(new BasicNameValuePair("A2", ans2));
            params.add(new BasicNameValuePair("Status", "User Defined Log Entry"));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_log_history, "POST", params);

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
                            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                        }
                    });
                    etStartDate.setText("");
                    etEndDate.setText("");
                    G1.clearCheck();
                    G2.clearCheck();
                } else {
                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}