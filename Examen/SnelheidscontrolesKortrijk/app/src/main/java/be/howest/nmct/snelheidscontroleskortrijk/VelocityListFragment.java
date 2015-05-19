package be.howest.nmct.snelheidscontroleskortrijk;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.howest.nmct.snelheidscontroleskortrijk.contract.Contract;
import be.howest.nmct.snelheidscontroleskortrijk.data.Month;
import be.howest.nmct.snelheidscontroleskortrijk.data.VelocityData;
import be.howest.nmct.snelheidscontroleskortrijk.loader.VelocityLoader;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class VelocityListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */
    private VelocityListAdapter adapter;
    private VelocityListFragmentListener fragmentListener;
    private ArrayList<String> dropdownList;

    /*
    ** Events
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.adapter = new VelocityListAdapter(getActivity(), R.layout.velocity_row, null, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);

        //Action bar
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(true);

        dropdownList = new ArrayList<String>();
        dropdownList.add("Toon alle");
        for(Month month : Month.values())
        {
            dropdownList.add(month.toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_row, R.id.textViewItem, dropdownList);
        actionBar.setListNavigationCallbacks(arrayAdapter, navigationListener);
    }

    ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener()
    {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId)
        {
            Month month = Month.getMonthByNumber(itemPosition);

            if(month != null)
            {
                adapter.swapCursor(filterCursor(month.getMonthNumber()));
            }

            else
            {
                adapter.swapCursor(VelocityData.getVelocityCursor());
            }

            return false;
        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        this.fragmentListener.ShowVelocityMap((int) id);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            this.fragmentListener = (VelocityListFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement VelocityListFragmentListener");
        }
    }

    /*
    ** Methods
     */
    private Cursor filterCursor(int month)
    {
        Cursor cursor = VelocityData.getVelocityCursor();
        cursor.moveToFirst();

        String[] columns = new String[]
        {
            BaseColumns._ID,
            Contract.VelocityContract.COLUMN_YEAR,
            Contract.VelocityContract.COLUMN_MONTH,
            Contract.VelocityContract.COLUMN_STREET,
            Contract.VelocityContract.COLUMN_ZIPCODE,
            Contract.VelocityContract.COLUMN_CITY,
            Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS,
            Contract.VelocityContract.COLUMN_NUMBER_OF_CARS,
            Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES,
            Contract.VelocityContract.COLUMN_X,
            Contract.VelocityContract.COLUMN_Y
        };

        MatrixCursor newCursor = new MatrixCursor(columns);
        int col1 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_YEAR);
        int col2 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_MONTH);
        int col3 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_STREET);
        int col4 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_ZIPCODE);
        int col5 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_CITY);
        int col6 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS);
        int col7 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_CARS);
        int col8 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES);
        int col9 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_X);
        int col10 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_Y);

        int id = 1;
        do
        {
            if(cursor.getInt(col2) == month)
            {
                MatrixCursor.RowBuilder row = newCursor.newRow()
                        .add(id)
                        .add(cursor.getString(col1))
                        .add(cursor.getString(col2))
                        .add(cursor.getString(col3))
                        .add(cursor.getString(col4))
                        .add(cursor.getString(col5))
                        .add(cursor.getString(col6))
                        .add(cursor.getString(col7))
                        .add(cursor.getString(col8))
                        .add(cursor.getString(col9))
                        .add(cursor.getString(col10));
                id++;
            }
        } while(cursor.moveToNext());

        return newCursor;
    }

    /*
    ** Loader events
    */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new VelocityLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
        VelocityData.setVelocityCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.swapCursor(null);
    }

    /*
    ** Adapter
     */
    class VelocityListAdapter extends ResourceCursorAdapter
    {
        int layout;

        public VelocityListAdapter(Context context, int layout, Cursor c, int flags)
        {
            super(context, layout, c, flags);
            this.layout = layout;
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

            int col1 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_STREET);
            int col2 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_CITY);
            int col3 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_MONTH);
            int col4 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS);
            int col5 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES);
            int col6 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_CARS);

            viewHolder.textViewStreet.setText(cursor.getString(col1));
            viewHolder.textViewCity.setText(cursor.getString(col2));
            viewHolder.textViewMonth.setText(Month.getMonthByNumber(Integer.parseInt(cursor.getString(col3))).toString());
            viewHolder.textViewNumberOfTrackings.setText("Aantal controles: " + cursor.getString(col4));

            int numberOfOffenses = cursor.getInt(col5);
            int numberOfCars = cursor.getInt(col6);
            double offensesGrade = getOffensesGrade(numberOfCars, numberOfOffenses);

            if(offensesGrade < 0.2)
            {
                viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#2ecc71"));
            }

            else if(offensesGrade >= 0.2 && offensesGrade < 0.3)
            {
                viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#e67e22"));
            }

            else if(offensesGrade >= 0.3)
            {
                viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
        }

        private double getOffensesGrade(int numberOfCars, int numberOfOffenses)
        {
            return (double) numberOfOffenses / (double) numberOfCars;
        }
    }

    /*
    ** ViewHolder
     */
    class ViewHolder
    {
        public RelativeLayout relativeLayout;
        public TextView textViewStreet;
        public TextView textViewCity;
        public TextView textViewMonth;
        public TextView textViewNumberOfTrackings;

        public ViewHolder(View view)
        {
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.mainLayoutContainer);
            this.textViewStreet = (TextView) view.findViewById(R.id.textViewStreet);
            this.textViewCity = (TextView) view.findViewById(R.id.textViewCity);
            this.textViewMonth = (TextView) view.findViewById(R.id.textViewMonth);
            this.textViewNumberOfTrackings= (TextView) view.findViewById(R.id.textViewNumberOfTrackings);
        }
    }

    /*
    ** FragmentListener
     */
    interface VelocityListFragmentListener
    {
        public void ShowVelocityMap(int id);
    }
}
