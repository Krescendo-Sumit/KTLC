package krescendo.sumit.kresc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OurEvents extends AppCompatActivity {
    static Context context;
    TabLayout tabLayout;
    static int tabindex = 0;
    ListView tempList;

    static int[] imgDrawer = {R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow};
    static String[] types = {"Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event"};
    static String[] title = {"Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation"};
    static String[] demodate = {"15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov"};
    static ListView lst_menu;

    static int[] imgDrawer1 = {R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow, R.drawable.arrow};
    static String[] types1 = {"Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event", "Calendar Event", "Calendar Event", "GCC Event", "GCC Event"};
    static String[] title1 = {"Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation", "Flag Hosting", "Republic Day", "Anual Festival", "Speech On Meditation"};
    static String[] demodate1 = {"15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov", "15 Aug", "26 Jan", "15 Nov", "14 Nov"};


    Typeface custom_font;
    static Customemain customemain = null;
    String upcommingstr = "", commingstr = "";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = OurEvents.this;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
setTitle("Events");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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
        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //  Toast.makeText(context, " " + tabindex, Toast.LENGTH_LONG).show();

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                View rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);

                lst_menu = (ListView) rootView.findViewById(R.id.lst);

                new LongOperation(lst_menu,1).execute(new JsonBilder().getHostName() + "selectEvent.php");
                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

                View rootView = inflater.inflate(R.layout.fragment_past_event, container, false);

                lst_menu = (ListView) rootView.findViewById(R.id.lst);
                new LongOperation(lst_menu,2).execute(new JsonBilder().getHostName() + "selectEventPast.php");

                // loadListView(lst_menu);
                return rootView;
            } else {
                return null;
            }


        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Upcoming";
                case 1:
                    return "Past";

            }
            return null;
        }
    }

    class Customemain extends BaseAdapter {

        @Override
        public int getCount() {
            return types.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.custom_mainactivity, null);

            ImageButton imgbtn = (ImageButton) convertView.findViewById(R.id.imgthumb);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.Title);
            TextView txtType = (TextView) convertView.findViewById(R.id.txtType);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);


            imgbtn.setImageResource(imgDrawer[position]);
            txtTitle.setText(Html.fromHtml(title[position]));
            //txtTitle.setTypeface(custom_font);
            txtType.setText(Html.fromHtml(types[position]));
            //txtType.setTypeface(custom_font);
            txtDate.setText(Html.fromHtml(demodate[position]));
            txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Intent intent=new Intent(getApplicationContext(),article.class);
                    //  startActivity(intent);
                }
            });

            // txtDate.setTypeface(custom_font);

            return convertView;
        }
    }


    public static class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        private String Error = null;
        // private ProgressDialog Dialog = new ProgressDialog(Events.this);
        private ListView lst;
        private int c;

        LongOperation(ListView lst,int c) {
            this.lst = lst;
            this.c=c;
        }

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //  Dialog.setMessage("Please Wait " );
            // Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method


                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //name, surname, username, dob, age, email, phone, mobile, education, city, state, country, pass


                //	nameValuePairs.add(new BasicNameValuePair("ma", ma));
                //	nameValuePairs.add(new BasicNameValuePair("pass", pass));
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(urls[0]);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    // Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                    // Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    // Toast.LENGTH_LONG).show();
                }


                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();

                    // Toast.makeText(getApplicationContext(), "Data is "+result,
                    // Toast.LENGTH_LONG).show();

                    // Log.e("pass 2", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());

                }

                //return result;


                //     String result = new JsonBilder().addUser(name, surname, username, dob, age, email, phone, mobile, gen, education, city, state, country, pass);

            } catch (Exception e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            //  Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                // Toast.makeText(Events.this, "" + result, Toast.LENGTH_SHORT).show();
                Log.i("Result :", result);

                //  insertLogin(result);

                //showRecords(result);

                try {
                    JSONArray ja = new JSONArray(result.trim());
                    JSONObject jo = null;

                    if(c==1) {
                        types = new String[ja.length()];
                        demodate = new String[ja.length()];
                        title = new String[ja.length()];
                        types = new String[ja.length()];
                        imgDrawer = new int[ja.length()];

                        for (int i = 0; i < ja.length(); i++) {
                            try {
                                jo = ja.getJSONObject(i);

                                types[i] = jo.toString();
                                title[i] = jo.getString("title");
                                demodate[i] =
                                        "<h3>Date    :&nbsp;"+jo.getString("edate")+
                                                "<br>Time    :&nbsp;"+jo.getString("time")+" - "+jo.getString("etime")+"</h3>" +
                                                "<br>Trainer :<b>&nbsp;"+jo.getString("trainer")+"</b>" +
                                                "<br>Charges :<b>&nbsp;"+jo.getString("charges")+"</b>";
                                imgDrawer[i] = R.drawable.arrow;


                            } catch (Exception e) {
                                //  Toast.makeText(Events.this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        lst.setAdapter(new Adapter_TimeTable(context, title, demodate, imgDrawer));
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(context, UpcommingEventsDetails.class);
                                intent.putExtra("json", types[i]);
                               context.startActivity(intent);

                            }
                        });
                    }if(c==2)
                    {

                        types1 = new String[ja.length()];
                        demodate1 = new String[ja.length()];
                        title1 = new String[ja.length()];
                        types1 = new String[ja.length()];
                        imgDrawer1 = new int[ja.length()];

                        for (int i = 0; i < ja.length(); i++) {
                            try {
                                jo = ja.getJSONObject(i);

                                types1[i] = jo.toString();
                                title1[i] = jo.getString("title");
                                demodate1[i] =
                                        "<h3><b>Date    :</b>&nbsp;"+jo.getString("edate")+
                                                "<br><b>Time    :</b>&nbsp;"+jo.getString("time")+"</h3>" +
                                                "<br><b>Trainer :</b>&nbsp;"+jo.getString("trainer")+"" ;
                                //      "<br><b>Charges :</b>&nbsp;"+jo.getString("charges")+"</b>";
                                imgDrawer1[i] = R.drawable.arrow;


                            } catch (Exception e) {
                                //  Toast.makeText(Events.this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        lst.setAdapter(new Adapter_TimeTable(context, title1, demodate1, imgDrawer1));
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(context, EventDetails.class);
                                intent.putExtra("json", types1[i]);
                                context.startActivity(intent);

                            }
                        });


                    }

                } catch (Exception e) {

                }


            }
        }

    }

    public void showRecords(String str) {
        /*  try{
          JSONArray jsonArray=new JSONArray(str.trim());
            JSONObject jsonObject=null;
            name=new String[jsonArray.length()];
            id=new String[jsonArray.length()];
            data=new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject=jsonArray.getJSONObject(i);
                name[i]=jsonObject.getString("name").trim();
                id[i]=jsonObject.getString("id").trim();
                data[i]=jsonObject.getString("details").trim();
            }

            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.type_item,name);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(LAWS.this, id[i]+"  -  "+name[i], Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),LAWSDetails.class);
                    intent.putExtra("data",data[i]);
                    startActivity(intent);

                }
            });

        }catch(Exception e)
        {
            Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void loadListView(ListView lst) {
        try {
            //   new LongOperation().execute(new JsonBilder().getHostName()+"selectEvent.php");
            lst.setAdapter(new Adapter_TimeTable(context, title, demodate, imgDrawer));

        } catch (Exception e) {

        }
    }
}
