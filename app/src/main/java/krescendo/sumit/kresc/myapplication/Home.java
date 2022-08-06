package krescendo.sumit.kresc.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;



public class Home extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_pa, btn_speech, btn_life, btn_video, btn_soft, btn_thought, btn_softskill,btn_th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
/*
        btn_pa = (ImageButton) findViewById(R.id.btn_pa);
        btn_speech = (ImageButton) findViewById(R.id.btn_speech);
        btn_life = (ImageButton) findViewById(R.id.btn_life);
        btn_video = (ImageButton) findViewById(R.id.btn_video);

        btn_softskill = (ImageButton) findViewById(R.id.btn_softskill);
        btn_th = (ImageButton) findViewById(R.id.btn_th);

        btn_pa.setOnClickListener(this);
        btn_speech.setOnClickListener(this);
        btn_life.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        btn_thought.setOnClickListener(this);
        btn_th.setOnClickListener(this);

        btn_soft.setOnClickListener(this);
        btn_softskill.setOnClickListener(this);
*/

    }

    public void startmenu(View v) {
      /*  Dialog d=new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        d.setContentView(R.layout.menufile);
        d.show();
*/

    }


    @Override
    public void onClick(View v) {
  /*      final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadebutton);
        switch (v.getId()) {
            case R.id.btn_pa:
                btn_pa.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            try {
                                Intent intent = new Intent(getApplicationContext(), AnalysisForm.class);
                                startActivity(intent);

                            } catch
                                    (Exception e) {
                            }

                        } catch
                                (Exception e) {
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;

            case R.id.btn_softskill:
                btn_softskill.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), Topic_List.class);
                            startActivity(intent);

                        } catch
                                (Exception e) {
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;

            case R.id.btn_speech:
                btn_speech.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(getApplicationContext(), List_Sir_Speech.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;

            case R.id.btn_life:
                btn_life.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(getApplicationContext(), LifeTopic.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;

            case R.id.btn_video:
                btn_video.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(getApplicationContext(), ListVideos.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;


            case R.id.btn_th:
                btn_th.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(getApplicationContext(), ListThought.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;



        }
*/
    }

    public void goback(View v) {
        try {

            this.finish();
        } catch (Exception e) {

        }

    }

    public void show(View v) {


    }

    public void showmenu(View  v) {

        try {

            Dialog dialog = new Dialog(this, R.style.DialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.mydrawer);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width=
            wlp.gravity = Gravity.TOP | Gravity.LEFT ;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
            dialog.show();


       //     Snackbar.make(v, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void share(View view) {try{
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Krishna Group");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=krescendo.sumit.kresc.myapplication&hl=en");

        startActivity(Intent.createChooser(share, "Krishna Group !"));
    }catch(Exception e)
    {

    }
    }
}
