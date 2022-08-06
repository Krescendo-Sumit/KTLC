package krescendo.sumit.kresc.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Nihar on 23-11-2017.
 */


 class LongOperation extends  AsyncTask<String, Void, Void> {


    String str,uname,pass;
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    String result = "";
    Context context;
    private String Error = null;
public  LongOperation(Context context,String uname,String pass)
{
    this.context=context;
    this.uname=uname;
    this.pass=pass;
}

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

    }

    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {
        try {

            // Call long running operations here (perform background computation)
            // NOTE: Don't call UI Element here.

            // Server url call by GET method
            str = new JsonBilder().checkLogin(uname.trim(), pass.trim(),"").trim();


        } catch (Exception e) {
            Error = e.getMessage();
            cancel(true);
        }

        return null;
    }

    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.

        // Close progress dialog


        if (Error != null) {

            //  uiUpdate.setText("Output : "+Error);

        } else {
              Log.i("Str",""+str);
            try{
                JSONArray jsonArray=new JSONArray(str);
                JSONObject jsonObject;
                for(int i=0;i<jsonArray.length();i++)
                {// [{"id":"512","fname":"sumit","lname":"suradkar","email":"sssnit@gmail.com","mobile":"9420329047","pass":"1234"}]
                    jsonObject=jsonArray.getJSONObject(i);
                    if(new MyDb(context).adduser(jsonObject.getString("id"),jsonObject.getString("fname"),jsonObject.getString("lname"),jsonObject.getString("email"),jsonObject.getString("mobile"),jsonObject.getString("pass")))
                    {
                        Toast.makeText(context, "Data saved Locally", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                    }else
                    {
                        Toast.makeText(context, "Data Not saved Locally", Toast.LENGTH_SHORT).show();
                    }

                }
            }catch(Exception e)
            {
Log.i("Error in Login ",""+e.getMessage());
            }
            if (str.trim().equals("Valid")) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

                //overridePendingTransition(R.anim.left_in, R.anim.left_out);

               // if (new MyDb(context).adduser("1", username, username, username, password, password)) {
                 //   Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                //}
                //finish();

            } else {
                Toast.makeText(context, "Wrong Password (Sign Up Please)", Toast.LENGTH_SHORT).show();
            }

        }
    }

}

