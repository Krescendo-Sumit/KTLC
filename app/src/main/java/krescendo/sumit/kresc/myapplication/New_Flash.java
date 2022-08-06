package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;



public class New_Flash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__flash);

        final TextView img = (TextView) findViewById(R.id.textView);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.logoanim);
        final Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);
        img.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new MyDb(getApplicationContext());
                // Toast.makeText(getApplicationContext()," "+new JsonBilder().getAllThought(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                //    img.startAnimation(animation1);

                Intent i = new Intent(getApplicationContext(), NewLogin.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
