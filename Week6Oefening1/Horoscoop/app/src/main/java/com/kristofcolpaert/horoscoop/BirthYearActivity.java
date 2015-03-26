package com.kristofcolpaert.horoscoop;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BirthYearActivity extends ListActivity
{

    /*
    ** Fields
     */

    private static final List<String> BIRTHYEARS;
    static
    {
        BIRTHYEARS = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for(int year = 1900; year <= Calendar.getInstance().get(Calendar.YEAR); year++)
        {
            BIRTHYEARS.add("" + year);
        }
    }

    private ListAdapter listAdapter;

    /*
    ** Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BIRTHYEARS);

        setListAdapter(this.listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        String birthYear = BIRTHYEARS.get(position);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_BIRTHYEAR, birthYear);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_birth_year, menu);
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
}
