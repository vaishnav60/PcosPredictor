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

public class ChangePassword extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_update_password = Common.SERVER_URL + "/App/changePassword.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    EditText et_oldpass, et_newpass, et_repass;

    String op,np,rp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        et_oldpass = (EditText)findViewById(R.id.txtOldPassword);
        et_newpass = (EditText)findViewById(R.id.txtNewPassword);
        et_repass = (EditText)findViewById(R.id.txtRetypePassword);

    }

    public void changepassword_click(View v) {
        op = et_oldpass.getText().toString().trim();
        np = et_newpass.getText().toString().trim();
        rp = et_repass.getText().toString().trim();


        if (op.length()<=0) {
            Toast.makeText(this, "Enter Old Password", Toast.LENGTH_SHORT).show();
            et_oldpass.requestFocus();
        }
        else if (np.length()<=0) {
            Toast.makeText(this, "Enter New Password", Toast.LENGTH_SHORT).show();
            et_oldpass.requestFocus();
        }
        else if (rp.length()<=0) {
            Toast.makeText(this, "Retype Password", Toast.LENGTH_SHORT).show();
            et_oldpass.requestFocus();
        }
        else if (op.equals(Common.password)==false) {
            Toast.makeText(this, "Invalid Old Password", Toast.LENGTH_SHORT).show();
            et_oldpass.requestFocus();
        }
        else if (op.equals(np)==true) {
            Toast.makeText(this, "Old and New Password cannot be same", Toast.LENGTH_SHORT).show();
            et_newpass.requestFocus();
        }
        else if (np.equals(rp)==false) {
            Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
            et_repass.requestFocus();
        }
        else {
            new updatePassword().execute();
        }
    }

    class updatePassword extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePassword.this);
            pDialog.setMessage("Updating Password.. Please Wait..");
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
            params.add(new BasicNameValuePair("Password", np));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_password, "POST", params);

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
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}