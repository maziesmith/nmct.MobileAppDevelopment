package be.howest.nmct.snelheidscontroleskortrijk2;

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.howest.nmct.snelheidscontroleskortrijk2.data.Months;
import be.howest.nmct.snelheidscontroleskortrijk2.data.SnelheidsControlesData;
import be.howest.nmct.snelheidscontroleskortrijk2.loader.Contract;
import be.howest.nmct.snelheidscontroleskortrijk2.loader.SnelheidsControlesLoader;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class SnelheidsControlesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private SnelheidsControlesAdapter adapter;
    private SnelheidsControlesFragmentListener fragmentListener;
    private ArrayList<String> dropdownList;

    /*
    ** Constructor
     */

    public SnelheidsControlesFragment()
    { }

    public static SnelheidsControlesFragment newInstance()
    {
        return new SnelheidsControlesFragment();
    }

    /*
    ** Events
     */

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            this.fragmentListener = (SnelheidsControlesFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(getActivity().getClass() + " must implement SnelheidsControlesFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.adapter = new SnelheidsControlesAdapter(getActivity(), R.layout.row_snelheids_controle, null, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);

        //ActionBar
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(true);

        this.dropdownList = new ArrayList<String>();
        this.dropdownList.add("Toon alle");
        this.dropdownList.addAll(Months.getListOfMonthNames());

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spinner, R.id.textViewSpinnerItem, dropdownList);
        actionBar.setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener()
        {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId)
            {
                Months month = Months.getMonthByNumber((int) itemId);

                if(month != null)
                {
                    adapter.swapCursor(filterCursor(month));
                }

                else
                {
                    adapter.swapCursor(SnelheidsControlesData.getSnelheidsControlesCursor());
                }

                return false;
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Cursor cursor = this.adapter.getCursor();
        cursor.moveToPosition(position);
        int col = cursor.getColumnIndex(BaseColumns._ID);
        int tempId = cursor.getInt(col);

        this.fragmentListener.showSnelheidsControlesMap(tempId);
    }

    /*
    ** Methods
     */

    private Cursor filterCursor(Months month)
    {
        String[] columns = new String[]
        {
            BaseColumns._ID,
            Contract.SnelheidsControlesContract.COLUMN_JAAR,
            Contract.SnelheidsControlesContract.COLUMN_MAAND,
            Contract.SnelheidsControlesContract.COLUMN_STRAAT,
            Contract.SnelheidsControlesContract.COLUMN_POSTCODE,
            Contract.SnelheidsControlesContract.COLUMN_GEMEENTE,
            Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES,
            Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN,
            Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING,
            Contract.SnelheidsControlesContract.COLUMN_X,
            Contract.SnelheidsControlesContract.COLUMN_Y
        };

        Cursor cursor = SnelheidsControlesData.getSnelheidsControlesCursor();
        cursor.moveToFirst();

        int col0 = cursor.getColumnIndex(BaseColumns._ID);
        int col1 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_JAAR);
        int col2 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_MAAND);
        int col3 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_STRAAT);
        int col4 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_POSTCODE);
        int col5 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEMEENTE);
        int col6 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES);
        int col7 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
        int col8 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING);
        int col9 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_X);
        int col10 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_Y);

        MatrixCursor newCursor = new MatrixCursor(columns);

        do
        {
            int test1 = cursor.getInt(col2);
            int test2 = month.getMonthNumber();
            if(cursor.getInt(col2) == month.getMonthNumber())
            {
                newCursor.newRow()
                        .add(cursor.getInt(col0))
                        .add(cursor.getInt(col1))
                        .add(cursor.getInt(col2))
                        .add(cursor.getString(col3))
                        .add(cursor.getInt(col4))
                        .add(cursor.getString(col5))
                        .add(cursor.getInt(col6))
                        .add(cursor.getInt(col7))
                        .add(cursor.getInt(col8))
                        .add(cursor.getDouble(col9))
                        .add(cursor.getDouble(col10));
            }
        } while(cursor.moveToNext());

        return newCursor;
    }

    /*
    ** Loader
     */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new SnelheidsControlesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
        SnelheidsControlesData.setSnelheidsControlesCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.swapCursor(null);
    }

    /*
    ** Adapter
     */

    class SnelheidsControlesAdapter extends ResourceCursorAdapter
    {
        public SnelheidsControlesAdapter(Context context, int layout, Cursor c, int flags)
        {
            super(context, layout, c, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            int col1 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_STRAAT);
            int col2 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEMEENTE);
            int col3 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_MAAND);
            int col4 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES);
            int col5 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
            int col6 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING);

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }

            viewHolder.textViewStraat.setText(cursor.getString(col1));
            viewHolder.textViewGemeente.setText(cursor.getString(col2));
            viewHolder.textViewMaand.setText(Months.getMonthByNumber(cursor.getInt(col3)).getMonthName());
            viewHolder.textViewAantalControles.setText("Aantal controles: " + cursor.getInt(col4));

            int gepasseerdeVoertuigen = cursor.getInt(col5);
            int vtgInOvertreding = cursor.getInt(col6);
            double overtredingsGraad = getOvertredingsGraad(gepasseerdeVoertuigen, vtgInOvertreding);

            if(overtredingsGraad < 0.2)
            {
                viewHolder.relativeLayoutMain.setBackgroundColor(Color.parseColor("#2ecc71"));
            }

            else if(overtredingsGraad >= 0.2 && overtredingsGraad < 0.3)
            {
                viewHolder.relativeLayoutMain.setBackgroundColor(Color.parseColor("#e67e22"));
            }

            else if(overtredingsGraad >= 0.3)
            {
                viewHolder.relativeLayoutMain.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
        }

        public double getOvertredingsGraad(int gepasseerdeVoertoegen, int vtgInOvertreding)
        {
            return (double) vtgInOvertreding / (double) gepasseerdeVoertoegen;
        }
    }

    /*
    ** ViewHolder
     */

    class ViewHolder
    {
        public TextView textViewStraat;
        public TextView textViewGemeente;
        public TextView textViewMaand;
        public TextView textViewAantalControles;
        public RelativeLayout relativeLayoutMain;

        public ViewHolder(View view)
        {
            this.textViewStraat = (TextView) view.findViewById(R.id.textViewStraat);
            this.textViewGemeente = (TextView) view.findViewById(R.id.textViewGemeente);
            this.textViewMaand = (TextView) view.findViewById(R.id.textViewMaand);
            this.textViewAantalControles = (TextView) view.findViewById(R.id.textViewAantalControles);
            this.relativeLayoutMain = (RelativeLayout) view.findViewById(R.id.relativeLayoutMain);
        }
    }

    /*
    ** FragmentListener
     */

    interface SnelheidsControlesFragmentListener
    {
        public void showSnelheidsControlesMap(int id);
    }
}
