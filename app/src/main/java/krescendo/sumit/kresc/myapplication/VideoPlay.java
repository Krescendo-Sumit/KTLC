package krescendo.sumit.kresc.myapplication;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;




import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class VideoPlay extends AppCompatActivity {

    String vname;
    String aname;

    public static String url = "";
    private VideoView videoView = null;
    private ProgressBar prog = null;
    private Context ctx = null;
    private MediaController mediaController = null;
    int curposition;

    WebView webView;
    String uid="";
    ProgressBar pb;
    String iname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Video Learning");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_play);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        vname = b.getString("vname").trim();
        iname=b.getString("iname").trim();
        url=new JsonBilder().getHostName()+"videoplay.php?fname="+vname+"&iname="+iname;

        pb=(ProgressBar)findViewById(R.id.pb);
        webView = (WebView) findViewById(R.id.web);

        webView.setWebViewClient(new Browser_Home());
        webView.setWebChromeClient(new ChromeClient());
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        loadWebSite();

    }

    private void loadWebSite() {
Log.i("Video URL IS",url);
        webView.loadUrl(url);
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
            Toast.makeText(VideoPlay.this, "Error Occur", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent=new Intent(VideoPlay.this,ErroActivity.class);
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





/*
public class VideoPlay extends AppCompatActivity {
    FullscreenVideoLayout videoLayout;
    String vname;
    String aname;

    public static String url = "";
    private VideoView videoView = null;
    private ProgressBar prog = null;
    private Context ctx = null;
    private MediaController mediaController = null;
int curposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_play);
        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        vname = b.getString("vname").trim();
      //  TextView txt=(TextView)findViewById(R.id.txt_name);

        aname = b.getString("aname").trim();
       // txt.setText(aname.toString().trim());
*/
/*
        videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);
        videoLayout.setActivity(this);
       // Toast.makeText(VideoPlay.this, ""+vname, Toast.LENGTH_SHORT).show();
        Toast.makeText(VideoPlay.this,new JsonBilder().getHostName()+"video/"+vname, Toast.LENGTH_SHORT).show();
        Uri videoUri = Uri.parse(new JsonBilder().getHostName()+"video/"+vname);
Log.i("URL",new JsonBilder().getHostName()+"video/"+vname);
        try {
            videoLayout.setVideoURI(videoUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
 *//*

        ctx = VideoPlay.this;
        url=new JsonBilder().getHostName()+"video/"+vname;
        prog = (ProgressBar) findViewById(R.id.prog);
        videoView = (VideoView) findViewById(R.id.video);
        Uri video = Uri.parse(url);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                Toast.makeText(ctx, "Error occured", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer arg0) {
                prog.setVisibility(View.GONE);
                videoView.start();
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //videoLayout.pause();
        try {
            curposition=videoView.getDuration();
            videoView.pause();
        }catch(Exception e)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            videoView.seekTo(curposition);
            videoView.resume();
        }catch(Exception e)
        {

        }
        //videoLayout.reset();
    }
    @Override
    protected void onDestroy() {
        try {
            videoView.stopPlayback();
        } catch (Exception e) {
            //
        }
        super.onDestroy();
    }
}*/
