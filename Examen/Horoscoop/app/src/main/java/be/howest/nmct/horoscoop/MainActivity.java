package be.howest.nmct.horoscoop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements HomeFragment.HomeFragmentListener,
        GeboortejaarFragment.GeboortejaarFragmentListener, HoroscoopFragment.HoroscoopFragmentListener
{

    /*
    ** Fields
     */

    public static final String EXTRA_JAAR = "be.howest.nmct.horoscoop.EXTRA_JAAR";
    public static final String EXTRA_HOROSCROOP = "be.howest.nmct.horoscoop.EXTRA_HOROSCOOP";

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = HomeFragment.newInstance("1900", R.drawable.steenbok);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, homeFragment, "homeFragment")
                .addToBackStack(null)
                .commit();
    }

    /*
    ** Interface implementations
     */

    @Override
    public void showGeboortejaarFragment()
    {
        GeboortejaarFragment geboortejaarFragment = GeboortejaarFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, geboortejaarFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showHoroscoopFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new HoroscoopFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showHomeFragment(String jaar)
    {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
        homeFragment.setJaar(jaar);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, homeFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showHomeFragment(int drawable)
    {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
        homeFragment.setHoroscoop(drawable);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, homeFragment)
                .addToBackStack(null)
                .commit();
    }
}
