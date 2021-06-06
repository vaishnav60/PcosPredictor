package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static String url_update_user = Common.SERVER_URL + "/App/updateUser.php";
    private static String url_fetch_user = Common.SERVER_URL + "/App/getUserDetails.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    EditText name_et;
    EditText mobile_et;
    EditText age_et;
    EditText weight_et;
    EditText height_et;
    EditText bmi_et;
    EditText marriagestatus_et;
    EditText noofchildren_et;

    String strName;
    String strMobile;
    String strAge;
    String strWeight;
    String strHeight;
    String strBMI;
    String strMarriageStatus;
    String strNoOfChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name_et = (EditText)findViewById(R.id.txtName_MP);
        mobile_et = (EditText)findViewById(R.id.txtMobile_MP);
        age_et = (EditText)findViewById(R.id.txtAge_MP);
        weight_et = (EditText)findViewById(R.id.txtWeight_MP);
        height_et = (EditText)findViewById(R.id.txtHeight_MP);
        bmi_et = (EditText)findViewById(R.id.txtBMI_MP);
        marriagestatus_et = (EditText)findViewById(R.id.txtMarriageStatus_MP);
        noofchildren_et = (EditText)findViewById(R.id.txtNoOfChildren_MP);

        name_et.setText(Common.name);
        mobile_et.setText(Common.username);

        strMobile = Common.username;

        new fetchUserDetails().execute();
    }

    public void save_click(View v)
    {
        strName = name_et.getText().toString().trim();
        strMobile = mobile_et.getText().toString().trim();

        if (strName.length()<=0) {
            Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show();
            name_et.requestFocus();
        }
        else if(strMobile.length()<=0) {
            Toast.makeText(this,"Enter your Mobile Number", Toast.LENGTH_SHORT).show();
            mobile_et.requestFocus();
        }
        else {
            new updateUser().execute();
        }
    }

    class fetchUserDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyProfile.this);
            pDialog.setMessage("Fetching your record.. Please Wait..");
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
            params.add(new BasicNameValuePair("MobileNo", strMobile));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_fetch_user, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                final String vName = json.getString("name");
                final String vAge = json.getString("Age");
                final String vWeight = json.getString("Weight");
                final String vHeight = json.getString("Height");
                final String vBMI = json.getString("BMI");
                final String vMarriageStatus = json.getString("MarriageStatus");
                final String vNoOfChildren = json.getString("NoOfChildren");
                if (success == 1) {
                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            name_et.setText(vName);
                            age_et.setText(vAge);
                            weight_et.setText(vWeight);
                            height_et.setText(vHeight);
                            bmi_et.setText(vBMI);
                            marriagestatus_et.setText(vMarriageStatus);
                            noofchildren_et.setText(vNoOfChildren);
                        }
                    });
                } else {
                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            name_et.setText(Common.name);
                            age_et.setText("");
                            weight_et.setText("");
                            height_et.setText("");
                            bmi_et.setText("");
                            marriagestatus_et.setText("");
                            noofchildren_et.setText("");
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

    class updateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyProfile.this);
            pDialog.setMessage("Saving your records.. Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            strAge = age_et.getText().toString().trim();
            strWeight = weight_et.getText().toString().trim();
            strHeight = height_et.getText().toString().trim();
            strBMI = bmi_et.getText().toString().trim();
            strMarriageStatus = marriagestatus_et.getText().toString().trim();
            strNoOfChildren = noofchildren_et.getText().toString().trim();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Name", strName));
            params.add(new BasicNameValuePair("MobileNo", strMobile));
            params.add(new BasicNameValuePair("Age", strAge));
            params.add(new BasicNameValuePair("Weight", strWeight));
            params.add(new BasicNameValuePair("Height", strHeight));
            params.add(new BasicNameValuePair("BMI", strBMI));
            params.add(new BasicNameValuePair("MarriageStatus", strMarriageStatus));
            params.add(new BasicNameValuePair("NoOfChildren", strNoOfChildren));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_user, "POST", params);

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
                            Common.name = strName;
                            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                        }
                    });
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