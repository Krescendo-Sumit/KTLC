package krescendo.sumit.kresc.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service implements Runnable {
    private Handler handler = new Handler();
    JSONArray jArray;
    long k=0;
    int thoughtstatus=0;
    JSONObject json_data = null;
    private static final int NOTIFY_ME_ID = 1337;
    int i = 1;
    MyDb db;
    String str = null;

    @SuppressWarnings("deprecation")
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //db=new MyDb(this);
        handler.postDelayed(this, 10000);


        Toast.makeText(this, "OnCreate Method Of Servise Class Meathod", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        this.run();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void run() {


        //==========================================================
        try {

            if(thoughtstatus==0) {
                loadFromServer();
                thoughtstatus=1;
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                public void run()
                {


                //   Toast.makeText(this, new JsonBilder().getAllUser().toString(), Toast.LENGTH_LONG).show();
                i++;
                handler.postDelayed(this, 1000);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(R.drawable.krishna11)
                                .setContentTitle("My notification")
                                .setContentText(i + "Messages Pending...");
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(MyService.this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(100, mBuilder.build());


                }},4000);      ///=======================================================

        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
            Toast.makeText(this, "Errror is in  First" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is in  First " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is in   First " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is in   First " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is in   First " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is in   First " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void show() {
        try {

            /*********** Create notification ***********/

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.krishna11)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(NOTIFY_ME_ID, mBuilder.build());


        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Errror is inn show " + e.getMessage(), Toast.LENGTH_SHORT).show();


        }
    }

    public void loadFromServer()
    {
        if (isNetworkAvailable()) {
            try {
                JsonBilder jb = new JsonBilder();
                Toast.makeText(getApplicationContext(), " pass 1", Toast.LENGTH_SHORT).show();
                String str = jb.getAllThought().toString().trim();
                Toast.makeText(getApplicationContext(), " pass 2", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), " From Web " + str, Toast.LENGTH_SHORT).show();
                JSONArray jArray = new JSONArray(str);
                Toast.makeText(getApplicationContext(), " pass 3", Toast.LENGTH_SHORT).show();
                //    names = new String[jArray.length()];
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    //    names[i] = json_data.getString("thought").toString().trim();
                    String title = json_data.getString("title").toString().trim().replace("'", "''");
                    String thought = json_data.getString("thought").toString().trim().replace("'", "''");
                    Toast.makeText(getApplicationContext(), " this retrive " + thought, Toast.LENGTH_SHORT).show();


                    if (db.addThought(json_data.getString("id").toString().trim(), title, thought)) {
                        k = 1;
                        //               Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } //else
                    //           Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_SHORT).show();

                }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}