package com.example.poornima.clickforchange;

/**
 * Created by poornima on 9/9/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CheckLoginActivity extends AsyncTask<String,Void,String>{

    private static final String LOG_TAG = "SIGN IN";
    private Context context;
    private TextView statusField;
    private String statusText;

    //flag 0 means get and 1 means post.(By default it is get.)
    public CheckLoginActivity(Context context, TextView statusField) {

        this.context = context;
        this.statusField = statusField;

        /*this.roleField = roleField;*/
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... params) {

        try{

            String username = params[0];

            String password = params[1];

            String link="http://172.31.77.196/Click4Change/checkLogin.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);

            }
            statusText = sb.toString();
            Log.v(LOG_TAG,statusText);
            //Toast.makeText(this.context, statusText, Toast.LENGTH_SHORT).show();


        }
        catch(Exception e){
            Log.e("ERROR:",e.toString());
        }

        return statusText;
    }

    @Override
    protected void onPostExecute(String result){
        /*Toast.makeText(this.context, statusText, Toast.LENGTH_SHORT);
        this.statusField.setText(statusText);*/

    }
}
