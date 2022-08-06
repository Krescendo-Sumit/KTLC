package krescendo.sumit.kresc.myapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import sssnit.gmal.cm.MyURLMaster;

public class JsonBilder {

    static JsonBilder jb = null;


    public static JsonBilder getInstance() {
        if (jb != null) {
            return jb;
        } else {
            return (new JsonBilder());

        }
    }

  //  String HOSTNAME = "";
   String HOSTNAME = new MyURLMaster().getHostName();

    public String getHostName() {
        try {
            return HOSTNAME;
        } catch (Exception e) {
            return null;

        }
    }

    public String checkLogin(String ma, String pass,String imie) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("ma", ma));
        nameValuePairs.add(new BasicNameValuePair("pass", pass));
        nameValuePairs.add(new BasicNameValuePair("imie", imie));
        Log.i("Uname",ma+"  "+pass);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "checkLogin.php");
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

        String result = "";
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

        return result;

    }

    public String updateStatus(String id) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "updateStatus.php");
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

        String result = "";
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

        return result;

    }

    public String addLogin(String fname, String lname, String email, String mobile, String pass ,String imie,String ref) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("fname", fname));
        nameValuePairs.add(new BasicNameValuePair("lname", lname));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        nameValuePairs.add(new BasicNameValuePair("pass", pass));
        nameValuePairs.add(new BasicNameValuePair("imie", imie));
        nameValuePairs.add(new BasicNameValuePair("ref", ref));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "insertLogin.php");
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

        String result = "";
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

            Log.e("pass 2", "connection success " + result);
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());

        }

        return result;

    }


    public String addAnalysis(String name, String dob, String quali, String role, String fun, String exper, String achive, String strength, String weakness, String friends, String readbook, String booktype, String tv, String problem) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("dob", dob));
        nameValuePairs.add(new BasicNameValuePair("quali", quali));
        nameValuePairs.add(new BasicNameValuePair("role", role));
        nameValuePairs.add(new BasicNameValuePair("fun", fun));
        nameValuePairs.add(new BasicNameValuePair("exper", exper));
        nameValuePairs.add(new BasicNameValuePair("achive", achive));
        nameValuePairs.add(new BasicNameValuePair("strength", strength));
        nameValuePairs.add(new BasicNameValuePair("weakness", weakness));
        nameValuePairs.add(new BasicNameValuePair("friends", friends));
        nameValuePairs.add(new BasicNameValuePair("readbook", readbook));
        nameValuePairs.add(new BasicNameValuePair("booktype", booktype));
        nameValuePairs.add(new BasicNameValuePair("tv", tv));
        nameValuePairs.add(new BasicNameValuePair("problem", problem));
        //	nameValuePairs.add(new BasicNameValuePair("ma", ma));
//		nameValuePairs.add(new BasicNameValuePair("pass", pass));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "insertAnalysis.php");
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

        String result = "";
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

        return result;

    }

    public String getAllThought() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectThought.php");
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

        String result = "";
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

        return result;
    }

    public String getAllVideo() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectVideo.php");
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

        String result = "";
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

        return result;
    }

    public String getAllLifeTopic() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectLifeTopic.php");
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

        String result = "";
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

        return result;
    }

    public String getAllSpeech() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectSpeech.php");
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

        String result = "";
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

        return result;
    }


    public String getAllPostFunctionWork() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectPostFunctionwork.php");
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

        String result = "";
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

        return result;
    }

    public String getPrecentage() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "viewPre.php");
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

        String result = "";
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

        return result;
    }

    public String getAllUser() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "selectUser.php");
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

        String result = "";
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

        return result;
    }

    public String getAllEvent() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "selectEvent.php");
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

        String result = "";
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

        return result;
    }

    public String getPoastFunctionWork() {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectPostFunctionWork.php");
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

        String result = "";
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

        return result;
    }

    public String getAllPhotById(String id) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME
                    + "selectPhotoById.php");
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

        String result = "";
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

        return result;
    }

    public String checkEvent(String checkindate, String checkoutdate) {
        InputStream is = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cindate", checkindate));
        nameValuePairs.add(new BasicNameValuePair("coutdate", checkoutdate));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOSTNAME + "checkEventDate.php");
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

        String result = "";
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

        return result;
    }

    public String insertWorkDeatils(String workid, String user) {
        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("workid", workid));
            nameValuePairs.add(new BasicNameValuePair("statusid", "1"));
            nameValuePairs.add(new BasicNameValuePair("userid", "4"));
            nameValuePairs.add(new BasicNameValuePair("user", user));
            // nameValuePairs.add(new BasicNameValuePair("address", address));
            // nameValuePairs.add(new BasicNameValuePair("order", allorder));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(HOSTNAME
                        + "insertWorkDetails.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // is = entity.getContent();


            } catch (Exception e) {


            }
            return null;
        } catch (Exception e) {


            return null;
        }

    }

    public String insertEvent(String eventname, String checkindate,
                              String checkoutdate, String guestname, String discription,
                              String mobile, String stt, String checkouttime, String checkintime) {
        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            // eventid,eventname,checkindate, checkoutdate,
            // guestname,createddate,discription,mobile
            nameValuePairs.add(new BasicNameValuePair("ename", eventname));
            nameValuePairs.add(new BasicNameValuePair("cindate", checkindate));
            nameValuePairs
                    .add(new BasicNameValuePair("coutdate", checkoutdate));
            nameValuePairs.add(new BasicNameValuePair("guestname", guestname));
            nameValuePairs.add(new BasicNameValuePair("des", discription));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
            nameValuePairs.add(new BasicNameValuePair("stt", stt));
            nameValuePairs.add(new BasicNameValuePair("ckout", checkouttime
                    + ":00:00"));
            nameValuePairs.add(new BasicNameValuePair("ckin", checkintime
                    + ":00:00"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(HOSTNAME + "insertEvent.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // is = entity.getContent();


            } catch (Exception e) {


            }
            return null;
        } catch (Exception e) {


            return null;
        }

    }

    public String insertPhotoDetails(String eventname, String checkindate,
                                     String mobile, String filename) {

        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            // eventid,eventname,checkindate, checkoutdate,
            // guestname,createddate,discription,mobile
            int st = filename.lastIndexOf("/");

            String nm = filename.substring(st + 1, filename.length());
            nameValuePairs.add(new BasicNameValuePair("name", eventname));
            nameValuePairs.add(new BasicNameValuePair("chin", checkindate));

            nameValuePairs.add(new BasicNameValuePair("mob", mobile));
            nameValuePairs.add(new BasicNameValuePair("fname", nm));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(HOSTNAME + "insertPhotoDetails.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // is = entity.getContent();


            } catch (Exception e) {

            }
            return null;
        } catch (Exception e) {


            return null;
        }

    }

}
