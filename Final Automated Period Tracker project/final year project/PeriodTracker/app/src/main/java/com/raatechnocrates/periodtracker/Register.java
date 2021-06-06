package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_create_user = Common.SERVER_URL + "/App/createUser.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    EditText name_et;
    EditText mobile_et;
    EditText password_et;
    EditText re_password_et;

    String strName;
    String strMobile;
    String strPassword;
    String strRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_et = (EditText)findViewById(R.id.txtName_R);
        mobile_et = (EditText)findViewById(R.id.txtMobile_R);
        password_et = (EditText)findViewById(R.id.txtPassword_R);
        re_password_et = (EditText)findViewById(R.id.txtRetypePassword_R);
    }

    public void signup_click(View v)
    {
        strName = name_et.getText().toString().trim();
        strMobile = mobile_et.getText().toString().trim();
        strPassword = password_et.getText().toString().trim();
        strRePassword = re_password_et.getText().toString().trim();

        if (strName.length()<=0) {
            Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show();
            name_et.requestFocus();
        }
        else if(strMobile.length()<=0) {
            Toast.makeText(this,"Enter your Mobile Number", Toast.LENGTH_SHORT).show();
            mobile_et.requestFocus();
        }
        else if(strPassword.length()<=0) {
            Toast.makeText(this,"Enter Password", Toast.LENGTH_SHORT).show();
            password_et.requestFocus();
        }
        else if(strRePassword.length()<=0) {
            Toast.makeText(this,"Retype Password", Toast.LENGTH_SHORT).show();
            re_password_et.requestFocus();
        }
        else if(strPassword.equals(strRePassword)==false) {
            Toast.makeText(this,"Password mismatch", Toast.LENGTH_SHORT).show();
            re_password_et.requestFocus();
        }
        else {
            new createUser().execute();
        }
    }

    class createUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User.. Please Wait..");
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
            params.add(new BasicNameValuePair("Name", strName));
            params.add(new BasicNameValuePair("MobileNo", strMobile));
            params.add(new BasicNameValuePair("Password", strPassword));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user, "POST", params);

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
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();
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

    public void back_to_login(View v)
    {
        Intent i=new Intent();
        i.setClass(Register.this,Login.class);
        startActivity(i);
    }
}