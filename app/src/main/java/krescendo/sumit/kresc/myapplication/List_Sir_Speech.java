package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class List_Sir_Speech extends AppCompatActivity {
    ListView lst;
    long ids[];
    int k = 0;
    MyDb db;
    String names[];//= {"Proper Utility of Time \n 3-04-2016 ", "wasting, spending & investing \n 24-04-2016 ", "Winning matters \n", "Speech 4", "Speech 5"};
    String goals[] = {">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">"};
    int imgs[] = {R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video};
    TextView uiUpdate;
    ProgressBar pb;
    LinearLayout ll;
    int kk=0;
    String filepath[],videopath[],vstatus[],vcost[],vid[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__sir__speech);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ll=(LinearLayout)findViewById(R.id.ll);
        ll.setVisibility(View.GONE);
        try {

            pb = (ProgressBar) findViewById(R.id.progressBar1);
            try {

                String serverURL = new JsonBilder().getHostName()+"selectSpeech.php";

                pb.setVisibility(View.VISIBLE);
                // Create Object and call AsyncTask execute Method
                new LongOperation().execute(serverURL);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            uiUpdate = (TextView) findViewById(R.id.output);
            final ImageButton GetServerData = (ImageButton) findViewById(R.id.myserver);

            GetServerData.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // Server Request URL
                    String serverURL = new JsonBilder().getHostName()+"selectSpeech.php";
                    pb.setVisibility(View.VISIBLE);
                    // Create Object and call AsyncTask execute Method
                    new LongOperation().execute(serverURL);

                }
            });

        } catch (Exception e) {

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(List_Sir_Speech.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //     Dialog.setMessage("Please Wait..");
            //   Dialog.show();
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

                //   uiUpdate.setText("Output : "+Content);
                Log.i("Details : ","\u00A9" +Content);
                loadFromServer(Content.toString().trim());

            }
        }

    }

    public void loadFromServer(String str) {
        if (isNetworkAvailable()) {
            try {
                JsonBilder jb = new JsonBilder();
                //   String str = jb.getAllSpeech().toString().trim();
                pb.setVisibility(View.GONE);
                JSONArray jArray = new JSONArray(str);
                uiUpdate.setText("Total : " + jArray.length());
                names = new String[jArray.length()];
                filepath=new String[jArray.length()];
                videopath=new String[jArray.length()];
                vstatus=new String[jArray.length()];
                vcost=new String[jArray.length()];
                vid=new String[jArray.length()];
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    names[i] = Html.fromHtml(json_data.getString("title").toString().trim()).toString().trim();
                    filepath[i] = json_data.getString("imgpath").toString().trim();
                    videopath[i ] = json_data.getString("filepath").toString().trim();
                    vstatus[i] = json_data.getString("vstatus").toString().trim();
                    vcost[i] = json_data.getString("cost").toString().trim();
                    vid[i] = json_data.getString("id").toString().trim();
                }


                lst = (ListView) findViewById(R.id.lst_sir_speech);
                Adapter adapter = new Adapter(List_Sir_Speech.this,names, goals, imgs,filepath,videopath,vstatus,vcost,vid);
                lst.setAdapter(adapter);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        Intent ii = new Intent(getApplicationContext(), AudioPlay.class);
                        ii.putExtra("vname", "" + names[position]);
                        startActivity(ii);

                    }
                });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
        if (k == 1) {
            Toast.makeText(getApplicationContext(), "New Speeches Added", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), "Video Buton Clicked", Toast.LENGTH_SHORT).show();

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
        Intent i=new Intent(getApplicationContext(),Topic_List.class);
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

}
