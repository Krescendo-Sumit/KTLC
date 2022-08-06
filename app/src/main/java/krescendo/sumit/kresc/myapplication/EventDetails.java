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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventDetails extends AppCompatActivity {
    TextView txt_title, txt_date, txt_details, txt_time, txt_trainer, txt_venue;
    RatingBar rb;
    String eid,rate,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        Bundle b = intent.getExtras();
        rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setNumStars(5);
        try {

            setTitle("Past Event");
            JSONObject jo = new JSONObject(b.getString("json"));
            txt_title = (TextView) findViewById(R.id.txt_title);
            txt_date = (TextView) findViewById(R.id.txt_date);
            txt_details = (TextView) findViewById(R.id.txt_details);
            txt_time = (TextView) findViewById(R.id.txt_time);
            txt_trainer = (TextView) findViewById(R.id.txt_trainer);
            txt_venue = (TextView) findViewById(R.id.txt_venue);
            eid=jo.getString("id").trim();
            txt_title.setText(jo.getString("title"));
            txt_date.setText(   "Date    : "+jo.getString("edate"));
            txt_details.setText(""+jo.getString("edetails"));
            txt_time.setText(   "Time    : "+jo.getString("time")+" - "+jo.getString("etime"));
            txt_trainer.setText("Trainer : \n"+jo.getString("trainer"));
            txt_venue.setText(  "Venue   : "+jo.getString("venue"));


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
    public void show(View v)
    {
        try
        {
            if(isInternetOn()) {
               Intent intent = new Intent(getApplicationContext(), Gallery.class);
              intent.putExtra("eid", eid);
               startActivity(intent);
            }
        }catch(Exception e)
        {

        }
    }
    public void rate(View v)
    {
        try
        {if(isInternetOn()) {
            uid = new MyDb(getApplicationContext()).getUserId().trim();
            rate = "" + rb.getRating();
            new LongOperation().execute("Welcome");
        }}catch(Exception e)
        {
            Toast.makeText(this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void feedback(View v)
    {
        try
        {if(isInternetOn()) {
        //    Intent intent = new Intent(getApplicationContext(), FeedbackForEvent.class);
         //   intent.putExtra("eid", eid);
         //   startActivity(intent);
        }
        }catch(Exception e)
        {

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
                    .make(findViewById(R.id.txt_date), "Check Internet !", Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(EventDetails.this);


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

                nameValuePairs.add(new BasicNameValuePair("eid", eid));
                nameValuePairs.add(new BasicNameValuePair("rate", rate));


                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(new JsonBilder().getHostName() + "insertrating.php");
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
                        Toast.makeText(getApplicationContext(), "Thanks you....", Toast.LENGTH_SHORT).show();
                        //finish();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something wrong Please try Again.", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }

    }

}
