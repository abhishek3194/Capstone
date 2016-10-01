package com.example.poornima.clickforchange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ServerSideAPIs.UploadClickedImage;


public class NewsFeedActivity extends ActionBarActivity {

    private File destination;
    private String imagePath;


    Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    String pathMedia = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/data/ClickForChange/.data";
    File image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsFeedFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void ClickPicture(View view) {

        String name =   dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");

       image = new File(pathMedia, name+".jpg");

        Uri uriSavedImage = Uri.fromFile(image);

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(imageIntent,1888);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap photo = null;

        photo = (Bitmap) data.getExtras().get("data");

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1888 && resultCode == RESULT_OK) {

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(image);
                if (fos != null) {
                    photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            ImageView imageView = (ImageView)findViewById(R.id.list_item_icon);

            Log.e("CameraDemo", "Pic saved");



            //Log.v("code",encoded);

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = (Location) lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            geoTag(image.getAbsolutePath(),latitude,longitude);



            UploadClickedImage uploader = new UploadClickedImage(photo,this,UserData.SESSION_USER);
            uploader.execute();

            imageView.setImageBitmap(photo);
        }
    }


    public void geoTag(String filename, double latitude, double longitude){

        ExifInterface exif;

        try {
            exif = new ExifInterface(filename);
            int num1Lat = (int)Math.floor(latitude);
            int num2Lat = (int)Math.floor((latitude - num1Lat) * 60);
            double num3Lat = (latitude - ((double)num1Lat+((double)num2Lat/60))) * 3600000;

            int num1Lon = (int)Math.floor(longitude);
            int num2Lon = (int)Math.floor((longitude - num1Lon) * 60);
            double num3Lon = (longitude - ((double)num1Lon+((double)num2Lon/60))) * 3600000;

            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat+"/1,"+num2Lat+"/1,"+num3Lat+"/1000");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon+"/1,"+num2Lon+"/1,"+num3Lon+"/1000");


            if (latitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
            }

            if (longitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
            }

            exif.saveAttributes();
        } catch (IOException e) {
            Log.e("PictureActivity", e.getLocalizedMessage());
        }

    }

    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
            return rootView;
        }
    }
}
