package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

public class EventRegistration extends AppCompatActivity {
    RadioButton rb1, rb2;
    RadioGroup rg;
    EditText et_names, et_nop;
    String name, mobile, dob, email, uid, nop, pdetails, eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
Intent ins=getIntent();
        Bundle b=ins.getExtras();
setTitle("Event Registration");
        eid = b.getString("eid");
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rg = (RadioGroup) findViewById(R.id.rg);
        et_names = (EditText) findViewById(R.id.et_details);
        et_nop = (EditText) findViewById(R.id.et_nofp);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb1) {
                    et_names.setEnabled(false);
                    et_nop.setEnabled(false);
                }
                if (i == R.id.rb2) {
                    et_names.setEnabled(true);
                    et_nop.setEnabled(true);
                }
            }
        });
        rb1.setChecked(true);


    }
    public final boolean isInternetOn() {


        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.rb1), "Check Internet !", Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    public void register(View vq) {
        try {
            if(isInternetOn()) {
                MyDb db=new MyDb(getApplicationContext());

                uid = db.getUserId();
                nop = et_nop.getText().toString().trim();
                pdetails = et_names.getText().toString().trim();
                new LongOperation().execute("Welcome");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(EventRegistration.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog.setMessage("Please Wait ");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method


                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //name, surname, username, dob, age, email, phone, mobile, education, city, state, country, pass

                nameValuePairs.add(new BasicNameValuePair("uid", uid));

                nameValuePairs.add(new BasicNameValuePair("nop", nop));
                nameValuePairs.add(new BasicNameValuePair("names", pdetails));
                nameValuePairs.add(new BasicNameValuePair("eid", eid));


             try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(new JsonBilder().getHostName() + "inserteventregistration.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    // Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                    // Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    // Toast.LENGTH_LONG).show();
                }


                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();

                    // Toast.makeText(getApplicationContext(), "Data is "+result,
                    // Toast.LENGTH_LONG).show();

                    // Log.e("pass 2", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());

                }

                //return result;


                //     String result = new JsonBilder().addUser(name, surname, username, dob, age, email, phone, mobile, gen, education, city, state, country, pass);

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
                //Toast.makeText(SignUp.this, "" + result, Toast.LENGTH_SHORT).show();
                Log.i("Result :", result);
                if (result.trim().equals("Saved")) {
                    // Intent i = new Intent(getApplicationContext(), Home.class);
                    // startActivity(i);
                    try {
                        Toast.makeText(getApplicationContext(), "Registered Successfully..", Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
