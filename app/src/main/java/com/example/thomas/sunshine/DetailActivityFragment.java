package com.example.thomas.sunshine;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
    private String mForecastStr;
    private final String LOG_TAG = getClass().getSimpleName();

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if(intent!=null&&intent.hasExtra(Intent.EXTRA_TEXT))
        {
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView)rootView.findViewById(R.id.detail_text))
                    .setText(mForecastStr);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Inflat ehte menu; this adds items to the action bar if it is present
        inflater.inflate(R.menu.detailfragment,menu);

        //Retrieve the share menu item
        MenuItem mi = menu.findItem(R.id.action_share);

        //Get the provider and hold onto it to set/change the share intent
        ShareActionProvider mSAP = (ShareActionProvider)MenuItemCompat.getActionProvider(mi);

        //Attach and intent to this share provider.  You can update this at any time, like when the user selects a new piece of data they might like to share
        if(mSAP!=null)
        {
            mSAP.setShareIntent(createShareForecastIntent());
        }
//        else
//        {
//            Log.d(LOG_TAG, "Share Action Provider is null?");
//        }

    }

    private Intent createShareForecastIntent()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr+FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }
}