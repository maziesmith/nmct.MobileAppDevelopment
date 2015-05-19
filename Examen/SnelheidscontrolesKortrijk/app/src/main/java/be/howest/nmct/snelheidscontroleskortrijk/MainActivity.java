package be.howest.nmct.snelheidscontroleskortrijk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import be.howest.nmct.snelheidscontroleskortrijk.data.VelocityData;


public class MainActivity extends ActionBarActivity implements VelocityListFragment.VelocityListFragmentListener
{
    /*
    ** Fields
     */
    public static final String EXTRA_VELOCITY_ID = "be.howest.nmct.SnelheidscontrolesKortrijk.EXTRA_VELOCITY_ID";

    /*
    ** Events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new VelocityListFragment())
                .addToBackStack(null)
                .commit();
    }

    /*
    ** Fragment listeners
     */
    @Override
    public void ShowVelocityMap(int id)
    {
        VelocityMapFragment velocityMapFragment = VelocityMapFragment.newInstance(id);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, velocityMapFragment)
                .addToBackStack(null)
                .commit();
    }
}
