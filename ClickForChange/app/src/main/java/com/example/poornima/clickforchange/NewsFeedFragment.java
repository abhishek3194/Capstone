package com.example.poornima.clickforchange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ServerSideAPIs.ServerConfig;
import Utils.FeedAdapter;

/**
 * Created by poornima on 1/9/16.
 */
public class NewsFeedFragment extends Fragment {

    //public ArrayAdapter<String> forecast_adapter;

    public FeedAdapter forecast_adapter;

    private String[] feedArray = {"Monday-Sunny-88/63",
    "Tuesday-Sunny-88/63",
            "Wednesday-Cloudy-88/63",
            "Thursday-Cloudy-88/63",
            "Friday-Rainy-88/63",
            "Saturday-Cloudy-88/63",
            "Sunday-Sunny-88/63",
            "Monday-Sunny-88/63",
            "Tuesday-Sunny-88/63",
            "Wednesday-Cloudy-88/63",
            "Thursday-Cloudy-88/63",
            "Friday-Rainy-88/63",
            "Saturday-Cloudy-88/63",
            "Sunday-Sunny-88/63"};

    public NewsFeedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setHasOptionsMenu(true);
        updateNewsFeed();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateNewsFeed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateNewsFeed() {
        GetImagesForWall wallBuilderTask = new GetImagesForWall();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //String location = prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        //wallBuilderTask.execute(location);

        wallBuilderTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateNewsFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        final String[] forecastArray = {
                "Monday-Sunny-88/63",
                "Tuesday-Sunny-88/63",
                "Wednesday-Cloudy-88/63",
                "Thursday-Cloudy-88/63",
                "Friday-Rainy-88/63",
                "Saturday-Cloudy-88/63",
                "Sunday-Sunny-88/63",
                "Monday-Sunny-88/63",
                "Tuesday-Sunny-88/63",
                "Wednesday-Cloudy-88/63",
                "Thursday-Cloudy-88/63",
                "Friday-Rainy-88/63",
                "Saturday-Cloudy-88/63",
                "Sunday-Sunny-88/63"
        };


        //List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));



        //forecast_adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_newsfeed, R.id.list_item_textview, weekForecast);

        forecast_adapter = new FeedAdapter(getActivity(), feedArray);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setAdapter(forecast_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = feedArray[(Integer)forecast_adapter.getItem(position)];
                //Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewsDetail.class).putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }

        public class GetImagesForWall extends AsyncTask<String,Void,String[]> {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            private static final String LOG_TAG = "THE WALL";

            @Override
            protected String[] doInBackground(String... params) {

                int num_days = 7;

                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast

                    final String FORECAST_BASE_URL = ServerConfig.SERVER+"getImages.php";

                    Uri build_url = Uri.parse(FORECAST_BASE_URL).buildUpon().build();


                    URL url = new URL(build_url.toString());

                    Log.v(LOG_TAG, url.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    forecastJsonStr = buffer.toString();

                    Log.v(LOG_TAG, forecastJsonStr);
                    try {
                        return getWallDataFromJson(forecastJsonStr, num_days);
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Error ", e);
                        // If the code didn't successfully get the weather data, there's no point in attemping
                        // to parse it.
                        return null;
                    }

                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

            }


            private String[] getWallDataFromJson(String forecastJsonStr, int numDays)
                    throws JSONException {


                // These are the names of the JSON objects that need to be extracted.
                final String PROB_LIST = "problems";
                final String PROB_COUNT = "count";


                JSONObject problemsJson = new JSONObject(forecastJsonStr);
                JSONArray problemArray = problemsJson.getJSONArray(PROB_LIST);

                int total_problems = problemsJson.getInt(PROB_COUNT);


                String[] resultStrs = new String[total_problems];
                for (int i = 0; i < total_problems; i++) {
                    // For now, using the format "PATH, USER, LATITUDE, LONGITUDE"
                    String path;
                    String user_id;
                    double latitude;
                    double longitude;

                    // Get the JSON object representing the problem
                    JSONObject problemObject = problemArray.getJSONObject(i);

                    /*path = problemObject.getString(PROB_IMG);

                    user_id = problemObject.getString(USER_ID);

                    latitude = problemObject.getDouble(LATITUDE);

                    longitude = problemObject.getDouble(LONGITUDE);*/

                    //resultStrs[i] = path + " - " + user_id + " - " + latitude + " - " + longitude;

                    resultStrs[i] = problemObject.toString();
                }

                feedArray = resultStrs;

                for (String s : resultStrs) {
                    Log.v(LOG_TAG, "Forecast entry: " + s);
                }
                return resultStrs;
            }


            //@Override
            /*protected void onPostExecute(String[] result) {
                if (result != null) {
                    forecast_adapter.clear();
                    for (String obj : result) {
                        forecast_adapter.add(obj);
                    }
                    ;
                }
            }*/
        }
}