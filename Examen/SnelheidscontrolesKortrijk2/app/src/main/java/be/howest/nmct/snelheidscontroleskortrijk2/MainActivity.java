package be.howest.nmct.snelheidscontroleskortrijk2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements SnelheidsControlesFragment.SnelheidsControlesFragmentListener
{
    /*
    ** Fields
     */

    public static final String EXTRA_ID = "be.howest.nmct.snelheidscontroleskortrijk2.EXTRA_ID";

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SnelheidsControlesFragment snelheidsControlesFragment = SnelheidsControlesFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, snelheidsControlesFragment)
                .addToBackStack(null)
                .commit();
    }

    /*
    ** Interface implementations
     */

    @Override
    public void showSnelheidsControlesMap(int id)
    {
        SnelheidsControlesMapFragment snelheidsControlesMapFragment = SnelheidsControlesMapFragment.newInstance(id);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, snelheidsControlesMapFragment)
                .addToBackStack(null)
                .commit();
    }
}
