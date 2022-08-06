package krescendo.sumit.kresc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewLogin extends AppCompatActivity {
EditText et_uname,et_pass;
    String username,password;
    Button btn_signin;
    String imie="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        if (new MyDb(getApplicationContext()).checkUser()) {

        }else
        {
          finish();
          Intent i = new Intent(getApplicationContext(), MainActivity.class);
          startActivity(i);
        }

        et_uname=(EditText)findViewById(R.id.et_uname);
        et_pass=(EditText)findViewById(R.id.et_pass);
        btn_signin=(Button)findViewById(R.id.btn_signin);
;

        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //  imie = mngr.getDeviceId().toString().trim();

            if (android.os.Build.VERSION.RELEASE.startsWith("10")) {
                // Do something for lollipop and above versions
                imie = Settings.Secure.getString(NewLogin.this.getContentResolver(), Settings.Secure.ANDROID_ID);

            } else {
                // do something for phones running an SDK before lollipop
                imie = mngr.getDeviceId().toString().trim();

            }

            if (android.os.Build.VERSION.SDK_INT >= 28) {
                // Do something for lollipop and above versions
                imie = Settings.Secure.getString(NewLogin.this.getContentResolver(), Settings.Secure.ANDROID_ID);

            } else {
                // do something for phones running an SDK before lollipop

                imie = mngr.getDeviceId().toString().trim();
            }
        } catch (Exception e) {

        }

    }
public void forget(View v)

{
    try{

        Intent intent=new Intent(NewLogin.this,ForgetPassword.class);
        startActivity(intent);
    }catch(Exception e)
    {
        Toast.makeText(NewLogin.this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

    public void show(View view)
    {

        if(view.getId()==R.id.btn_signup) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }else
        {

            username =et_uname.getText().toString().trim();
            password=et_pass.getText().toString().trim();




            if (username.trim().equals("") || password.trim().equals("")) {

                Toast.makeText(getApplicationContext(), "Please Fill Username and Password !", Toast.LENGTH_SHORT).show();

            } else {

            //    String st = new JsonBilder().checkLogin(username, password).trim();
              //  String st = new JsonBilder().checkLogin(username, password).trim();
                btn_signin.setText("Please Wait...");
               // Toast.makeText(getApplicationContext(),et_uname.getText().toString().trim()+"  " + et_pass.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                new LongOperation1(getApplicationContext(),et_uname.getText().toString().trim(),et_pass.getText().toString().trim()).execute("welcome");

                //   Toast.makeText(getApplicationContext(), "" + st, Toast.LENGTH_SHORT).show();

            }

          //  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          //  startActivity(intent);
        }
    }

    class LongOperation1 extends AsyncTask<String, Void, Void> {


        String str,uname,pass;
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        Context context;
        private String Error = null;
        public  LongOperation1(Context context,String uname,String pass)
        {
            this.context=context;
            this.uname=uname;
            this.pass=pass;
        }

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
           // Toast.makeText(context,uname+ "  "+pass, Toast.LENGTH_SHORT).show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                str = new JsonBilder().checkLogin(uname.trim(), pass.trim(),imie).trim();


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
                    {
                        // [{"id":"512","fname":"sumit","lname":"suradkar","email":"sssnit@gmail.com","mobile":"9420329047","pass":"1234"}]
                        jsonObject=jsonArray.getJSONObject(i);
                        if(new MyDb(context).adduser(jsonObject.getString("id"),jsonObject.getString("fname"),jsonObject.getString("lname"),jsonObject.getString("email"),jsonObject.getString("mobile"),jsonObject.getString("pass")))
                        {
                          //  Toast.makeText(context, "Data saved Locally", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(context,MainActivity.class);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            startActivity(intent);
                            finish();
                        }
                    }
                }catch(Exception e)
                {
                    btn_signin.setText("Sign In");
                    Log.i("Error in Login ",""+e.getMessage());
                    if(str.trim().equals("Invalid")){
                        Toast.makeText(context, "Please Enter Valid Username and Password", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }}


            }
        }

    }


}
