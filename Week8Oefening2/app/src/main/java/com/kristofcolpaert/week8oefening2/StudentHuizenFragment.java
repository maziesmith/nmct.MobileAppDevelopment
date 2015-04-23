package com.kristofcolpaert.week8oefening2;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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

    /*
    ** Events
     */

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
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> cursorLoader)
    {
        this.adapter.swapCursor(null);
    }
}
