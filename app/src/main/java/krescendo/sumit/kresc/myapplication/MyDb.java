package krescendo.sumit.kresc.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;


public class MyDb extends SQLiteOpenHelper {

	final static String DBName="krishna";
	final static int version=1;
	
	public MyDb(Context context) {
		super(context, DBName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_q="CREATE TABLE demo (sid INTEGER PRIMARY KEY,I_name text,prize text)";
		String tbl_life_topic="CREATE TABLE tbl_life_topic(id INTEGER PRIMARY KEY,title text,desc text,dd text,filename text)";
		String tbl_video="CREATE TABLE tbl_video(id INTEGER PRIMARY KEY,title text,topic text,dd text,fname text)";
		String tbl_thought="CREATE TABLE tbl_thought(id INTEGER PRIMARY KEY,title text,thought text)";
		String tbl_speech="CREATE TABLE tbl_speech(id INTEGER PRIMARY KEY,title text,topic text,fname text,dd text)";
		String tbl_user="CREATE TABLE user(id INTEGER PRIMARY KEY,fname text,lname text,email text,mobile text,pass text)";
		db.execSQL(tbl_life_topic);
		Log.i("First ","Life topic Created");
		db.execSQL(tbl_video);
		Log.i("First ","Video Created");
		db.execSQL(tbl_thought);
		Log.i("First ","Thought Created");
		db.execSQL(tbl_speech);
		Log.i("First ","Speech created ");
		db.execSQL(create_q);
		Log.i("First ","Demo Created");
		db.execSQL(tbl_user);
		String tbl_pay="CREATE TABLE pay(id INTEGER PRIMARY KEY,sts text)";
		db.execSQL(tbl_pay);
		Log.i("First ","PAy Created");
		tbl_pay="Insert into pay values(3,'NO')";
		db.execSQL(tbl_pay);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS demo");
        onCreate(db);		
	}
    public boolean addRecord(String id,String fname,String lname)
    {
    	try{
    		
    		SQLiteDatabase mydb=this.getWritableDatabase();
         
    		ContentValues data=new ContentValues();
			data.put("sid", id);
    		data.put("i_name", fname);
    		data.put("prize", lname);
    		mydb.insert("demo", null,data);
    		mydb.close();
    		return true;
    	}catch(Exception e)
    	{
    		return false;
    	}
    	
    }
	public Vector[] viewAllUser() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM demo";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
			q = "SELECT * FROM demo";

			Cursor c1 = mydb.rawQuery(q, null);
			v = new Vector[i];
			i = 0;
			while (c1.moveToNext()) {
				v[i] = new Vector();

				v[i].addElement(c1.getString(0));
				v[i].addElement(c1.getString(1));
                v[i].addElement(c1.getString(1));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}
    public String getCount() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = "";
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            // Vector ov = viewAllOrderById(id);
            String q = "SELECT  count(*) from demo";

            Cursor c1 = mydb.rawQuery(q, null);

            if (c1.moveToNext()) {

                v = (c1.getString(0));

            }

            return v;
        } catch (Exception e) {

            return "11";
        } finally {
            mydb.close();
        }
    }
	public boolean addThought(String id, String title,String thought)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_thought(id,title,thought) values("+id+",'"+title+"','"+thought+"')";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public boolean RemoveUser()
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from user";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public boolean UpdatePay(String st)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "update pay set sts='"+st+"' where id=3";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public String getPayStatus() {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  id,sts from pay";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = c1.getString(1).toString().trim();

			}

			return v;
		} catch (Exception e) {

			return "No Data Found"+e.getMessage();
		} finally {
			mydb.close();
		}
	}


	public boolean addLifeTopic(String id, String title,String descr,String dd,String fname)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_life_topic(id,title,desc,dd,filename) values("+id+",'"+title+"','"+descr+"','"+dd+"','"+fname+"')";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public boolean removeAllLifeTopic()
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from  tbl_life_topic";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public boolean removeLifeTopic()
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from  tbl_life_topic";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public String getLifeTopicDescription(String name) {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  filename from tbl_life_topic where title='"+name+"'";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = (c1.getString(0));

			}

			return v;
		} catch (Exception e) {

			return "No Data Found";
		} finally {
			mydb.close();
		}
	}
	public boolean addSpeech(String id,String title, String topic,String fname,String dd)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_speech(id,title,topic,fname,dd) values("+id+",'"+title+"','"+topic+"','"+fname+"','"+dd+"')";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}
	public boolean addVideo(String id,String title, String topic,String dd,String fname)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_video(id,title,topic,dd,fname) values("+id+",'"+title+"','"+topic+"','"+dd+"','"+fname+"')";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}


	public boolean adduser(String id,String fname, String lname,String email,String mobile,String pass)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into user(id,fname,lname,email,mobile,pass) values("+id+",'"+fname+"','"+lname+"','"+email+"','"+mobile+"','"+pass+"')";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}

	}

	public Vector[] viewAllThought() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_thought order by id desc";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
			q = "SELECT * FROM tbl_thought  order by id desc";

			Cursor c1 = mydb.rawQuery(q, null);
			v = new Vector[i];
			i = 0;
			while (c1.moveToNext()) {
				v[i] = new Vector();

				v[i].addElement(c1.getString(0));
				v[i].addElement(c1.getString(1));
				v[i].addElement(c1.getString(2));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}
	public String getUserName() {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  fname,lname from user";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = (c1.getString(0))+" "+(c1.getString(1));

			}

			return v;
		} catch (Exception e) {

			return "No Data Found";
		} finally {
			mydb.close();
		}
	}
	public String getUserId() {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  id from user";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = (c1.getString(0));

			}

			return v;
		} catch (Exception e) {

			return "No Data Found";
		} finally {
			mydb.close();
		}
	}

	public String getUserPhone() {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  mobile from user";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = (c1.getString(0));

			}

			return v;
		} catch (Exception e) {

			return "No Data Found";
		} finally {
			mydb.close();
		}
	}
	public String getUserEmail() {
		SQLiteDatabase mydb = null;
		String k = "";
		String v = "";
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			// Vector ov = viewAllOrderById(id);
			String q = "SELECT  email from user";

			Cursor c1 = mydb.rawQuery(q, null);

			if (c1.moveToNext()) {

				v = (c1.getString(0));

			}

			return v;
		} catch (Exception e) {

			return "No Data Found";
		} finally {
			mydb.close();
		}
	}
	public boolean checkUser() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM user";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
		if(i==0)
			return true;
			else
			return false;
		} catch (Exception e) {

			return false;
		} finally {
			mydb.close();
		}
	}

	public Vector[] viewAllSpeech() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_speech";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
			q = "SELECT * FROM tbl_speech";

			Cursor c1 = mydb.rawQuery(q, null);
			v = new Vector[i];
			i = 0;
			while (c1.moveToNext()) {
				v[i] = new Vector();

				v[i].addElement(c1.getString(0));
				v[i].addElement(c1.getString(1));
				v[i].addElement(c1.getString(2));
				v[i].addElement(c1.getString(3));
				v[i].addElement(c1.getString(4));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}

	public Vector[] viewAllVideo() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_video";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
			q = "SELECT * FROM tbl_video";

			Cursor c1 = mydb.rawQuery(q, null);
			v = new Vector[i];
			i = 0;
			while (c1.moveToNext()) {
				v[i] = new Vector();

				v[i].addElement(c1.getString(0));
				v[i].addElement(c1.getString(1));
				v[i].addElement(c1.getString(2));
				v[i].addElement(c1.getString(3));
				v[i].addElement(c1.getString(4));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}

	public Vector[] viewAllLifeTopic() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_life_topic ORDER BY ID DESC";

			Cursor c = mydb.rawQuery(q, null);

			while (c.moveToNext()) {
				i++;
			}
			q = "SELECT * FROM tbl_life_topic";

			Cursor c1 = mydb.rawQuery(q, null);
			v = new Vector[i];
			i = 0;
			while (c1.moveToNext()) {
				v[i] = new Vector();

				v[i].addElement(c1.getString(0));
				v[i].addElement(c1.getString(1));
				v[i].addElement(c1.getString(2));
				v[i].addElement(c1.getString(3));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}
}
