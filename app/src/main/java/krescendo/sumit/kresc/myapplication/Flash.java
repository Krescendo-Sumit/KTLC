package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;



public class Flash extends AppCompatActivity {
    ImageButton img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final ImageButton img = (ImageButton) findViewById(R.id.btn_img);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.logoanim);
        final Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);
        img.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
             //   new MyDb(getApplicationContext());
                // Toast.makeText(getApplicationContext()," "+new JsonBilder().getAllThought(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                if (new MyDb(getApplicationContext()).checkUser()) {
                    Intent i = new Intent(getApplicationContext(), NewLogin.class);
                    startActivity(i);
                }else
                {

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }


                //    img.startAnimation(animation1);
/*
         NOte changing Intent to New Login Page For Desingngi hages given to Arun Sir
                 Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
  */
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}