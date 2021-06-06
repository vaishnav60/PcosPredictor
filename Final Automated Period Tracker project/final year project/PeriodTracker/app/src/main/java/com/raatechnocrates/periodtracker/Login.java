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

public class Login extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_check_login = Common.SERVER_URL + "/App/checkLogin.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    EditText username_et;
    EditText password_et;

    Button login_btn;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_et = (EditText)findViewById(R.id.txtUsernameLP);
        password_et = (EditText)findViewById(R.id.txtPasswordLP);
        login_btn = (Button)findViewById(R.id.btnLoginLP);
    }

    public void login_click(View v)
    {
        username = username_et.getText().toString().trim();
        password = password_et.getText().toString().trim();

        if (username.length()<=0) {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            username_et.requestFocus();
        }
        else if(password.length()<=0) {
            Toast.makeText(this,"Enter Password", Toast.LENGTH_SHORT).show();
            password_et.requestFocus();
        }
        else {
            new CheckUser().execute();
        }
    }

    class CheckUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Verifying User.. Please Wait..");
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
            params.add(new BasicNameValuePair("Username", username));
            params.add(new BasicNameValuePair("Password", password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_check_login, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString("message");
                final String nm = json.getString("name");
                final String uid = json.getString("uid");
                if (success == 1) {
                    Common.username = username;
                    Common.name = nm;
                    Common.userid = uid; // This is User ID
                    Common.password = password;
                    Handler handler =  new Handler(getApplicationContext().getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
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

    public void register_click(View v)
    {
        Intent i=new Intent();
        i.setClass(Login.this,Register.class);
        startActivity(i);
    }
}