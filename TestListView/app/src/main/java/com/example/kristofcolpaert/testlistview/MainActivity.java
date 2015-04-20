package com.example.kristofcolpaert.testlistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity
{
    /*
    ** Fields
     */
    private ListView listView;
    private ListAdapter listAdapter;

    private GridView gridView;

    private String[] data;

    /*
    ** Events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Activity view instellen.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Array vullen met passende data.
        this.data = new String[]{"Belgium", "France", "Netherlands", "Germany", "Italy", "Spain",
                "Denmark", "Sweden", "Norway", "Greece", "Russia", "Portugal", "Mexico"};

        setCustomView();
    }

    public void setListView()
    {
        //Adapter instellen:
        //- Aangeven dat je gebruik wil maken van de simple_list_item_1 layout (dus met 1 textview).
        //- Aangeven welke data je wil zien.
        this.listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        //Listview declareren.
        this.listView = (ListView) findViewById(R.id.listView);

        //De adapter van de listview instellen op de zojuist geconfigureerde adapter.
        this.listView.setAdapter(listAdapter);
    }

    public void setGridView()
    {
        //Adapter instellen:
        //- Aangeven dat je gebruik wil maken van de simple_list_item_1 layout (dus met 1 textview).
        //- Aangeven welke data je wil zien.
        this.listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        //Gridview declareren.
        this.gridView = (GridView) findViewById(R.id.gridView);

        //De adapter van de gridview instellen op de zojuist geconfigureerde adapter.
        this.gridView.setAdapter(listAdapter);
    }

    public void setCustomView()
    {
        //Adapter instellen:
        //- Aangeven dat je gebruik wil maken van de custom layout (dus met 1 textview).
        //- Aangeven dat de text in textViewText moet komen.
        //- Aangeven welke data je wil zien.
        this.listAdapter = new ArrayAdapter<String>(this, R.layout.custom_row, R.id.textViewText, data);

        //Listview declareren.
        this.listView = (ListView) findViewById(R.id.listView2);

        //De adapter van de listview instellen op de zojuist geconfigureerde adapter.
        this.listView.setAdapter(listAdapter);
    }
}
