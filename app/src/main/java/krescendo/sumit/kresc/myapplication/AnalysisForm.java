package krescendo.sumit.kresc.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;


public class AnalysisForm extends AppCompatActivity {
    Spinner sp_ocup, sp_probel, sp_book, sp_tv, sp_readbook, sp_friend;
    EditText et_name, et_dob, et_quali, et_role, et_functio, et_exper, et_achive, et_weakness, et_otherbook, et_othertv, et_otherproblem,et_stregth;
    CheckBox chkmanagement, chkspi, chknovel, chk_poetry, chk_auti, chkself, chknews, chksport, chkinform, chkmusic, chkgenral, chkmovie, chk_kids;
    CheckBox chkanger, chkfrus, chkinferior, chkoverconfi, chkshy;
    String frined[] = {"Less", "Average", "Many"};
    String readboo[] = {"Yes", "No"};

    LinearLayout ll;
    int kk=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll=(LinearLayout)findViewById(R.id.ll);
        ll.setVisibility(View.GONE);
        sp_friend = (Spinner) findViewById(R.id.sp_pafriend);
        sp_readbook = (Spinner) findViewById(R.id.sp_pabook);


        et_name = (EditText) findViewById(R.id.txt_paname);
        et_dob = (EditText) findViewById(R.id.txt_padob);
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AnalysisForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        et_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        et_quali = (EditText) findViewById(R.id.txt_paquali);
        et_role = (EditText) findViewById(R.id.txt_ocurole);
        et_functio = (EditText) findViewById(R.id.txt_function);
        et_exper = (EditText) findViewById(R.id.txt_paexp);
        et_achive = (EditText) findViewById(R.id.txt_paachiv);
        et_weakness = (EditText) findViewById(R.id.txt_weakness);
        et_otherbook = (EditText) findViewById(R.id.et_paotherbook);
        et_othertv = (EditText) findViewById(R.id.et_othertv);
        et_otherproblem = (EditText) findViewById(R.id.txt_paotherproblems);
        et_stregth= (EditText) findViewById(R.id.txt_pastrengh);

        chkmanagement = (CheckBox) findViewById(R.id.chk_pamgnt);
        chkspi = (CheckBox) findViewById(R.id.chk_paspiri);
        chknovel = (CheckBox) findViewById(R.id.chk_panovel);
        chk_poetry = (CheckBox) findViewById(R.id.chk_papoverty);
        chk_auti = (CheckBox) findViewById(R.id.chk_anti);
        chkself = (CheckBox) findViewById(R.id.chk_paself);
        chknews = (CheckBox) findViewById(R.id.chk_panews);
        chksport = (CheckBox) findViewById(R.id.chk_pasport);
        chkinform = (CheckBox) findViewById(R.id.chk_painform);
        chkmusic = (CheckBox) findViewById(R.id.chk_pamusic);
        chkgenral = (CheckBox) findViewById(R.id.chk_pageneral);
        chkmovie = (CheckBox) findViewById(R.id.chk_pamovies);
        chk_kids = (CheckBox) findViewById(R.id.chk_pakids);
        chkanger = (CheckBox) findViewById(R.id.chk_paanger);
        chkfrus = (CheckBox) findViewById(R.id.chk_pafrus);
        chkinferior = (CheckBox) findViewById(R.id.chk_painfer);
        chkoverconfi = (CheckBox) findViewById(R.id.chk_paoverconf);
        chkshy = (CheckBox) findViewById(R.id.chk_pashy);



        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, frined);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_friend.setAdapter(adapter);


        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, readboo);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_readbook.setAdapter(adapter1);


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

    public void addtoserver(View view) {

        try {
            String name = et_name.getText().toString().trim();
            String dob = et_dob.getText().toString().trim();
            String quali = et_quali.getText().toString().trim();
            String role = et_role.getText().toString().trim();
            String functio = et_functio.getText().toString().trim();
            String exper = et_exper.getText().toString().trim();
            String achive = et_achive.getText().toString().trim();
            String weakness = et_weakness.getText().toString().trim();
            String otherbook = et_otherbook.getText().toString().trim();
            String othertv = et_othertv.getText().toString().trim();
            String otherproblem = et_otherproblem.getText().toString().trim();
            String stregth = et_stregth.getText().toString().trim();
            String chkbookread = "" + otherbook;
            String chktv = "" + othertv;
            String chkprobl = "" + otherproblem;
            String frin=sp_friend.getSelectedItem().toString().trim();
            String book=sp_readbook.getSelectedItem().toString().trim();


            if (chkmanagement.isChecked()) {
                chkbookread += "," + chkmanagement.getText().toString().trim();
            }
            if (chkspi.isChecked()) {
                chkbookread += "," + chkspi.getText().toString().trim();
            }


            if (chknovel.isChecked()) {
                chkbookread += "," + chknovel.getText().toString().trim();
            }
            if (chk_poetry.isChecked()) {
                chkbookread += "," + chk_poetry.getText().toString().trim();
            }
            if (chk_auti.isChecked()) {
                chkbookread += "," + chk_auti.getText().toString().trim();
            }
            if (chkself.isChecked()) {
                chkbookread += "," + chkself.getText().toString().trim();
            }
            if (chknews.isChecked()) {
                chktv += "," + chknews.getText().toString().trim();
            }
            if (chksport.isChecked()) {
                chktv += "," + chksport.getText().toString().trim();
            }
            if (chkinform.isChecked()) {
                chktv += "," + chkinform.getText().toString().trim();
            }
            if (chkmusic.isChecked()) {
                chktv += "," + chkmusic.getText().toString().trim();
            }
            if (chkgenral.isChecked()) {
                chktv += "," + chkgenral.getText().toString().trim();
            }
            if (chkmovie.isChecked()) {
                chktv += "," + chkmovie.getText().toString().trim();
            }
            if (chk_kids.isChecked()) {
                chktv += "," + chk_kids.getText().toString().trim();
            }
            if (chkanger.isChecked()) {
                chkprobl += "," + chkanger.getText().toString().trim();
            }
            if (chkfrus.isChecked()) {
                chkprobl += "," + chkfrus.getText().toString().trim();
            }
            if (chkinferior.isChecked()) {
                chkprobl += "," + chkinferior.getText().toString().trim();
            }
            if (chkoverconfi.isChecked()) {
                chkprobl += "," + chkoverconfi.getText().toString().trim();
            }
            if (chkshy.isChecked()) {
                chkprobl += "," + chkshy.getText().toString().trim();
            }
            if (name.trim().equals("") || dob.trim().equals("") || quali.trim().equals("") || functio.trim().equals("") || achive.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Fill All Fields Carefully", Toast.LENGTH_LONG).show();


            }else {

                if (isNetworkAvailable()) {
                    String sk = new JsonBilder().addAnalysis(name, dob, quali, role, functio, exper, achive, stregth, weakness, frin, book, chkbookread, chktv, chkprobl).trim();
                    if (sk.equals("saved")) {
                        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Not Saved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Connection ", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void goback(View v) {
        try {

            this.finish();
        } catch (Exception e) {

        }

    }
    public void colapse(View v)
    {

        if(kk==0)
        {
            ll.setVisibility(View.VISIBLE);


            final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bounce);

            ll.startAnimation(animation);

            kk=1;
        }
        else
        {
            ll.setAnimation(null);
            ll.setVisibility(View.GONE);
            kk=0;
        }

    }

    public void video(View v)
    {
        Toast.makeText(getApplicationContext(), "Video Buton Clicked", Toast.LENGTH_SHORT).show();
        finish();
        Intent i=new Intent(getApplicationContext(),ListVideos.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void audio(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Audio Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),List_Sir_Speech.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }
    public void lifetopics(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Lifetopics Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),LifeTopic.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void softskills(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Soft Skill Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),Topic_List.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void thoughts(View v)
    {finish();
        Toast.makeText(getApplicationContext(), "Thoughts Buton Clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),ListThought.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
    public void pa(View v)
    {finish();
        Intent i=new Intent(getApplicationContext(),AnalysisForm.class);
        startActivity(i);  overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        Toast.makeText(getApplicationContext(), "Perosnality Analysis Buton Clicked", Toast.LENGTH_SHORT).show();
    }


}
