package be.howest.nmct.snelheidscontroleskortrijk2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.howest.nmct.snelheidscontroleskortrijk2.data.Months;
import be.howest.nmct.snelheidscontroleskortrijk2.data.SnelheidsControlesData;
import be.howest.nmct.snelheidscontroleskortrijk2.loader.Contract;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class SnelheidsControlesMapFragment extends Fragment
{
    /*
    ** Fields
     */

    private int id;
    private static View view;
    private GoogleMap googleMap;

    /*
    ** Constructor
     */

    public SnelheidsControlesMapFragment()
    { }

    public static SnelheidsControlesMapFragment newInstance(int id)
    {
        SnelheidsControlesMapFragment snelheidsControlesMapFragment = new SnelheidsControlesMapFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.EXTRA_ID, id);
        snelheidsControlesMapFragment.setArguments(bundle);
        return snelheidsControlesMapFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.id = bundle.getInt(MainActivity.EXTRA_ID);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null)
                parent.removeView(view);
        }

        try
        {
            view = inflater.inflate(R.layout.fragment_snelheids_controles_map, container, false);
        }

        catch(InflateException ex)
        {
            Log.d("An error occured: ", ex.getMessage());
        }

        this.googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        showMarker();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Cursor cursor = SnelheidsControlesData.getSnelheidsControlesCursor();
        cursor.moveToPosition(id - 1);

        int col = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_MAAND);
        int maand = cursor.getInt(col);
        Months month = Months.getMonthByNumber(maand);

        inflater.inflate(R.menu.menu_map, menu);
        MenuItem menuItem = menu.findItem(R.id.action_show_all);
        menuItem.setTitle("Toon alle controles in " + month.getMonthName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_show_all)
        {
            Cursor cursor = SnelheidsControlesData.getSnelheidsControlesCursor();
            cursor.moveToPosition(id - 1);
            int col = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_MAAND);
            int maand = cursor.getInt(col);

            showMarkers(maand);
        }
        return false;
    }

    /*
    ** Methods
    */
    private void showMarker()
    {
        googleMap.clear();

        Cursor cursor = SnelheidsControlesData.getSnelheidsControlesCursor();
        cursor.moveToPosition(id);

        int col1 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_STRAAT);
        int col2 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES);
        int col3 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
        int col4 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING);
        int col5 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_X);
        int col6 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_Y);

        String straat = cursor.getString(col1);
        int aantalControles = cursor.getInt(col2);
        int gepasseerdeVoertuigen = cursor.getInt(col3);
        int vtgInOvertreding = cursor.getInt(col4);
        double x = cursor.getDouble(col5);
        double y = cursor.getDouble(col6);

        addMarker(x, y, straat, aantalControles, gepasseerdeVoertuigen, vtgInOvertreding, true);
    }

    private void showMarkers(int maand)
    {
        googleMap.clear();

        Cursor cursor = SnelheidsControlesData.getSnelheidsControlesCursor();
        cursor.moveToFirst();

        int col1 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_STRAAT);
        int col2 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES);
        int col3 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
        int col4 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING);
        int col5 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_X);
        int col6 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_Y);
        int col7 = cursor.getColumnIndex(Contract.SnelheidsControlesContract.COLUMN_MAAND);

        while(cursor.moveToNext())
        {
            if(maand == cursor.getInt(col7))
            {
                String straat = cursor.getString(col1);
                int aantalControles = cursor.getInt(col2);
                int gepasseerdeVoertuigen = cursor.getInt(col3);
                int vtgInOvertreding = cursor.getInt(col4);
                double x = cursor.getDouble(col5);
                double y = cursor.getDouble(col6);

                addMarker(x, y, straat, aantalControles, gepasseerdeVoertuigen, vtgInOvertreding, false);
            }
        }
    }

    private void addMarker(double x, double y, String straat, int aantalControles, int gepasseerdeVoertuigen, int vtgInOvertreding, boolean showInfo)
    {
        MarkerOptions markerOptions = new MarkerOptions()
            .position(lambert72toWGS84(x, y))
                .title("Aantal controles in " + straat + ": " + aantalControles)
                .snippet("Voertuigen: " + gepasseerdeVoertuigen + ", Overtredingen: " + vtgInOvertreding)
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        CameraPosition cameraPosition;

        if(showInfo)
        {
            googleMap.addMarker(markerOptions).showInfoWindow();
            cameraPosition = new CameraPosition.Builder()
                    .target(lambert72toWGS84(x, y))
                    .zoom(14)
                    .build();
        }

        else
        {
            googleMap.addMarker(markerOptions);
            cameraPosition = new CameraPosition.Builder()
                    .target(lambert72toWGS84(x, y))
                    .zoom(12)
                    .build();
        }

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private LatLng lambert72toWGS84(double x, double y)
    {
        double newLongitude;
        double newLatitude;

        double n = 0.77164219;
        double F = 1.81329763;
        double thetaFudge = 0.00014204;
        double e = 0.08199189;
        double a = 6378388;
        double xDiff = 149910;
        double yDiff = 5400150;

        double theta0 = 0.07604294;

        double xReal = xDiff - x;
        double yReal = yDiff - y;

        double rho = Math.sqrt(xReal * xReal + yReal * yReal);
        double theta = Math.atan(xReal / -yReal);

        newLongitude = (theta0 + (theta + thetaFudge) / n) * 180 / Math.PI;
        newLatitude = 0;

        for (int i = 0; i < 5; ++i) {
            newLatitude = (2 * Math.atan(Math.pow(F * a / rho, 1 / n)
                    * Math.pow(
                    (1 + e * Math.sin(newLatitude))
                            / (1 - e * Math.sin(newLatitude)), e / 2)))
                    - Math.PI / 2;
        }
        newLatitude *= 180 / Math.PI;
        return new LatLng(newLatitude, newLongitude);
    }
}
