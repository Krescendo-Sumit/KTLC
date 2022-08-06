package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


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
import java.io.IOException;
import java.util.Vector;

import static android.view.KeyCharacterMap.load;

public class ListThought extends AppCompatActivity {
    ListView lst;
    int k = 0;
    LinearLayout ll;
    int kk = 0;
    String names[];
    private ViewFlipper viewFlipper;
    private float lastX;
    ImageButton btn;
    ProgressBar pb;
    TextView txt;
    MyDb db;
    Vector v[];
    //  String teams[] = {"Birthday", "Aniversary", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference", "Wedding", "Wedding", "Valetine", "Engadegement", "Conference","Zal"};
    String goals[] = {">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">", ">"};
    int imgs[] = {R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video, R.drawable.video};
    String shartext = "";

    TextView txt_pat;
    Typeface custom_font;
    LinearLayout rl;
    String stt = "";
    int iid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_thought);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.setVisibility(View.GONE);
        db = new MyDb(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        txt = new TextView(this);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn = (ImageButton) findViewById(R.id.myserver);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_LONG).show();
                    String serverURL = new JsonBilder().getHostName() + "selectThought.php";
                    //  Toast.makeText(getApplicationContext(), "URL  "+serverURL, Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.VISIBLE);
                    // Create Object and call AsyncTask execute Method
                    new LongOperation().execute(serverURL);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_pat = (TextView) findViewById(R.id.txt_pat);
        v = db.viewAllThought();
        if(v.length>0)
        txt_pat.setText(v[0].elementAt(2).toString().trim());
        else
        txt_pat.setText("Please Refresh Your Thoughts.");
        rl = (LinearLayout) findViewById(R.id.rl);
        rl.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                //  showToast("Left swipe happened!");
                if (iid + 1 != v.length) {
                    iid++;


                }
                txt_pat.setText(v[iid].elementAt(2).toString().trim());


            }

            @Override
            public void onSwipeRight() {

                if (iid - 1 != -1) {
                    iid--;


                }
                txt_pat.setText(v[iid].elementAt(2).toString().trim());
                // showToast("Right swipe happened!");
            }

            @Override
            public void onSwipeTop() {
                //showToast("Top swipe happened!");
            }

            @Override
            public void onSwipeBottom() {
                //showToast("Bottom swipe happened!");
            }
        });

//loadListView();

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

    /*    public boolean onTouchEvent(MotionEvent touchevent) {
            switch (touchevent.getAction()) {
                // when user first touches the screen to swap
                case MotionEvent.ACTION_DOWN: {
                    lastX = touchevent.getX();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    float currentX = touchevent.getX();

                    // if left to right swipe on screen
                    if (lastX < currentX) {
                        // If no more View/Child to flip
                        if (viewFlipper.getDisplayedChild() == 0)
                            break;

                        // set the required Animation type to ViewFlipper
                        // The Next screen will come in form Left and current Screen will go OUT from Right
                        viewFlipper.setInAnimation(this, R.anim.in_from_left);
                        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                        // Show the next Screen
                        viewFlipper.showNext();
                    }

                    // if right to left swipe on screen
                    if (lastX > currentX) {
                        if (viewFlipper.getDisplayedChild() == 1)
                            break;
                        // set the required Animation type to ViewFlipper
                        // The Next screen will come in form Right and current Screen will go OUT from Left
                        viewFlipper.setInAnimation(this, R.anim.in_from_right);
                        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                        // Show The Previous Screen
                        viewFlipper.showPrevious();

                    }
                    break;
                }
            }
            return false;
        }*/
    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(ListThought.this);


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
                //   uiUpdate.setText("Output : "+Content);
                loadFromServer(Content.toString().trim());

            }
        }

    }

    public void goback(View v) {
        try {
//stopService();
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
        if (isNetworkAvailable()) {
            try {
                //  JsonBilder jb = new JsonBilder();
                //   Toast.makeText(getApplicationContext(), " pass 1", Toast.LENGTH_SHORT).show();
                //  String str = jb.getAllThought().toString().trim();
                //   Toast.makeText(getApplicationContext(), " pass 2", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getApplicationContext(), " From Web " + str, Toast.LENGTH_SHORT).show();
                JSONArray jArray = new JSONArray(str);
                //    Toast.makeText(getApplicationContext(), " pass 3", Toast.LENGTH_SHORT).show();
                //    names = new String[jArray.length()];
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    //    names[i] = json_data.getString("thought").toString().trim();
                    String title = Html.fromHtml(json_data.getString("title").toString().trim().replace("'", "''")).toString().trim();
                    String thought = Html.fromHtml(json_data.getString("thought").toString().trim().replace("'", "''")).toString().trim();
                    //     Toast.makeText(getApplicationContext(), " this retrive " + thought, Toast.LENGTH_SHORT).show();


                    if (db.addThought(json_data.getString("id").toString().trim(), title, thought)) {
                        k = 1;
                        //  Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } //else
                    //           Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_SHORT).show();

                }
                pb.setVisibility(View.GONE);
                loadListView();
            } catch (Exception e) {
                //  Toast.makeText(getApplicationContext(), "No Internet Connection Found " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
        if (k == 1) {
            Toast.makeText(getApplicationContext(), "New Thoughts Added", Toast.LENGTH_SHORT).show();
        }


    }

    public void loadListView() {
        try {

            //  startService();
            //      AsyncTaskRunner runner = new AsyncTaskRunner();
            //    String sleepTime = "10";
            //  runner.execute(sleepTime);


            //  viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);


            v = db.viewAllThought();
            // Toast.makeText(getApplicationContext(), "Length is "+v.length, Toast.LENGTH_SHORT).show();
            //  names = new String[v.length];
        /*for (int i = 0; i < v.length; i++) {

            txt.setText(Html.fromHtml(v[i].elementAt(2).toString().trim()));
            txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setGravity(Gravity.CENTER);
            txt.setTextSize(24);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;

            txt.setLayoutParams(params);
            //txt.setGravity(Gravity.CENTER);
            txt.setTypeface(null, Typeface.BOLD);
            txt.setBackgroundColor(Color.parseColor("#65FFFFFF"));
            viewFlipper.addView(txt);
            //    names[i] = v[i].elementAt(2).toString().trim();
        }

        //   lst = (ListView) findViewById(R.id.lst_th);
        //    Adapter adapter = new Adapter(getApplicationContext(), names, goals, imgs);
        //    lst.setAdapter(adapter);
        //     lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //         @Override
        //         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //             Toast.makeText(getApplicationContext(), "" + names[position], Toast.LENGTH_LONG).show();
        //   Intent ii = new Intent(getApplicationContext(), VideoPlay.class);
        //  startActivity(ii);

        //       }
        //    });*/
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void share(View v) {
     //   Toast.makeText(getApplicationContext(), "" + txt_pat.getText(), Toast.LENGTH_SHORT).show();

        String shareBody = txt_pat.getText().toString().trim()+"\n \n - Krishna The Life Coach";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share Quotes"));
    }

}
