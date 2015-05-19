package be.howest.nmct.snelheidscontroleskortrijk;

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

import be.howest.nmct.snelheidscontroleskortrijk.contract.Contract;
import be.howest.nmct.snelheidscontroleskortrijk.data.Month;
import be.howest.nmct.snelheidscontroleskortrijk.data.VelocityData;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class VelocityMapFragment extends Fragment
{
    /*
    ** Fields
     */
    private int velocityId;
    private int month;
    private GoogleMap googleMap;
    private static View view;

    /*
    ** Constructors
     */
    public VelocityMapFragment()
    { }

    public static VelocityMapFragment newInstance(int id)
    {
        VelocityMapFragment velocityMapFragment = new VelocityMapFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.EXTRA_VELOCITY_ID, id);

        velocityMapFragment.setArguments(bundle);
        return velocityMapFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.velocityId = bundle.getInt(MainActivity.EXTRA_VELOCITY_ID);
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
            view = inflater.inflate(R.layout.fragment_velocity_map, container, false);
        }

        catch(InflateException ex)
        {
            Log.d("An error occured: ", ex.getMessage());
        }

        this.googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

        showMarker();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Cursor cursor = VelocityData.getVelocityCursor();
        cursor.moveToPosition(velocityId - 1);

        int col = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_MONTH);
        this.month = cursor.getInt(col);
        String tempMonth = Month.getMonthByNumber(cursor.getInt(col)).toString();

        inflater.inflate(R.menu.menu_map, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_show_all);
        menuItem.setTitle("Toon alle controles in " + tempMonth);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.menu_show_all)
        {
            showMarkers();
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    ** Methods
     */

    private void showMarker()
    {
        googleMap.clear();

        Cursor cursor = VelocityData.getVelocityCursor();
        cursor.moveToPosition(velocityId);

        int col1 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_X);
        int col2 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_Y);
        int col3 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_STREET);
        int col4 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS);
        int col5 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_CARS);
        int col6 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES);
        double x = cursor.getDouble(col1);
        double y = cursor.getDouble(col2);
        String street = cursor.getString(col3);
        int numberOfTrackings = cursor.getInt(col4);
        int numberOfCars = cursor.getInt(col5);
        int numberOfOffenses = cursor.getInt(col6);

        addMarkerToScreen(x, y, street, numberOfTrackings, numberOfCars, numberOfOffenses, true);
    }

    private void showMarkers()
    {
        googleMap.clear();

        Cursor cursor = VelocityData.getVelocityCursor();
        cursor.moveToFirst();

        int col1 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_MONTH);
        int col2 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_X);
        int col3 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_Y);
        int col4 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_STREET);
        int col5 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS);
        int col6 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_CARS);
        int col7 = cursor.getColumnIndex(Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES);

        do
        {
            int tempMonth = cursor.getInt(col1);
            if(tempMonth == this.month)
            {
                double x = cursor.getDouble(col2);
                double y = cursor.getDouble(col3);
                String street = cursor.getString(col4);
                int numberOfTrackings = cursor.getInt(col5);
                int numberOfCars = cursor.getInt(col6);
                int numberOfOffenses = cursor.getInt(col7);

                addMarkerToScreen(x, y, street, numberOfTrackings, numberOfCars, numberOfOffenses, false);
            }
        } while(cursor.moveToNext());
    }

    private void addMarkerToScreen(double x, double y, String street, int numberOfTrackings, int numberOfCars, int numberOfOffenses, boolean showInfo)
    {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(lambert72toWGS84(x, y))
                .title("Aantal controlers in " + street + ": " + numberOfTrackings)
                .snippet("Voertuigen: " + numberOfCars + ", Overtredingen: " + numberOfOffenses);

        CameraPosition cameraPosition = null;

        if(showInfo)
        {
            googleMap.addMarker(markerOptions).showInfoWindow();
            cameraPosition = new CameraPosition.Builder()
                .target(lambert72toWGS84(x, y))
                .zoom(14).build();
        }

        else
        {
            googleMap.addMarker(markerOptions);
            cameraPosition = new CameraPosition.Builder()
                .target(lambert72toWGS84(x, y))
                .zoom(12).build();
        }



        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private static LatLng lambert72toWGS84(double x, double y) {
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