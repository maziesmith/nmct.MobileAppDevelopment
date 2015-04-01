package com.kristofcolpaert.week6oefening1tris;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.kristofcolpaert.week6oefening1tris.Data.Data;


public class MainActivity extends ActionBarActivity implements MainFragment.MainFragmentListener,
        YearFragment.YearFragmentListener, HoroscopeFragment.HoroscopeFragmentListner
{
    /*
    ** Fields
     */

    public static final String EXTRA_HOROSCOPE = "com.kristofcolpaert.week6oefening1tris.EXTRA_HOROSCOPE";
    public static final String EXTRA_YEAR = "com.kristofcolpaert.com.week6oefening1tris.EXTRA_YEAR";

    private String year = "1900";
    private Data.Horoscoop horoscope = Data.Horoscoop.BOOGSCHUTTER;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(horoscope, year))
                    .commit();
        }
    }

    /*
    ** Interface implementations
     */

    @Override
    public void showYearFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        YearFragment yearFragment = YearFragment.newInstance();
        fragmentTransaction.replace(R.id.container, yearFragment).addToBackStack(null).commit();
    }

    @Override
    public void showHoroscopeFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HoroscopeFragment horoscopeFragment = HoroscopeFragment.newInstance();
        fragmentTransaction.replace(R.id.container, horoscopeFragment).addToBackStack(null).commit();
    }

    @Override
    public void showMainFragment(String year) {
        this.year = year;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = MainFragment.newInstance(this.horoscope, this.year);
        fragmentTransaction.replace(R.id.container, mainFragment).addToBackStack(null).commit();
    }

    @Override
    public void showMainFragment(Data.Horoscoop horoscope)
    {
        this.horoscope = horoscope;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = MainFragment.newInstance(this.horoscope, this.year);
        fragmentTransaction.replace(R.id.container, mainFragment).addToBackStack(null).commit();
    }
}
