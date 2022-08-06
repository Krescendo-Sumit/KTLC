package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class SkillDescription extends AppCompatActivity {
    TextView topicname;
    String str1;
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        topicname = (TextView) findViewById(R.id.txt_skname);
        // topicdesc = (TextView) findViewById(R.id.txt_skdescrip);
        web=(WebView)findViewById(R.id.web);
        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            String str = b.get("id").toString();
            // Toast.makeText(getApplicationContext(), "Id = " + str, Toast.LENGTH_SHORT).show();


            web.getSettings().setJavaScriptEnabled(true);
            web.setWebChromeClient(new WebChromeClient());

            web.loadUrl(new JsonBilder().getHostName()+"krishna/Skills/"+str.toString().trim());
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
                    Toast.makeText(SkillDescription.this," Check Internet Connectivity . ",Toast.LENGTH_LONG).show();
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
