package krescendo.sumit.kresc.myapplication;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;




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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AudioPlay extends AppCompatActivity {
    TextView txt_count;
    String vname;
    String aname;
    String AUDIO_PATH =
            "";
    private MediaPlayer mediaPlayer;
    private int playbackPosition = 0;
    Timer timer;
    TimerTask timerTask;
    WebView web;
    Handler handler;

    SeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_audio_play);
        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        TextView txt=(TextView)findViewById(R.id.txt_name);
        txt_count=(TextView)findViewById(R.id.txt_coundwon);
        vname = b.getString("vname").trim();
        aname = b.getString("aname").trim();
        txt.setText(aname.toString().trim());
        Uri videoUri = Uri.parse(new JsonBilder().getHostName()+"audio/"+vname);
        AUDIO_PATH=new JsonBilder().getHostName()+"audio/"+vname;
         web=(WebView)findViewById(R.id.web);
        String data = "<html><body><audio controls>\n" +
                "  <source src=\""+AUDIO_PATH+"\" type=\"audio/mpeg\">\n" +
                "  Your browser does not support the audio tag.\n" +
                "</audio></body></html>";

        web.loadData(data, "text/html", "UTF-8");


        sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 10);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        try {
            //  playAudio(AUDIO_PATH);
            if(mediaPlayer!=null) {
                sb.setMax(mediaPlayer.getDuration());
            }
            // txt.setText("" + mediaPlayer.getDuration());
            //  playLocalAudio();
            //        playLocalAudio_UsingDescriptor();
            handler=new Handler();
            timer=new Timer();


            timerTask = new TimerTask() {
                public void run() {

                    //use a handler to run a toast that shows the current timestamp
                    handler.post(new Runnable() {
                        public void run() {
                            if(mediaPlayer!=null) {
                                sb.setProgress(((mediaPlayer.getCurrentPosition() / 1000) / 60));
                                float gg = (((float) (mediaPlayer.getCurrentPosition()) / 1000) / 60);
                                Log.i("------------------ >", "" + TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()) + ":" + (TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) % 60) + "/" + (TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.getDuration()))));
                                txt_count.setText(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()) + ":" + (TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) % 60) + "/" + (TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.getDuration())))+" Min");
                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 1000, 1000);
            timerTask.run();
        } catch (Exception e) {
            Toast.makeText(this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Error is",e.getMessage());
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void playa(View v) {
        try {
            playAudio(AUDIO_PATH);

            // new LongOperation().execute("");

            //sb.setMax(mediaPlayer.getDuration());

            // txt.setText("" + mediaPlayer.getDuration());
            //  playLocalAudio();
            //        playLocalAudio_UsingDescriptor();
        } catch (Exception e) {

        }
    }

    public void resumea(View v) {
        try {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(playbackPosition);
                mediaPlayer.start();
            }
        } catch (Exception e) {

        }
    }

    public void stopa(View v) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                playbackPosition = 0;
            }
        } catch (Exception e) {

        }
    }

    public void pausea(View v) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                playbackPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }
        } catch (Exception e) {

        }
    }

    private void playAudio(String url) throws Exception {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        ///mediaPlayer.prepareAsync();

        mediaPlayer.prepare();
        mediaPlayer.start();

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();

        timerTask.cancel();
        timer.cancel();
        web.reload();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        web.reload();
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {


        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(AudioPlay.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(AUDIO_PATH);

            }catch(Exception e)
            {

            }
            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog.setMessage("Please Wait..");
            Dialog.show();
            //pb.setVisibility(View.VISIBLE);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {
                //playAudio(AUDIO_PATH);
                mediaPlayer.prepareAsync();



            } catch (Exception e) {
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
                Dialog.dismiss();
                Log.i("Error","----------->"+Error);
            } else {

                try {

                    mediaPlayer.start();
                    //   uiUpdate.setText("Output : "+Content);
                    // Log.i("Contents are", "" + Content.trim());
                    Dialog.dismiss();

                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"No Life Topics Found",Toast.LENGTH_LONG).show();
                    Dialog.dismiss();
                }

            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
