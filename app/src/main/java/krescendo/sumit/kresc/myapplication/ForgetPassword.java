package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    WebView webView;
    String uid="";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setTitle("Forget password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        pb=(ProgressBar)findViewById(R.id.pb);
        webView = (WebView) findViewById(R.id.web);

        webView.setWebViewClient(new Browser_Home());
        webView.setWebChromeClient(new ChromeClient());
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
     uid=new MyDb(ForgetPassword.this).getUserId();
        loadWebSite();

    }

    private void loadWebSite() {
        Toast.makeText(ForgetPassword.this, "Url Loading is "+new JsonBilder().getHostName()+"forgetPassword.php?uid="+uid, Toast.LENGTH_SHORT).show();
        webView.loadUrl(new JsonBilder().getHostName()+"forgetPassword.php?uid="+uid);
    }

    private class Browser_Home extends WebViewClient {
        Browser_Home(){}

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
            Toast.makeText(ForgetPassword.this, "Error Occur", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent=new Intent(ForgetPassword.this,ErroActivity.class);
            startActivity(intent);
            // view.loadData(data, "text/html", "UTF-8");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }
        public void onProgressChanged(WebView view, int progress) {
            //Make the bar disappear after URL is loaded, and changes string to Loading...
            //  setTitle("Loading...");
            setProgress(progress * 100); //Make the bar disappear after URL is loaded
            pb.setProgress(progress);
            // Return the app name after finish loading
            if (progress == 100) {

                pb.setVisibility(View.GONE);
            }}


        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}


