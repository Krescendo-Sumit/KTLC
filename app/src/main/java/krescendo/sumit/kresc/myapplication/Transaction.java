package krescendo.sumit.kresc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class Transaction extends AppCompatActivity {

    Intent intent;
    Bundle b;
    String cost;
    String vid;
    String iname;
    MyDb db;
    String email;
    String name;
    String mobile;
    String uid;
    String vname;
    String type;
    WebView web;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intent = getIntent();
        b = intent.getExtras();
        db = new MyDb(getApplicationContext());
        cost = "365";
        vid = db.getUserId();;
        vname = "test";
        iname = "test";
        type = "VA";

        email = db.getUserEmail();
        name = db.getUserName().replaceAll(" ", "-");
        mobile = db.getUserPhone();
        uid = db.getUserId();

        /*
    // old Data Transaction for the individual video purchase
         cost = b.getString("cost").trim();
        vid = b.getString("vid").trim();
        vname = b.getString("vname").trim();
        iname = b.getString("iname").trim();
        type = b.getString("type").trim();
        db = new MyDb(getApplicationContext());
        email = db.getUserEmail();
        name = db.getUserName().replaceAll(" ", "-");
        mobile = db.getUserPhone();
        uid = db.getUserId();

         */

        web = (WebView) findViewById(R.id.web);
   //     Toast.makeText(getApplicationContext(), "1" + type, Toast.LENGTH_SHORT).show();
     /*
      // Needed For Old Transactions
      try {
            if (type.equals("A")) {
            //    Toast.makeText(Transaction.this, " Audio" + type, Toast.LENGTH_SHORT).show();
                new LongOperation().execute(new JsonBilder().getHostName() + "selectPaidAudio.php?email=" + email + "&phone=" + mobile + "&vid=" + vid);

            } else if (type.equals("V")) {
            //    Toast.makeText(getApplicationContext(), " Video - " + type, Toast.LENGTH_SHORT).show();
                Log.i("Url ", new JsonBilder().getHostName() + "selectpaidvideo.php?email=" + email + "&phone=" + mobile + "&vid=" + vid);
                new LongOperation().execute(new JsonBilder().getHostName() + "selectpaidvideo.php?email=" + email + "&phone=" + mobile + "&vid=" + vid);
            } else {
                Toast.makeText(Transaction.this, "Type Not Found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.i("Error in Loading", e.getMessage());
        }*/

        loadpaymentgatway();
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

    public void loadpaymentgatway() {
        try {


            Log.i("url is ", new JsonBilder().getHostName() + "PayUMoney_form.php?" +
                    "firstname=" + name +
                    "&amount=" + cost +
                    "&email=" + email +
                    "&phone=" + mobile +
                    "&productinfo=" + vid +
                    "&surl=n" +
                    "&furl=s");

            web.getSettings().setJavaScriptEnabled(true);
            web.setWebChromeClient(new WebChromeClient());
            String url = new JsonBilder().getHostName() + "PayUMoney_form.php?" +
                    "firstname=" + name +
                    "&amount=" + cost +
                    "&email=" + email +
                    "&phone=" + mobile +
                    "&productinfo=A-" + vid +
                    "&surl=n" +
                    "&furl=s";
            if (type.equals("A")) {
                web.loadUrl(new JsonBilder().getHostName() + "PayUMoney_form.php?" +
                        "firstname=" + name +
                        "&amount=" + cost +
                        "&email=" + email +
                        "&phone=" + mobile +
                        "&productinfo=A-" + vid +
                        "&surl=n" +
                        "&furl=s");
                Log.i("ISIDE A", "YESS" + url);
            } else {
                web.loadUrl(new JsonBilder().getHostName() + "PayUMoney_form.php?" +
                        "firstname=" + name +
                        "&amount=" + cost +
                        "&email=" + email +
                        "&phone=" + mobile +
                        "&productinfo=V-" + vid +
                        "&surl=n" +
                        "&furl=s");
                Log.i("ISIDE V", "YESS" + url);
            }

            web.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                    setTitle("Loading...");
                    setProgress(progress * 100); //Make the bar disappear after URL is loaded

                    // Return the app name after finish loading
                    if (progress == 100)
                        setTitle("Payment Process");
                }
            });

            web.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    if (url.contains("yeszalpayment")) {
                        if (type.equals("A")) {
                            finish();
                            Intent i = new Intent(getApplicationContext(), List_Sir_Speech.class);
                            startActivity(i);
                        } else {
                            finish();
                            Intent i = new Intent(getApplicationContext(), ListVideos.class);
                            startActivity(i);
                        }
                        new MyDb(Transaction.this).UpdatePay("Yes");
                    } else {
                        view.loadUrl(url);
                    }
                    return false; // then it is not handled by default action
                }
            });

            web.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        WebView webView = (WebView) v;

                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack()) {
                                    webView.goBack();
                                    return true;
                                }
                                break;
                        }
                    }

                    return false;
                }
            });


        } catch (Exception e) {
            Toast.makeText(Transaction.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error For Loading", e.getMessage());
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Transaction.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            //Toast.makeText(getApplicationContext(), "onprepare", Toast.LENGTH_SHORT).show();
            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog.setMessage("Please Wait..");//
            Dialog.show();
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
            Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);
                //  Toast.makeText(getApplicationContext(), "1"+Error, Toast.LENGTH_SHORT).show();

            } else {

                //   uiUpdate.setText("Output : "+Content);
                // Toast.makeText(getApplicationContext(), "1"+Content, Toast.LENGTH_SHORT).show();
                loadFromServer(Content.toString().trim());

            }
        }

    }

    public void loadFromServer(String str) {

        try {
            JsonBilder jb = new JsonBilder();
            //   String str = jb.getAllSpeech().toString().trim();

            JSONArray jArray = new JSONArray(str);
            if (type.equals("A")) {
                if (jArray.length() > 0) {
                    finish();
                    Intent i = new Intent(getApplicationContext(), AudioPlay.class);
                    i.putExtra("vname", vname);
                    i.putExtra("iname", iname);
                    i.putExtra("aname", "Krishna The Life Coach");
                    startActivity(i);
                } else {
                    loadpaymentgatway();
                }
            } else {
                if (jArray.length() > 0) {
                    finish();
                    Intent i = new Intent(getApplicationContext(), VideoPlay.class);
                    i.putExtra("vname", vname);
                    i.putExtra("iname", iname);
                    i.putExtra("aname", "Krishna The Life Coach");
                    startActivity(i);
                } else {
                    loadpaymentgatway();
                }

            }
        } catch (Exception e) {
            Toast.makeText(Transaction.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            loadpaymentgatway();
        }

    }


}
