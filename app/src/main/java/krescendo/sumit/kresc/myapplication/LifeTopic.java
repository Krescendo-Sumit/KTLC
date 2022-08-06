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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import java.util.Vector;

public class LifeTopic extends AppCompatActivity {
    ListView lst;
    int k = 0;
    LinearLayout ll;
    int kk = 0;
    String str[] = {"संधी व संकट", "तयारी पहिल्या नौकरीची", "परीक्षा कोणाची विद्यार्थ्याची का पालकांची?", "स्वतःचा शोध", "योग्य अयोग्याची निवड"};
    String strfile[] = {"1.html", "2.html", "3.html", "4.html", "5.html"};
    String names[];
    ImageButton btn;
    ProgressBar pb;
    ArrayAdapter adapterc;
    MyDb db;
    long ids[];// = {"Chasing Your Dreams in 10 Minutes a Day", "How to Use Your Subconscious to Change Your Life", "Rediscovering Your Inner Teenager"};
    //  String teams[] = {"Birthday", "Aniversary", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference","Zal"};
    String goals[] = {">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">"};
    int imgs[] = {R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_topic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.setVisibility(View.GONE);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn = (ImageButton) findViewById(R.id.myserver);
        db = new MyDb(getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_LONG).show();
                    String serverURL = new JsonBilder().getHostName() + "selectLifeTopic.php";
                    //  Toast.makeText(getApplicationContext(), "URL  "+serverURL, Toast.LENGTH_LONG).show();
                    //  pb.setVisibility(View.VISIBLE);
                    // Create Object and call AsyncTask execute Method
                    new LongOperation().execute(serverURL);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


       // loadDataToList();

        try {
            // Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_LONG).show();
            String serverURL = new JsonBilder().getHostName() + "selectLifeTopic.php";
            //  Toast.makeText(getApplicationContext(), "URL  "+serverURL, Toast.LENGTH_LONG).show();
            //  pb.setVisibility(View.VISIBLE);
            // Create Object and call AsyncTask execute Method
            new LongOperation().execute(serverURL);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LifeTopic.this);


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
                try {
                    //   uiUpdate.setText("Output : "+Content);
                    Content = Content.substring(Content.indexOf("["), Content.length());
                    Log.i("Contents are", "" + Content.trim());
                    loadFromServer(Content.toString().trim());
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"No Life Topics Found.",Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    void loadDataToList() {
        try {
            lst = (ListView) findViewById(R.id.lst_life_topics);
            //     Adapter adapter1 = new Adapter(getApplicationContext(), str, goals, imgs);
            //   lst.setAdapter(adapter1);

            db = new MyDb(getApplicationContext());

            int k = 0;
            // loadFromServer();
            Vector v[] = db.viewAllLifeTopic();
            //Toast.makeText(getApplicationContext(), "Length is "+v.length, Toast.LENGTH_SHORT).show();
            names = new String[v.length + 5];
            for (int i = 0; i < str.length; i++) {
                names[k] = str[i];
                k++;
            }
            if (v.length != 0) {
                for (int i = 0; i < v.length; i++) {
                    System.out.println(v[i].toString());
                    names[k] = Html.fromHtml(v[i].elementAt(1).toString().trim()).toString().trim();
                    k++;

                    //    //   Toast.makeText(getApplicationContext(), "Toic desce "+v[i].elementAt(2), Toast.LENGTH_SHORT).show();
                }
            /*    k = k + 1;
                for (int i = 0; i < str.length; i++) {
                    names[k] = str[i];
                    k++;
                }*/
            } else {

               /* for (int i = 0; i < str.length; i++) {
                    names[k] = str[i];
                    k++;
                }*/
            }
            adapterc=new ArrayAdapter(LifeTopic.this,R.layout.listitem,names);
            lst.setAdapter(adapterc);
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //   Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_SHORT).show();
                    String fname="";
                    Intent ii = new Intent(getApplicationContext(), TopicDescription.class);
                    if(position<5)
                    {
                        fname=strfile[position];
                    }
                    else
                    {
                        fname=names[position];

                    }
                    ii.putExtra("id", " " + fname);
                    startActivity(ii);
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    public void loadFromServer(String str) {

        JsonBilder jb = new JsonBilder();
     //   str = jb.getAllLifeTopic().toString().trim();
    //    Log.i("From JSON Builder",""+str);
        if (isNetworkAvailable()) {
            try {
                boolean b =db.removeAllLifeTopic();
                Toast.makeText(this, ""+b, Toast.LENGTH_SHORT).show();
                JSONArray jArray = new JSONArray(str);
                names = new String[jArray.length()];
                ids = new long[jArray.length()];
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    names[i] = json_data.getString("title").toString().trim();
                    ids[i] = Integer.parseInt(json_data.getString("id").toString().trim());

                    String title = json_data.getString("title").toString().trim().replace("'", "''");
                    title.replace("'", "''");
                    String desc = json_data.getString("descr").toString().trim().replace("'", "''");
                    desc.replace("'", "''");
                    String dd = json_data.getString("date").toString().trim();
                    String filename = json_data.getString("filename").toString().trim();
                    //    Toast.makeText(getApplicationContext(), "Title" + title, Toast.LENGTH_SHORT).show();

                    if (db.addLifeTopic(json_data.getString("id").toString().trim(), title, desc, dd,filename)) {
                        // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        k = 1;
                    }
                    //   Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_SHORT).show();


                }
                loadDataToList();


            } catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "No Internet Connection"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
        if (k == 1) {
            Toast.makeText(getApplicationContext(), "New Life Topic Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void colapse(View v) {

        if (kk == 0) {
            ll.setVisibility(View.VISIBLE);
            final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bounce);
            ll.startAnimation(animation);
            kk = 1;
        } else {
            ll.setAnimation(null);
            ll.setVisibility(View.GONE);
            kk = 0;
        }

    }

    public void video(View v) {
        Toast.makeText(getApplicationContext(), "Video Buton Clicked", Toast.LENGTH_SHORT).show();
        finish();
        Intent i = new Intent(getApplicationContext(), ListVideos.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }

    public void audio(View v) {
        finish();
        Toast.makeText(getApplicationContext(), "Audio Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), List_Sir_Speech.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }

    public void lifetopics(View v) {
        finish();
        Toast.makeText(getApplicationContext(), "Lifetopics Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), LifeTopic.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void softskills(View v) {
        finish();
        Toast.makeText(getApplicationContext(), "Soft Skill Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Topic_List.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void thoughts(View v) {
        finish();
        Toast.makeText(getApplicationContext(), "Thoughts Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), ListThought.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void pa(View v) {
        finish();
        Intent i = new Intent(getApplicationContext(), AnalysisForm.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        Toast.makeText(getApplicationContext(), "Perosnality Analysis Buton Clicked", Toast.LENGTH_SHORT).show();
    }


}
