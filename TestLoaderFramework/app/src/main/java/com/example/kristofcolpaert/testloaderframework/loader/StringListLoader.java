package com.example.kristofcolpaert.testloaderframework.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristofcolpaert on 20/04/15.
 */
public class StringListLoader extends AsyncTaskLoader<List<String>>
{
    private List<String> listOfStrings;

    public StringListLoader(Context context)
    {
        super(context);
    }

    protected void onStartLoading()
    {
        if(listOfStrings == null)
            forceLoad();
    }

    public List<String> loadInBackground()
    {
        listOfStrings = new ArrayList<>();
        for(int i = 0 ; i < 100; i++)
        {
            listOfStrings.add("String" + i);
        }
        return listOfStrings;
    }
}
