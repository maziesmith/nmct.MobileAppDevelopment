package be.howest.nmct.studentenhuizen;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import be.howest.nmct.studentenhuizen.data.Contract;
import be.howest.nmct.studentenhuizen.data.StudentHuizenLoader;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class StudentHuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private StudentHuizenAdapter adapter;
    private SearchView searchView;
    private Cursor baseCursor;

    /*
    ** Constructor
     */

    public StudentHuizenFragment()
    { }

    public static StudentHuizenFragment newInstance()
    {
        return new StudentHuizenFragment();
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.adapter = new StudentHuizenAdapter(getActivity(), R.layout.row_student_huis, null, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.my_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        final int resource_edit_text = searchView.getResources().getIdentifier("android:id/search_src_text", null, null);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCursor(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCursor(newText);
                return false;
            }
        });
    }

    private boolean isAlwaysExpanded()
    {
        return true;
    }

    private void filterCursor(String query)
    {
        String[] tempColumnNames = new String[]
                {
                        BaseColumns._ID,
                        Contract.StudentHuizenContract.COLUMN_ADRES,
                        Contract.StudentHuizenContract.COLUMN_HUISNR,
                        Contract.StudentHuizenContract.COLUMN_GEMEENTE,
                        Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS
                };

        MatrixCursor matrixCursor = new MatrixCursor(tempColumnNames);
        int col1 = baseCursor.getColumnIndex(BaseColumns._ID);
        int col2 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_ADRES);
        int col3 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_HUISNR);
        int col4 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_GEMEENTE);
        int col5 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS);

        baseCursor.moveToFirst();
        do
        {
            if(baseCursor.getString(col2).toLowerCase().contains(query.toLowerCase().trim()))
            {
                matrixCursor.newRow()
                        .add(baseCursor.getInt(col1))
                        .add(baseCursor.getString(col2))
                        .add(baseCursor.getString(col3))
                        .add(baseCursor.getString(col4))
                        .add(baseCursor.getString(col5));
            }
        } while (baseCursor.moveToNext());

        this.adapter.swapCursor(matrixCursor);
    }

    /*
    ** Loader
     */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new StudentHuizenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
        this.baseCursor = cursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.swapCursor(null);
    }

    /*
    ** Adapter
     */

    class StudentHuizenAdapter extends ResourceCursorAdapter
    {
        public StudentHuizenAdapter(Context context, int layout, Cursor c, int flags)
        {
            super(context, layout, c, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }

            int col1 = cursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_ADRES);
            int col2 = cursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_HUISNR);
            int col3 = cursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_GEMEENTE);
            int col4 = cursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS);

            viewHolder.textViewStraat.setText(cursor.getString(col1) + " " + cursor.getString(col2));
            viewHolder.textViewGemeente.setText(cursor.getString(col3));
            viewHolder.textViewAantalKamers.setText("Aantal kamers: " + cursor.getInt(col4));
        }
    }

    class ViewHolder
    {
        public TextView textViewStraat;
        public TextView textViewGemeente;
        public TextView textViewAantalKamers;

        public ViewHolder(View view)
        {
            this.textViewStraat = (TextView) view.findViewById(R.id.textViewStraat);
            this.textViewGemeente = (TextView) view.findViewById(R.id.textViewGemeente);
            this.textViewAantalKamers = (TextView) view.findViewById(R.id.textViewAantalKamers);
        }
    }
}
