package com.example.thomas.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 9/29/2015.
 */
public class FetchWeatherTask extends AsyncTask<String, Void ,List<DailyWeather>>
{
    FetchWeatherTask()
    {
        super();
    }

    private ArrayAdapter<String> weather;
    private ArrayList<DailyWeather> dailyWeathers;

    FetchWeatherTask(ArrayAdapter<String> weather)
    {
        super();
        this.weather = weather;
    }

    FetchWeatherTask(ArrayAdapter<String> weather, ArrayList<DailyWeather> dailyWeathers)
    {
        super();
        this.weather = weather;
        this.dailyWeathers = dailyWeathers;
    }

    @Override
    protected List<DailyWeather> doInBackground(String... params)
    {
        return getDataAsList(params[0]);
    }

    public String getDataAsString(String zip)
    {
        final String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?";
//        "http://api.openweathermap.org/data/2.5/forecast/daily?q=11792&mode=json&units=metric&cnt=7";

        final String QUERY_PARAM = "q";
        final String FORMAT_PARAM = "mode";
        final String UNITS_PARAM= "units";
        final String DAY_COUNT = "cnt";

        try {
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, zip)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .appendQueryParameter(UNITS_PARAM, "metric")
                    .appendQueryParameter(DAY_COUNT, "7")
                    .build();

            URL url = new URL(builtUri.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            StringBuilder builder = new StringBuilder();
            if(inputStream!=null)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine())!=null)
                {
                    builder.append(line);
                }
            }
            return builder.toString();
        }
        catch(IOException e)
        {

        }
        return null;
    }

    public List<DailyWeather> getDataAsList(String zip)
    {
        List<DailyWeather> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(getDataAsString(zip));
            JSONArray items = json.getJSONArray("list");
            for(int i = 0; i< items.length(); i++)
            {
                JSONObject j = items.getJSONObject(i);

                JSONArray weather = j.getJSONArray("weather");
                JSONObject temp = j.getJSONObject("temp");

                double min = temp.getDouble("min");
                double max = temp.getDouble("max");
                String Weather = weather.getJSONObject(0).getString("main");
                Date date = new Date(j.getLong("dt")*1000);
                double pressure = j.getDouble("pressure");
                int humidity = j.getInt("humidity");
                String wind = j.getString("speed");
                //DailyWeather dw = new DailyWeather(date,Weather, max, min);
                DailyWeather dw = new DailyWeather(date, Weather, max, min, humidity, pressure, wind);
                list.add(dw);
            }
        } catch (JSONException e)
        {

        }

//        for(DailyWeather dw: list)
//        {
//            Log.v("VERBOSE", "FORECAST: " + dw.toString());
//        }
        return list;
    }

    @Override
    protected void onPostExecute(List<DailyWeather> dailyWeathers) {
        super.onPostExecute(dailyWeathers);
        if(dailyWeathers!=null) {
            weather.clear();
            this.dailyWeathers.clear();
            for (DailyWeather dailyWeather : dailyWeathers) {
                weather.add(dailyWeather.toString());
                this.dailyWeathers.add(dailyWeather);
            }
        }
    }
}
