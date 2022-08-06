package krescendo.sumit.kresc.myapplication;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Button btn1, btn2, btn_persnal;
    //ImageButton btn_speech, btn_video,btn_life,btn_thought,btn_topic,btn_skill;
    static final String FTP_HOST = "";

    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "";

    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "";
    DrawerLayout drawer;
    TextView txt_name, txtheadername, txthederemail;
    ImageView img_dp;
    String uid = "0";
    String PrimaryUrl = "";
    final String uploadFilePath = "/mnt/sdcard/data/";
    final String uploadFileName = "main.db";
    int REQUEST_CODE_LOAD_IMAGE = 100;
    String filepath = "";
    File ff;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    private ProgressDialog Dialog;//= new ProgressDialog(AddProductImges.this);
    File upfile = null;
    // ProgressBar pb;
    LinearLayout rl, rl1;
    String paystatus = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //  setSupportActionBar(toolbar);
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            rl = (LinearLayout) findViewById(R.id.rll);
            rl1 = (LinearLayout) findViewById(R.id.rll1);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
/*
            btn_speech=(ImageButton)findViewById(R.id.btn_speech);
            btn_life=(ImageButton)findViewById(R.id.btn_life);
            btn_thought=(ImageButton)findViewById(R.id.btn_thought);
            btn_topic=(ImageButton)findViewById(R.id.btn_topics);
            btn_video=(ImageButton)findViewById(R.id.btn_video);
            btn_skill=(ImageButton)findViewById(R.id.btn_soft);

*/
            uid = new MyDb(getApplicationContext()).getUserId().trim();
            String name = new MyDb(getApplicationContext()).getUserName().toString().trim();
            txt_name = (TextView) findViewById(R.id.txt_name);
            txt_name.setText(name);


            View hView = navigationView.getHeaderView(0);
            TextView nav_user = (TextView) hView.findViewById(R.id.txt_headername);
            nav_user.setText(name);
            txthederemail = (TextView) hView.findViewById(R.id.txt_headeremail);
            txthederemail.setText(new MyDb(getApplicationContext()).getUserEmail().toString().trim());



/*
            View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
       ///     navigationView.addHeaderView(header);
            TextView text = (TextView) header.findViewById(R.id.txt_headername);
            text.setText(""+name);

                  txthederemail=(TextView)header.findViewById(R.id.txt_headeremail);
                 txthederemail.setText(new MyDb(getApplicationContext()).getUserEmail().toString().trim());

*/
            // ======================= First WebView Load FIles

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), OurEvents.class);
                    startActivity(intent);
                }
            });
            rl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), OurEvents.class);
                    startActivity(intent);
                }
            });


            WebView web = (WebView) findViewById(R.id.web);

            web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            web.getSettings().setAppCacheEnabled(false);
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setAppCacheEnabled(false);
            web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            web.setWebChromeClient(new WebChromeClient());
            web.loadUrl(new JsonBilder().getHostName() + "slideimage.php");

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
                   view.loadUrl(new JsonBilder().getHostName() + "slideimage.php");
                       Intent intent=new Intent(MainActivity.this,EventViewPage.class);
                       intent.putExtra("url",url);
                       startActivity(intent);
                    return false; // then it is not handled by default action
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    view.loadUrl("file:///android_asset/slideimage.html");
                    Toast.makeText(MainActivity.this, " Check Internet Connectivity . ", Toast.LENGTH_LONG).show();
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

            //==============================End Firt Load FIles


            String urlss = new JsonBilder().getHostName() + "uploads/" + uid + "_c.png";
            PrimaryUrl = urlss;
            img_dp = (ImageView) findViewById(R.id.imp_dp);
            //  new LongOperation().execute(urlss.trim());
            //   String id=new MyDb(getApplicationContext()).getUserId().trim();
            String id = uid;
            uid = id;
            upLoadServerUri = new JsonBilder().getHostName() + "upload.php?id=" + id.trim();
            //  Toast.makeText(this, "Upload Server URI "+upLoadServerUri, Toast.LENGTH_SHORT).show();
            Log.i("File URl", urlss);
            try {
                requestWriteStoragePermission();
            } catch (Exception e) {
                Toast.makeText(getApplication(), "Storage Permission " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            try {
                paystatus=new MyDb(MainActivity.this).getPayStatus();
                if(paystatus.trim().equals("Yes"))
                {
                    paystatus="Yes";
                }else {
                    new LongOperation().execute(new JsonBilder().getHostName() + "getPayStatus.php?uid=" + uid);
                }
            } catch (Exception e) {

            }


        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error is ", e.toString());
        }

    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //     Dialog.setMessage("Please Wait..");
            //   Dialog.show();
            //  pb.setVisibility(View.VISIBLE);
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
                //    pb.setVisibility(View.GONE);
                //uiUpdate.setText("Output : "+Content);
                Log.i("Contents are", "" + Content);
                if (Content.trim().equals("paid")) {

                       if(new MyDb(MainActivity.this).UpdatePay("Yes"))
                       {
                           Toast.makeText(MainActivity.this, "CHeck Account", Toast.LENGTH_SHORT).show();
                       }
                    paystatus = "Yes";
                } else if (Content.trim().equals("not")) {
                    paystatus = "No";
                }
                //  loadFromServer(Content.toString().trim());

            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.book) {
            // Handle the camera action
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), Books.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.video) {

            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), ListVideos.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }

        } else if (id == R.id.life) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), LifeTopic.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.audio) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), List_Sir_Speech.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.pa) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), AnalysisForm.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.quote) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), ListThought.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.softskil) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), Topic_List.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.aboutus) {
            if (paystatus.equals("Yes")) {
                Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                Intent intent = new Intent(getApplicationContext(), Transaction.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else if (id == R.id.contactus) {
            Intent intent = new Intent(getApplicationContext(), ContactUs.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        } else if (id == R.id.feedback) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "krishnathelifecoach01@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback From Krishna The Life Coach");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send Feedback..."));
        } else if (id == R.id.event) {
            Intent intent = new Intent(getApplicationContext(), OurEvents.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
        else if (id == R.id.logout) {
            if(new MyDb(MainActivity.this).RemoveUser()) {
                finish();
                Intent intent = new Intent(getApplicationContext(), NewLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showmenu(View view) {
        try {
            drawer.openDrawer(Gravity.LEFT);

        } catch (Exception e) {

        }


    }

    public void slide(View view) {
        try {
            Toast.makeText(MainActivity.this, "Clecked", Toast.LENGTH_LONG).show();

        } catch (Exception e) {

        }


    }

    public void video(View v) {
        //   Toast.makeText(getApplicationContext(), "Video Buton Clicked", Toast.LENGTH_SHORT).show();


        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), ListVideos.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }

    }

    public void audio(View v) {
        // Toast.makeText(getApplicationContext(), "Audio Buton Clicked", Toast.LENGTH_SHORT).show();

        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), ProjectAndCreativity.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }

    }

    public void lifetopics(View v) {
        //   Toast.makeText(getApplicationContext(), "Lifetopics Buton Clicked", Toast.LENGTH_SHORT).show();

        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), LifeTopic.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
    }

    public void softskills(View v) {
        //   Toast.makeText(getApplicationContext(), "Soft Skill Buton Clicked", Toast.LENGTH_SHORT).show();

        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), Topic_List.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
    }

    public void thoughts(View v) {
        // Toast.makeText(getApplicationContext(), "Thoughts Buton Clicked", Toast.LENGTH_SHORT).show();
        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), ListThought.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
    }

    public void pa(View v) {

        if (paystatus.equals("Yes")) {
        Intent i = new Intent(getApplicationContext(), SkillEnhancement.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  } else {
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
        // Toast.makeText(getApplicationContext(), "Perosnality Analysis Buton Clicked", Toast.LENGTH_SHORT).show();
    }

    public void uploadimage(View view) {
        try {
     /*       Intent intent = new Intent();

            intent.setType("image/*");

            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
*/

            Intent in = new Intent(MainActivity.this, ImageCropping.class);
            startActivity(in);

        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent I) {

        try {
            super.onActivityResult(requestCode, resultCode, I);

/*            if(I!=null) {
                Uri uri = I.getData();
                Toast.makeText(this, "" + uri.getPath(), Toast.LENGTH_SHORT).show();
                uri.getLastPathSegment();
                Bitmap FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                img_dp.setImageBitmap(FixBitmap);

                Uri uri1 = I.getData();
                Toast.makeText(getApplicationContext(), "" + uri1.getPath(), Toast.LENGTH_LONG);
                String spath = "";
                try {
                    spath = getFilePath(MainActivity.this, uri1);
                } catch (Exception e) {
                    System.out.println("---------------------------- Error in New " + e.getMessage());
                }
                System.out.println("Ne Path----------------------------->>" + spath);

                String path = uri1.getPath();
                // path = path.substring(path.indexOf(":") + 1);
                Log.i("Uri Path", "" + path + " ---->" + uri.getLastPathSegment());


                File saveFile = Environment.getExternalStorageDirectory();
                File f = new File(path);

                String ll = spath;

                int li = spath.lastIndexOf("/");
                ll = spath.substring(li);
                // String p = saveFile.getPath() + "/" + path;
                ll = spath.substring(0, li);
                ll = ll + "/" + uid + "_c.png";
                System.out.println("-----------llllllll->>" + ll);
                f = new File(spath);
                File cfile = new File(ll);
                f.renameTo(cfile);
                f = new File(ll);
                System.out.println("------------>>" + ll);
                System.out.println(f.getAbsolutePath() + "----------------------->Path" + saveFile.getPath() + "/" + path);
                //uploadFile(f);
                //   ff=new File(uri.getPath());
                // uploadFile(ff);
               // new LongOperation_uploading(f).execute(uri.getPath());*/
        /*    }*/
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private class LongOperation1 extends AsyncTask<String, Void, Void> {

        Bitmap bmp;
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //     Dialog.setMessage("Please Wait..");
            //   Dialog.show();
            //pb.setVisibility(View.VISIBLE);
            Log.i("Primary URLS ", PrimaryUrl);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                URL url = new URL(PrimaryUrl);

                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


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
                //  pb.setVisibility(View.GONE);
                //uiUpdate.setText("Output : "+Content);
                img_dp.setImageBitmap(bmp);

                //loadFromServer(Content.toString().trim());

            }
        }

    }

    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;
        Log.i("pass ", "1");
        HttpURLConnection conn = null;
        Log.i("pass ", "2");
        DataOutputStream dos = null;
        Log.i("pass ", "3");
        String lineEnd = "\r\n";
        Log.i("pass ", "4");
        String twoHyphens = "--";
        Log.i("pass ", "5");
        String boundary = "*****";
        Log.i("pass ", "6");
        int bytesRead, bytesAvailable, bufferSize;
        Log.i("pass ", "7");
        byte[] buffer;
        Log.i("pass ", "8");
        int maxBufferSize = 80 * 1024 * 1024;
        Log.i("pass ", "9");
        File sourceFile = ff;
        Log.i("pass ", "10");

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :" + uploadFilePath + ""
                    + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    //  txt_imagepath.setText("Source File not exist :"
                    //      + uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        } else {
            try {
                Log.i("pass ", "11");
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(
                        sourceFile);

                Log.i("pass ", "12");
                URL url = new URL(upLoadServerUri);
                Log.i("pass ", "13");
                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                Log.i("pass ", "14");
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);
                Log.i("pass ", "15");
                conn.setRequestProperty("uploaded_file", fileName);
                Log.i("pass ", "16");

                dos = new DataOutputStream(conn.getOutputStream());
                Log.i("pass ", "17");
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename="
                        + fileName + " " + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.";

                            //      txt_imagepath.setText(msg);
                            // finish();
                            Dialog.dismiss();
                            Toast.makeText(MainActivity.this,
                                    "File Upload Complete.", Toast.LENGTH_SHORT)
                                    .show();
                            String urlss = new JsonBilder().getHostName() + "uploads/" + uid + "_c.png";
                            PrimaryUrl = urlss;
                            //  new LongOperation().execute(new JsonBilder().getHostName()+"uploads/"+uid+"_c.png");


                        }
                    });
                }

                // close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //  txt_imagepath
                        //    .setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(MainActivity.this,
                                "MalformedURLException", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                Dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //    txt_imagepath.setText("Got Exception : see logcat ");
                        Toast.makeText(MainActivity.this,
                                "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e("Upload file to server   Exception : " + e.getMessage(), e);
            }
            Dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }


    private void requestWriteStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
    }


    /*******
     * Used to file upload and show progress
     **********/


    @Override
    protected void onResume() {
        super.onResume();
        try {

            paystatus=new MyDb(MainActivity.this).getPayStatus();
            Log.i("PAY Status IN On RESUME",paystatus);

            //  String urlss = new JsonBilder().getHostName() + "uploads/" + uid + "_c.png";
            //  PrimaryUrl = urlss;
            // new LongOperation().execute(urlss.trim());


        } catch (Exception e) {

        }
    }
}
