package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import krescendo.sumit.kresc.myapplication.firebaceservice.EndPoints;
import krescendo.sumit.kresc.myapplication.firebaceservice.SharedPrefManager;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText et_fname, et_lname, et_emmail, et_pass, et_mobile,et_ref;
    LinearLayout lay;
    TextView txt_status;

    String fname;
    String lname;
    String email;
    String pass;
    String mobile;
    String ref="";
    String imie = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //           WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        //    Intent i1 = new Intent(getApplicationContext(), Home.class);
        //    startActivity(i1);
        if (new MyDb(getApplicationContext()).checkUser()) {

        } else {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        et_fname = (EditText) findViewById(R.id.txt_fname);
        et_lname = (EditText) findViewById(R.id.txt_lname);
        et_emmail = (EditText) findViewById(R.id.txt_email);
        et_mobile = (EditText) findViewById(R.id.txt_mobile);
        et_pass = (EditText) findViewById(R.id.txt_password);
        et_ref = (EditText) findViewById(R.id.txt_ref);
        txt_status = (TextView) findViewById(R.id.txt_loginstatus);
      /*  btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.requestFocus();
        lay=(LinearLayout)findViewById(R.id.layot);
        try{

            final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);
            lay.startAnimation(animation);
            final Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);
            et_pass.startAnimation(animation1);
            et_fname.startAnimation(animation1);
            et_lname.startAnimation(animation1);
            et_emmail.startAnimation(animation1);
            et_mobile.startAnimation(animation1);


        }catch(Exception e)
        {

        }
*/
        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //  imie = mngr.getDeviceId().toString().trim();

            if (android.os.Build.VERSION.RELEASE.startsWith("10")) {
                // Do something for lollipop and above versions
                imie = Settings.Secure.getString(Login.this.getContentResolver(), Settings.Secure.ANDROID_ID);

            } else {
                // do something for phones running an SDK before lollipop
                imie = mngr.getDeviceId().toString().trim();

            }

            if (android.os.Build.VERSION.SDK_INT >= 28) {
                // Do something for lollipop and above versions
                imie = Settings.Secure.getString(Login.this.getContentResolver(), Settings.Secure.ANDROID_ID);

            } else {
                // do something for phones running an SDK before lollipop

                imie = mngr.getDeviceId().toString().trim();
            }
        } catch (Exception e) {

        }
    }

    public void login(View v) {
        //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //  startActivity(i);
        if (new MyDb(getApplicationContext()).checkUser()) {
            if (isNetworkAvailable()) {
                fname = et_fname.getText().toString().trim();
                lname = et_lname.getText().toString().trim();
                email = et_emmail.getText().toString().trim();
                pass = et_pass.getText().toString().trim();
                mobile = et_mobile.getText().toString().trim();
                ref = et_ref.getText().toString().trim();
                if (fname.trim().equals("") || lname.trim().equals("") || email.trim().equals("") || pass.trim().equals("") || mobile.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields Carefully", Toast.LENGTH_LONG).show();
                } else {


                    new LongOperation().execute("WelCome");

                /*   txt_status.setText("Please Wait....");
                    String str = new JsonBilder().addLogin(fname, lname, email, mobile, pass).trim();
                   // Toast.makeText(getApplicationContext(), "Welcome " + str, Toast.LENGTH_SHORT).show();
                    if (str.equals("saved")) {
                        try {
                            Toast.makeText(getApplicationContext(), "Registered Successfully..", Toast.LENGTH_SHORT).show();
                            if (new MyDb(getApplicationContext()).adduser("1", fname, lname, email, mobile, pass)) {
                                Toast.makeText(getApplicationContext(), "Welcome " + fname, Toast.LENGTH_SHORT).show();
                            }
                            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(ii);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }  */
                }
            } else {
                Toast.makeText(getApplicationContext(), "Connection Not Found..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent iii = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(iii);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    String getLoginStatus() {
        String str = null;

        return str;
    }


    private class LongOperation extends AsyncTask<String, Void, Void> {


        String str;
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Login.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog.setMessage("Please Wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                str = new JsonBilder().addLogin(fname, lname, email, mobile, pass,imie,ref).trim();


            } catch (Exception e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                String sg[] = str.split(">>");
                Toast.makeText(getApplicationContext(), sg[0] + " -- " + sg[1], Toast.LENGTH_LONG).show();
                if (sg[0].trim().equals("saved")) {
                    try {
                        Toast.makeText(getApplicationContext(), "Registered Successfully..", Toast.LENGTH_SHORT).show();
                        if (new MyDb(getApplicationContext()).adduser(sg[1].trim(), fname, lname, email, mobile, pass)) {
                            Toast.makeText(getApplicationContext(), "Welcome " + fname, Toast.LENGTH_SHORT).show();
                            sendTokenToServer();

                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = et_emmail.getText().toString().trim();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(ii);
                            Toast.makeText(Login.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(ii);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
