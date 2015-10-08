package com.example.thomas.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private ArrayAdapter<String> mForecastAdapter;
    private ArrayList<DailyWeather> weathers = new ArrayList<DailyWeather>();

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_refresh)
        {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateWeather()
    {
        FetchWeatherTask fetch = new FetchWeatherTask(mForecastAdapter, weathers);
//            fetch.execute("11792");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String imperial = prefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_metric));
        final String imperialConstant = getString(R.string.pref_units_label_imperial);
//        Log.v("IMPERIAL",imperial);
//        Log.v("IMPERIAL",imperialConstant);

        DailyWeather.imperial =imperial.equals(imperialConstant);

        String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        fetch.execute(location);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        List<String> Weather = new ArrayList<String>();
        //Change the above list to aWeather and then do the below to get a list of days of destruction
//        ArrayList<String> Weather = new ArrayList<String>(aWeather);
//
//        for(int i = 0; i<100; i++)
//        {
//            Weather.add("Day " + i + " - desturction - 0k/0k");
//        }

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                new ArrayList<String>()
        );
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
//        new FetchWeatherTask(mForecastAdapter).execute("11792");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String forecast = mForecastAdapter.getItem(position);
                String forecast = weathers.get(position).toExtendedString();

//                Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
