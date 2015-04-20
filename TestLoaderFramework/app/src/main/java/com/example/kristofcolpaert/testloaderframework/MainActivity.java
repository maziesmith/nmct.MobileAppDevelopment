package com.example.kristofcolpaert.testloaderframework;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.example.kristofcolpaert.testloaderframework.loader.StringListLoader;

import java.util.List;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<List<String>>
{
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        setListAdapter(listAdapter);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return new StringListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        this.listAdapter.clear();
        this.listAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        this.listAdapter.clear();
    }
}
