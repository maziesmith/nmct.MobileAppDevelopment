package be.howest.nmct.studentenhuizen;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import be.howest.nmct.studentenhuizen.data.Contract;
import be.howest.nmct.studentenhuizen.data.StudentHuizenLoader;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class StudentHuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private StudentHuizenAdapter adapter;
    private RecyclerView recyclerViewList;
    private SearchView searchView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_student_huizen, container, false);

        this.recyclerViewList = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.my_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
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
        Cursor baseCursor = getad

        String[] tempColumnNames = new String[]
        {
            BaseColumns._ID,
            Contract.StudentHuizenContract.COLUMN_ADRES,
            Contract.StudentHuizenContract.COLUMN_HUISNR,
            Contract.StudentHuizenContract.COLUMN_GEMEENTE,
            Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS
        };

        MatrixCursor matrixCursor = new MatrixCursor(tempColumnNames);
        int col1 =
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
        this.adapter = new StudentHuizenAdapter(cursor);
        recyclerViewList.setAdapter(this.adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        recyclerViewList.setAdapter(null);
    }

    /*
    ** Adapter
     */

    class StudentHuizenAdapter extends RecyclerView.Adapter<StudentHuizenViewHolder>
    {
        private final Cursor studentHuizenCursor;

        public StudentHuizenAdapter(Cursor studentHuizenCursor)
        {
            this.studentHuizenCursor = studentHuizenCursor;
        }

        @Override
        public StudentHuizenViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_huis, parent, false);
            return new StudentHuizenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StudentHuizenViewHolder holder, int position)
        {
            this.studentHuizenCursor.moveToPosition(position);

            int col1 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_ADRES);
            int col2 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_HUISNR);
            int col3 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_GEMEENTE);
            int col4 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS);

            holder.textViewStraat.setText(studentHuizenCursor.getString(col1) + " " + studentHuizenCursor.getString(col2));
            holder.textViewGemeente.setText(studentHuizenCursor.getString(col3));
            holder.textViewAantalKamers.setText("Aantal kamers: " + studentHuizenCursor.getString(col4));
        }

        @Override
        public int getItemCount()
        {
            return studentHuizenCursor.getCount();
        }
    }

    class StudentHuizenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textViewStraat;
        public TextView textViewGemeente;
        public TextView textViewAantalKamers;


        public StudentHuizenViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            this.textViewStraat = (TextView) itemView.findViewById(R.id.textViewStraat);
            this.textViewGemeente = (TextView) itemView.findViewById(R.id.textViewGemeente);
            this.textViewAantalKamers = (TextView) itemView.findViewById(R.id.textViewAantalKamers);
        }

        @Override
        public void onClick(View v)
        {
            TextView tempTextView = (TextView) v.findViewById(R.id.textViewStraat);
            Toast.makeText(getActivity(), tempTextView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
