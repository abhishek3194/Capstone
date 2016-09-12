package com.example.poornima.clickforchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {


    private static final String LOG_TAG = "LOGIN: ";
    private EditText usernameField,passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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

    public void CallScreen(View view) {
        usernameField = (EditText)findViewById(R.id.user_textView);
        passwordField = (EditText)findViewById(R.id.pass_textView);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        String statusText = null;

        TextView status = (TextView) findViewById(R.id.status);

        try {
            statusText = new CheckLoginActivity(this,status).execute(username, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG,"returned: "+statusText);

        if(statusText.equals("Successful"))
        {
            Intent intent = new Intent(this, NewsFeedActivity.class).putExtra(Intent.EXTRA_TEXT,statusText);
            startActivity(intent);
            //Toast.makeText(this,"Correct!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Invalid username/password",Toast.LENGTH_SHORT).show();
        }

    }

    public void RegisterNewMember(View view) {

        Intent intent = new Intent(this,RegisterMember.class);
        startActivity(intent);

    }
}
