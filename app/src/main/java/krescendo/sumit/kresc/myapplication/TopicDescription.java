package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;



public class TopicDescription extends AppCompatActivity {
    TextView topicname;
    String str1;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        topicname = (TextView) findViewById(R.id.txt_topicname);


        web=(WebView)findViewById(R.id.web);


        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            String str = b.get("id").toString();
            topicname.setText(str.trim());
            String fname=new MyDb(getApplicationContext()).getLifeTopicDescription(str.trim());
            web.setWebChromeClient(new WebChromeClient());
            if(fname.trim().equals(""))
            {
                web.loadUrl(new JsonBilder().getHostName()+"krishna/lifetopic/"+str.toString().trim());
            }else
            {
                web.loadUrl(new JsonBilder().getHostName()+"krishna/lifetopic/"+fname.toString().trim());
            }



           web.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                    setTitle("Loading...");
                    setProgress(progress * 100); //Make the bar disappear after URL is loaded

                    // Return the app name after finish loading

                }
            });

            web.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    view.loadUrl("file:///android_asset/simple.html");
                    Toast.makeText(TopicDescription.this," Check Internet Connectivity . ",Toast.LENGTH_LONG).show();
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



        }catch(Exception e)
        {
            Log.i("ErrorLifeDescription",""+e.getMessage());
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
}
