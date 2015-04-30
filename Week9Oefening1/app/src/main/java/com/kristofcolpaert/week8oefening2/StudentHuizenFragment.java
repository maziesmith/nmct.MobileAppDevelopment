package com.kristofcolpaert.week8oefening2;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import com.kristofcolpaert.week8oefening2.loader.Contract;
import com.kristofcolpaert.week8oefening2.loader.StudentHuizenLoader;

/**
 * Created by kristofcolpaert on 23/04/15.
 */
public class StudentHuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private SimpleCursorAdapter adapter;
    private SearchView searchView;
    private String street;
    private Cursor baseCursor;

    /*
    ** Events
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[]
        {
            Contract.KotColumns.COLUMN_ADRES,
            Contract.KotColumns.COLUMN_HUISNUMMER,
            Contract.KotColumns.COLUMN_GEMEENTE,
            Contract.KotColumns.COLUMN_AANTAL_KAMERS
        };

        int[] viewIds = new int[]
        {
            R.id.textViewAddress,
            R.id.textViewHomeNumber,
            R.id.textViewCity,
            R.id.textViewNumber
        };

        this.adapter = new SimpleCursorAdapter(getActivity(), R.layout.row, null, columns, viewIds);
        setListAdapter(this.adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new StudentHuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> cursorLoader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
        this.baseCursor = cursor;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> cursorLoader)
    {
        this.adapter.swapCursor(null);
    }

    // Intent to Google Maps.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Cursor cursor = this.adapter.getCursor();
        int col1 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_ADRES);
        int col2 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_HUISNUMMER);
        int col3 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_GEMEENTE);

        cursor.moveToPosition(position);
        String adres = cursor.getString(col1);
        int huisnummer = cursor.getInt(col2);
        String gemeente = cursor.getString(col3);
        String tempGeo = "geo:0,0?q=" + adres + "+" + huisnummer + "+" + gemeente;
        Uri geo = Uri.parse(tempGeo);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geo);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        MenuItem searchItem = menu.findItem(R.id.my_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        //Lege string opvangen.
        final int resource_edit_text = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        ((EditText) searchView.findViewById(resource_edit_text)).addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String currentString = s.toString();
                if(currentString.isEmpty())
                {
                    adapter.swapCursor(baseCursor);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String currentString = s.toString();
                if(currentString.isEmpty())
                {
                    adapter.swapCursor(baseCursor);
                }
            }
        });
    }

    /*
    ** Methods
     */

    private void setupSearchView(MenuItem searchItem)
    {
        if(isAlwaysExpanded())
        {
            searchView.setIconifiedByDefault(false);
        }

        else
        {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                street = s;
                filterCursorOnStreet();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                street = s;
                filterCursorOnStreet();
                return false;
            }
        });
    }

    private boolean isAlwaysExpanded()
    {
        return true;
    }

    private void filterCursorOnStreet()
    {
        String[] tempColumnNames = new String[]
        {
                BaseColumns._ID,
                Contract.KotColumns.COLUMN_ADRES,
                Contract.KotColumns.COLUMN_HUISNUMMER,
                Contract.KotColumns.COLUMN_GEMEENTE,
                Contract.KotColumns.COLUMN_AANTAL_KAMERS,
        };

        MatrixCursor newCursor = new MatrixCursor(tempColumnNames);
        int colnr1 = baseCursor.getColumnIndex(Contract.KotColumns.COLUMN_ADRES);
        int colnr2 = baseCursor.getColumnIndex(Contract.KotColumns.COLUMN_HUISNUMMER);
        int colnr3 = baseCursor.getColumnIndex(Contract.KotColumns.COLUMN_GEMEENTE);
        int colnr4 = baseCursor.getColumnIndex(Contract.KotColumns.COLUMN_AANTAL_KAMERS);

        int id = 1;
        if(baseCursor.moveToFirst())
        {
            do
            {
                if(baseCursor.getString(colnr1).toLowerCase().contains(street.toLowerCase().trim()))
                {
                    MatrixCursor.RowBuilder row = newCursor.newRow();
                    row.add(id++);
                    row.add(baseCursor.getString(colnr1));
                    row.add(baseCursor.getString(colnr2));
                    row.add(baseCursor.getString(colnr3));
                    row.add(baseCursor.getString(colnr4));
                }
            } while(baseCursor.moveToNext());
        }

        this.adapter.swapCursor(newCursor);
    }
}
