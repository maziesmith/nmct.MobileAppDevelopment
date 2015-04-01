package com.kristofcolpaert.week6oefening1bis;

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

import com.kristofcolpaert.week6oefening1bis.data.Data;


public class MainActivity extends ActionBarActivity implements BirthYearFragment.BirthYearFragmentListener,
        MainFragment.MainFragmentListener, HoroscopeFragment.HoroScopeFragmentListener
{

    /*
    ** Fields
     */
    public static final String EXTRA_BIRTHYEAR = "com.kristofcolpaert.week6oefening1bis.EXTRA_BIRTHYEAR";
    public static final String EXTRA_HOROSCOPE = "com.kristofcolpaert.week6oefening1bis.EXTRA_HOROSCOPE";

    public Data.Horoscoop horoscope = Data.Horoscoop.BOOGSCHUTTER;
    public String year = "1900";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment().newInstance(horoscope, year))
                    .commit();
        }
    }

    /*
    ** Interface implementation
     */

    @Override
    public void showMainFragment(String year)
    {
        this.year = year;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = MainFragment.newInstance(this.horoscope, this.year);
        fragmentTransaction.replace(R.id.container, mainFragment).addToBackStack(null).commit();
    }

    @Override
    public void showBirthYearFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BirthYearFragment birthYearFragment = BirthYearFragment.newInstance();
        fragmentTransaction.replace(R.id.container, birthYearFragment).addToBackStack(null).commit();
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
    public void showMainFragment(Data.Horoscoop horoscope) {
        this.horoscope = horoscope;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = MainFragment.newInstance(this.horoscope, this.year);
        fragmentTransaction.replace(R.id.container, mainFragment).addToBackStack(null).commit();
    }
}
