package be.howest.nmct.politiekortrijk;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.howest.nmct.politiekortrijk.data.Contract;
import be.howest.nmct.politiekortrijk.data.Maanden;
import be.howest.nmct.politiekortrijk.data.SnelheidsMetingenData;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class SnelheidsMetingenMapFragment extends Fragment
{
    /*
    ** Fields
     */

    private GoogleMap googleMap;
    private static View view;
    private int cursorPosition;
    private int maand;
    private Menu menu;
    public Menu getMenu()
    {
        return menu;
    }

    public void setCursorPosition(int cursorPosition)
    {
        this.cursorPosition = cursorPosition;
        showMarker();
    }
    /*
    ** Events
     */

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
            view = inflater.inflate(R.layout.fragment_snelheids_metingen_map, container, false);
        }

        catch(InflateException ex)
        {
            Log.d("An error occured: ", ex.getMessage());
        }

        this.googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_show_all);
        item.setVisible(false);

        this.menu = menu;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.action_show_all);

        Cursor tempCursor = SnelheidsMetingenData.getSnelheidsMetingenCursor();
        if(tempCursor != null)
        {
            tempCursor.moveToPosition(this.cursorPosition);
            int col = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_MAAND);
            String maand = Maanden.getMonthByNumber(tempCursor.getInt(col)).toString();

            item.setTitle("Toon alle controles van: " + maand);
            item.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.action_show_all)
        {
            showMarkers();
        }
        return false;
    }

    /*
    ** Methods
     */

    private void showMarker()
    {
        googleMap.clear();

        Cursor tempCursor = SnelheidsMetingenData.getSnelheidsMetingenCursor();
        tempCursor.moveToPosition(this.cursorPosition);

        int col1 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_MAAND);
        int col2 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_STRAAT);
        int col3 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_AANTAL_CONTROLES);
        int col4 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
        int col5 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_VTG_IN_OVERTREDING);
        int col6 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_X);
        int col7 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_Y);

        this.maand = tempCursor.getInt(col1);
        String straat = tempCursor.getString(col2);
        int aantalControles = tempCursor.getInt(col3);
        int gepasseerdeVoertuigen = tempCursor.getInt(col4);
        int vtgInOvertreding = tempCursor.getInt(col5);
        double x = tempCursor.getDouble(col6);
        double y = tempCursor.getDouble(col7);
        double overtredingsGraad = getOvertredingsGraad(gepasseerdeVoertuigen, vtgInOvertreding);

        addMarker(x, y, straat, aantalControles, overtredingsGraad, true);
    }

    private void showMarkers()
    {
        googleMap.clear();
        Cursor tempCursor = SnelheidsMetingenData.getSnelheidsMetingenCursor();
        tempCursor.moveToFirst();

        int col1 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_MAAND);
        int col2 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_STRAAT);
        int col3 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_AANTAL_CONTROLES);
        int col4 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_GEPASSEERDE_VOERTUIGEN);
        int col5 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_VTG_IN_OVERTREDING);
        int col6 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_X);
        int col7 = tempCursor.getColumnIndex(Contract.SnelheidsMetingenContract.COLUMN_Y);

        do
        {
            if(tempCursor.getInt(col1) == this.maand)
            {
                String straat = tempCursor.getString(col2);
                int aantalControles = tempCursor.getInt(col3);
                int gepasseerdeVoertuigen = tempCursor.getInt(col4);
                int vtgInOvertreding = tempCursor.getInt(col5);
                double x = tempCursor.getDouble(col6);
                double y = tempCursor.getDouble(col7);
                double overtredingsGraad = getOvertredingsGraad(gepasseerdeVoertuigen, vtgInOvertreding);

                addMarker(x, y, straat, aantalControles, overtredingsGraad, false);
            }
        } while(tempCursor.moveToNext());
    }

    private void addMarker(double x, double y, String straat, int aantalControles, double overtredingsGraad, boolean isInfo)
    {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(lambert72toWGS84(x, y))
                .title("Aantal controles in: " + straat + ": " + aantalControles)
                .snippet("Overtredingsgraad: " + ((double) Math.round(overtredingsGraad * 1000) / 10) + "%")
                .flat(true);

        CameraPosition cameraPosition;

        if(isInfo)
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

    private double getOvertredingsGraad(int gepasseerdeVoertuigen, int vtgInOvertreding)
    {
        return (double) vtgInOvertreding / (double) gepasseerdeVoertuigen;
    }

    private LatLng lambert72toWGS84(double x, double y) {
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
