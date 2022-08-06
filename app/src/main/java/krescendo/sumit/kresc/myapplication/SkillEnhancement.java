package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SkillEnhancement extends AppCompatActivity {
    ListView lst;
    LinearLayout ll;
    String strtitle[]=null;
    String strfname[]=null;
    int kk=0;
    ProgressBar pb;
    // String names[] = {"Dealing with Criticism", "Decision Making", "Leadership Skills", "Positive Thinking", "Time Management Skills"};
    //  String teams[] = {"Birthday", "Aniversary", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference","Zal"};
    String goals[] = {">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">"};
    int imgs[] = {R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p, R.drawable.p};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_enhancement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ll=(LinearLayout)findViewById(R.id.ll);
        ll.setVisibility(View.GONE);
        pb=(ProgressBar)findViewById(R.id.pb);
        try {
            lst = (ListView) findViewById(R.id.lst_topic);
            new LongOperation().execute(new JsonBilder().getHostName()+"selectSkillEnhancement.php");
        } catch (Exception e) {
            Log.i("Error is ",""+e.getMessage());
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

    public void goback(View v) {
        try {

            this.finish();
        } catch (Exception e) {

        }

    }
    public void colapse(View v)
    {

        if(kk==0)
        {
            ll.setVisibility(View.VISIBLE);


            final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bounce);

            ll.startAnimation(animation);

            kk=1;
        }
        else
        {
            ll.setAnimation(null);
            ll.setVisibility(View.GONE);
            kk=0;
        }

    }

    public void video(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Video Button Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),ListVideos.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void audio(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Audio Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),List_Sir_Speech.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void lifetopics(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Lifetopics Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),LifeTopic.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void softskills(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Soft Skill Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),SkillEnhancement.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void thoughts(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Thoughts Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),ListThought.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void pa(View v)
    {finish();
        Intent i=new Intent(getApplicationContext(),AnalysisForm.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        Toast.makeText(getApplicationContext(), "Perosnality Analysis Buton Clicked", Toast.LENGTH_SHORT).show();
    }



    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SkillEnhancement.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //     Dialog.setMessage("Please Wait..");
            //   Dialog.show();
            pb.setVisibility(View.VISIBLE);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            // Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                pb.setVisibility(View.GONE);
                //uiUpdate.setText("Output : "+Content);
                Log.i("Contents are",""+Content);
                loadFromServer(Content.toString().trim());

            }
        }

    }

    public void loadFromServer(String str)
    {

        try{

            JSONArray jsonArray=new JSONArray(str.trim());
            strtitle=new String[jsonArray.length()];
            strfname=new String[jsonArray.length()];
            JSONObject jsonObject=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject=jsonArray.getJSONObject(i);
                strtitle[i]= Html.fromHtml(jsonObject.getString("title")).toString().trim();
                strfname[i]=jsonObject.getString("fname");

            }

            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.listitem,strtitle);
            lst.setAdapter(adapter);

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //   Toast.makeText(SkillEnhancement.this, ""+strfname[i], Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(getApplicationContext(), SkillDescription.class);
                    ii.putExtra("id", "" + strfname[i]);
                    startActivity(ii);
                }
            });


        }catch(Exception e)
        {

        }
    }



}
