package krescendo.sumit.kresc.myapplication;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import static android.content.Context.DOWNLOAD_SERVICE;

public class AudioPlayOnline extends AppCompatActivity {
WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play_online);

        web=(WebView)findViewById(R.id.web);


        web.loadUrl("http://192.168.0.3/krishna/audioplay.php");


    }
}
