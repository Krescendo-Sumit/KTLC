package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class UpcommingEventsDetails extends AppCompatActivity {
    TextView txt_title, txt_date, txt_details, txt_time, txt_trainer, txt_charges, txt_venue;
String eid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomming_events_details);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        Bundle b = intent.getExtras();
setTitle("Upcoming Event");
        try {

            JSONObject jo = new JSONObject(b.getString("json"));
            txt_title = (TextView) findViewById(R.id.txt_title);
            txt_date = (TextView) findViewById(R.id.txt_date);
            txt_details = (TextView) findViewById(R.id.txt_details);
            txt_time = (TextView) findViewById(R.id.txt_time);
            txt_trainer = (TextView) findViewById(R.id.txt_trainer);
            txt_charges = (TextView) findViewById(R.id.txt_charges);
            txt_venue = (TextView) findViewById(R.id.txt_venue);
            eid=jo.getString("id");

            txt_title.setText(jo.getString("title"));
            txt_date.setText("Date    : " + jo.getString("edate"));
            txt_details.setText("" + jo.getString("edetails"));
            txt_time.setText("Time    : " + jo.getString("time"));
            txt_trainer.setText("Trainer : " + jo.getString("trainer"));
            txt_charges.setText("Charges : " + jo.getString("charges"));
            txt_venue.setText("Venue   : " + jo.getString("venue"));


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void register(View v) {
        try {

           Intent intent = new Intent(getApplicationContext(), EventRegistration.class);
            intent.putExtra("eid",eid.trim());
            startActivity(intent);

        } catch (Exception e) {

        }
    }
}
