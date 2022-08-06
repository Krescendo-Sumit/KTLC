package krescendo.sumit.kresc.myapplication;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;*/

import org.apache.http.client.ClientProtocolException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
/*

import it.sauronsoftware.ftp4j.FTPClient;

*/

public class ImageCropping extends AppCompatActivity {
    String PrimaryUrl = "";
    ImageButton imageButton;
    ProgressDialog Dialog = null;

    static final String FTP_HOST = "";

    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "";

    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "";


    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;
    TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cropping);



        txt_name=(TextView)findViewById(R.id.txt_name);



    }

  /*  *//**
     * Start pick image activity with chooser.
     *//*
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageButton) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
            //    Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();


                try {
                    //  super.onActivityResult(requestCode, resultCode, data);

                    if (data != null) {
                        Uri uri = result.getUri();
                     //   Toast.makeText(this, "" + uri.getPath(), Toast.LENGTH_SHORT).show();
                        uri.getLastPathSegment();
                        // Bitmap FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        //  img_dp.setImageBitmap(FixBitmap);

                        Uri uri1 = result.getUri();
                    //    Toast.makeText(getApplicationContext(), "" + uri1.getPath(), Toast.LENGTH_LONG);
                        String spath = "";
                        try {
                            spath = getFilePath(ImageCropping.this, uri1);
                        } catch (Exception e) {
                            System.out.println("---------------------------- Error in New " + e.getMessage());
                        }
                        System.out.println("Ne Path----------------------------->>" + spath);

                        String path = uri1.getPath();
                        // path = path.substring(path.indexOf(":") + 1);
                        Log.i("Uri Path", "" + path + " ---->" + uri.getLastPathSegment());


                        File saveFile = Environment.getExternalStorageDirectory();
                        File f = new File(path);

                        String ll = spath;

                        int li = spath.lastIndexOf("/");
                        ll = spath.substring(li);
                        // String p = saveFile.getPath() + "/" + path;
                        ll = spath.substring(0, li);
                        ll = ll + "/61_c.png";
                        System.out.println("-----------llllllll->>" + ll);
                        f = new File(spath);
                        File cfile = new File(ll);
                        f.renameTo(cfile);
                        f = new File(ll);
                        System.out.println("------------>>" + ll);
                        System.out.println(f.getAbsolutePath() + "----------------------->Path" + saveFile.getPath() + "/" + path);
                        //uploadFile(f);
                        //   ff=new File(uri.getPath());
                        // uploadFile(ff);
                        new LongOperation_uploading(f).execute(uri.getPath());
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    *//**
     * Start crop image activity for the given image.
     *//*
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private class LongOperation_uploading extends AsyncTask<String, Void, Void> {


        private String Content;
        private String Error = null;
        File f;
        FTPClient client;

        LongOperation_uploading(File f) {
            this.f = f;
        }

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog = new ProgressDialog(ImageCropping.this);
            Dialog.setMessage("Changing Profile Image....");
            Dialog.show();

            try {
                client = new FTPClient();
                Log.i("Error is ", "pass 1");
                try {

                    client.connect(FTP_HOST, 21);
                    client.login(FTP_USER, FTP_PASS);
                    client.setType(FTPClient.TYPE_BINARY);


                } catch (Exception e) {
                    //  Toast.makeText(ImageCropping.this, "Uploading Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("Error is ", e.getMessage() + "  ");
                    e.printStackTrace();

                }


            } catch (Exception e) {
                Error = e.getMessage();
                cancel(true);
            }


            //pb.setVisibility(View.VISIBLE);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // uploadFile(f);


                try {
                    try {
                        client = new FTPClient();
                        Log.i("Error is ", "pass 1");
                        try {

                            client.connect(FTP_HOST, 21);
                            client.login(FTP_USER, FTP_PASS);
                            client.setType(FTPClient.TYPE_BINARY);


                        } catch (Exception e) {
                            //  Toast.makeText(ImageCropping.this, "Uploading Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i("Error is ", e.getMessage() + "  ");
                            e.printStackTrace();

                        }


                    } catch (Exception e) {
                        Error = e.getMessage();
                        cancel(true);
                    }

                    client.upload(new File(f.getPath()), null);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            } catch (Exception e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog


            Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);
                Toast.makeText(ImageCropping.this, "Profile Image Not Updated.", Toast.LENGTH_SHORT).show();


            } else {
                //pb.setVisibility(View.GONE);
                //   uiUpdate.setText("Output : "+Content);
                // loadFromServer(Content.toString().trim());

                Toast.makeText(ImageCropping.this, "Profile Image Updated.", Toast.LENGTH_SHORT).show();
                finish();

            }

            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            // Toast.makeText(ImageCropping.this, "Profile Image Changed.", Toast.LENGTH_SHORT).show();
        }

    }
    private class LongOperation extends AsyncTask<String, Void, Void> {

        Bitmap bmp;
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            //     Dialog.setMessage("Please Wait..");
            //   Dialog.show();
            //pb.setVisibility(View.VISIBLE);
            Log.i("Primary URLS ", PrimaryUrl);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                URL url = new URL(PrimaryUrl);

                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            // Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                //  pb.setVisibility(View.GONE);
                //uiUpdate.setText("Output : "+Content);
                imageButton.setImageBitmap(bmp);

                //loadFromServer(Content.toString().trim());

            }
        }

    }
*/

}