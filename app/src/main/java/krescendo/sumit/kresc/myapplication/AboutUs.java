package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.FileNotFoundException;
import java.io.InputStream;

public class AboutUs extends AppCompatActivity {
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txt=(TextView)findViewById(R.id.txtdtl);
        txt.setText(Html.fromHtml("" +
                "<center><h3>Krishna Group</h3></center>" +
                "<br>Transforming the Society with spirituality and Positive thinking since 1989.\n" +

                "\n</center>" +
                "\n<br>" +
                "<p>It all started with a proprietary institute under the name of<b> Shrikrishna Career Academy </b>established in 1989 with a major focus on Spoken English & Personality Development. The Krishna group is an educational and humanitarian organization founded by very popular, revered and spiritual teacher -<b> Shri. Abhijit Dharmadhikari</b>. The Krishna Group serves and attracts people from all areas & level starting from a student to senior leadership from Corporates.\n" +
                "\n</p><br>" +
                "<p> We believe in education in its true sense enhances `Excellence` and that helps to nurture/cultivate an individual's character finally enabling an individual to stand confidently in the competitive world and express the talent completely.\n" +
                "\n</p><br>" +
                "<p><b> Krishna Group </b>is a known for revitalizing the human spirit, resolve inner conflict through counselling & meditation. It is also known for inspiring people and saw the transformation in an individual’s life. It is constantly working for inner well-being.</p>"));


        Button btn=(Button)findViewById(R.id.share);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "The Krishna Group");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=krescendo.sumit.kresc.myapplication&hl=en");

                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

    }
    public void goback(View v) {
        try {

            this.finish();
        } catch (Exception e) {

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showmenu(View view) {

    }
}
