package krescendo.sumit.kresc.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class ListVideos extends AppCompatActivity {
    ListView lst;
    long ids[];
    TextView uiUpdate;
    int kk=0;
    LinearLayout ll;
    ProgressBar pb;
    String Urldata="";
int k=0;
    MyDb db;
    String names[];
    String filepath[],videopath[],vstatus[],vcost[],vid[],typeid[];
    //  String teams[] = {"Birthday", "Aniversary", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference","Zal"};
    String goals[] = {">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">"};
    int imgs[] = {R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video};


    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ll=(LinearLayout)findViewById(R.id.ll);
        ll.setVisibility(View.GONE);

     /*   if(kk==0)
        {
            ll.setVisibility(View.GONE);
            kk=1;
        }*/
        try {

            pb = (ProgressBar) findViewById(R.id.progressBar1);
            try {

                String serverURL = new JsonBilder().getHostName()+"selectVideo.php";

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
                    String serverURL = new JsonBilder().getHostName()+"selectVideo.php";
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
        private ProgressDialog Dialog = new ProgressDialog(ListVideos.this);


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
                typeid=new String[jArray.length()];
                //   ids = new long[jArray.length()];
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    names[i] = Html.fromHtml(json_data.getString("title").toString().trim()).toString().trim();
                    filepath[i] = json_data.getString("imgpath").toString().trim();
                    videopath[i ] = json_data.getString("filepath").toString().trim();
                    vstatus[i] = json_data.getString("vstatus").toString().trim();
                    vcost[i] = json_data.getString("cost").toString().trim();
                    vid[i] = json_data.getString("id").toString().trim();
                    typeid[i] = json_data.getString("typeid").toString().trim();
                    Urldata+="<iframe  id='myFrame' src='"+new JsonBilder().getHostName()+"video/"+json_data.getString("filepath").toString().trim()+"' frameborder='0' allowfullscreen></iframe>";


                }

                lst = (ListView) findViewById(R.id.lst_sir_video);
                Adapter_Videos adapter = new Adapter_Videos(ListVideos.this, names, goals, imgs,filepath,videopath,vstatus,vcost,vid,typeid);
                lst.setAdapter(adapter);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      //  Toast.makeText(ListVideos.this, "Item Clicked", Toast.LENGTH_SHORT).show();

                        Intent ii = new Intent(getApplicationContext(), VideoPlay.class);
                        ii.putExtra("vname",""+names[position]);
                        startActivity(ii);
                    }
                });


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No Internet Connection"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    {
        Toast.makeText(getApplicationContext(), "Video Buton Clicked", Toast.LENGTH_SHORT).show();
        finish();
        Intent i=new Intent(getApplicationContext(),ListVideos.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void audio(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Audio Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),List_Sir_Speech.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void lifetopics(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Lifetopics Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),LifeTopic.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void softskills(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Soft Skill Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),Topic_List.class);
        startActivity(i);
    }
    public void thoughts(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Thoughts Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),ListThought.class);
        startActivity(i);   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void pa(View v)
    {finish();
        Intent i=new Intent(getApplicationContext(),AnalysisForm.class);
        startActivity(i);   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        Toast.makeText(getApplicationContext(), "Perosnality Analysis Buton Clicked", Toast.LENGTH_SHORT).show();
    }


    public  void startDownload(String ll,String name) {
        String url = ll;
        new DownloadFileAsync().execute(url,name);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/Download/"+aurl[1]);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }


}
