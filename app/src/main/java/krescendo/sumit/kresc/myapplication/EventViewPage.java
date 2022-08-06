package krescendo.sumit.kresc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class EventViewPage extends AppCompatActivity {
String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_page);

        url=getIntent().getExtras().getString("url").trim();
        WebView web = (WebView) findViewById(R.id.web);


        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setDisplayZoomControls(false);
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(url);

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
                Toast.makeText(EventViewPage.this, ""+url, Toast.LENGTH_SHORT).show();
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/slideimage.html");
                Toast.makeText(EventViewPage.this, " Check Internet Connectivity . ", Toast.LENGTH_LONG).show();
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
    }
}