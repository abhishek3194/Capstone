package com.example.poornima.clickforchange;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by poornima on 11/9/16.
 */

    public class AddNewMember extends AsyncTask<String,Void,Boolean>

    {
        private static final String LOG_TAG = "REGISTER: ";
        private Context context;

        public AddNewMember(Context context) {
            this.context = context;
        }


        @Override
        protected Boolean doInBackground(String... params) {

            try{

                String username = params[0];
                String password = params[1];
                String phone = params[2];
                String emailAddr = params[3];
                String gender = params[4];

                String link="http://172.31.77.196/Click4Change/addNewMember.php";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(emailAddr, "UTF-8");
                data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");

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
                String statusText = sb.toString();
                Log.v(LOG_TAG, statusText);

                if(statusText.equals("Successful"))
                {
                    return true;
                }

                else
                {
                    return false;
                }
                //Toast.makeText(this.context, statusText, Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Log.e("ERROR:",e.toString());
            }


            return null;
        }
    }

