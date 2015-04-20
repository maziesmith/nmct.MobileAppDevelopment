package com.example.kristofcolpaert.testlistactivity;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class MainActivity extends ListActivity
{
    private String[] data;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.data = new String[]{"Belgium", "France", "Netherlands", "Germany", "Italy", "Spain",
                "Denmark", "Sweden", "Norway", "Greece", "Russia", "Portugal", "Mexico"};

        showCustomView();
    }

    public void showListView()
    {
        this.listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(this.listAdapter);
    }

    public void showCustomView()
    {
        setContentView(R.layout.activity_main);

        this.listAdapter = new ArrayAdapter<String>(this, R.layout.custom_row, R.id.textViewText, data);
        setListAdapter(this.listAdapter);
    }
}