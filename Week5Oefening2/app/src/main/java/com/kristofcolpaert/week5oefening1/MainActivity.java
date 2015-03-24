package com.kristofcolpaert.week5oefening1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends ActionBarActivity implements ChangeFragment.ChangeFragmentListener, BitcoinRateFragment.BitcoinRateFragmentListener {

    private float currenBitcoinRate = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ChangeFragment.newInstance(currenBitcoinRate), "changeFragment")
                    .addToBackStack("start_changeFragment")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();

        else
            super.onBackPressed();
    }

    public void showFragmentBitcoinRate(float rate)
    {
        //Create new transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Replace whatever is in the container view with this fragment,
        //and add the transaction to the back stack.
        Fragment newFragment = BitcoinRateFragment.newInstance(rate);
        fragmentTransaction.replace(R.id.container, newFragment);
        fragmentTransaction.addToBackStack("bitcoinRateFragment");

        //Commit the transaction.
        fragmentTransaction.commit();
    }

    public void showFragmentChange(float rate)
    {
        //Create new transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ChangeFragment fragment = (ChangeFragment) fragmentManager.findFragmentByTag("changeFragment");
        Log.d("hier", "ook");
        fragment.setCurrentRateBitcoinInEuro(rate);

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("changeFragment");

        fragmentTransaction.commit();
    }
}
