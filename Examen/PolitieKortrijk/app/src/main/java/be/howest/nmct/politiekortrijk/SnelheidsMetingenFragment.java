package be.howest.nmct.politiekortrijk;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import be.howest.nmct.politiekortrijk.data.Contract;
import be.howest.nmct.politiekortrijk.data.Maanden;
import be.howest.nmct.politiekortrijk.data.SnelheidsMetingenData;
import be.howest.nmct.politiekortrijk.data.SnelheidsMetingenLoader;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class SnelheidsMetingenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private SnelheidsMetingenAdapter adapter;

    /*
    ** Events
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_snelheids_metingen, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.adapter = new SnelheidsMetingenAdapter(getActivity(), R.layout.row_snelheids_meting, null, 0);
        setListAdapter(this.adapter);
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        SnelheidsMetingenMapFragment snelheidsMetingenMapFragment = (SnelheidsMetingenMapFragment) getFragmentManager().findFragmentByTag("snelheidsMetingenMapFragment");
        snelheidsMetingenMapFragment.setCursorPosition(position);

        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
    }

    /*
    ** Loader
     */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new SnelheidsMetingenLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
        SnelheidsMetingenData.setSnelheidsMetingenCursor(cursor);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.swapCursor(null);
    }

    /*
    ** Adapter
     */

    class SnelheidsMetingenAdapter extends ResourceCursorAdapter
    {
        public SnelheidsMetingenAdapter(Context context, int layout, Cursor c, int flags)
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

            int col1 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_STRAAT);
            int col2 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_GEMEENTE);
            int col3 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_MAAND);
            int col4 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_AANTAL_CONTROLES);
            int col5 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
            int col6 = cursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_VTG_IN_OVERTREDING);

            String maandNaam = Maanden.getMonthByNumber(cursor.getInt(col3)).toString();
            String test = cursor.getString(col1);

            viewHolder.textViewStraat.setText(cursor.getString(col1));
            viewHolder.textViewGemeente.setText(cursor.getString(col2));
            viewHolder.textViewMaand.setText(Maanden.getMonthByNumber(cursor.getInt(col3)).toString());
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

            else
            {
                viewHolder.relativeLayoutMain.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
        }

        private double getOvertredingsGraad(int gepasseerdeVoertuigen, int vtgInOvertreding)
        {
            return (double) vtgInOvertreding / (double) gepasseerdeVoertuigen;
        }
    }

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
}
