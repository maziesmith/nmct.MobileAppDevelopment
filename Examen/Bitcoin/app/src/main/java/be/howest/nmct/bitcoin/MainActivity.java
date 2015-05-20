package be.howest.nmct.bitcoin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements BitcoinRateFragment.BitcoinRateFragmentListener, ChangeFragment.ChangeFragmentListener
{

    /*
    ** Fields
     */

    public static final String EXTRA_BITCOIN_RATE = "be.howest.nmct.bitcoin.EXTRA_BITCOIN_RATE";
    public static final String EXTRA_EURO = "be.howest.nmct.bitcoin.EXTRA_EURO";
    public static final String EXTRA_BITCOIN = "be.howest.nmct.bitcoin.EXTRA_BITCOIN";
    public static final String EXTRA_BITCOIN_KEY = "be.howest.nmct.bitcoin.EXTRA_BITCOIN_KEY";
    private double bitcoinRate;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(EXTRA_BITCOIN_KEY, MODE_PRIVATE);
        this.bitcoinRate = sharedPreferences.getFloat(EXTRA_BITCOIN_RATE, 100.0f);

        BitcoinRateFragment bitcoinRateFragment = BitcoinRateFragment.newInstance(this.bitcoinRate);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, bitcoinRateFragment, "changeFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            finish();
        }

        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void ShowChangeFragment(double bitcoinRate)
    {
        ChangeFragment changeFragment = ChangeFragment.newInstance(bitcoinRate);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, changeFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void ShowBitcoinRateFragment(double bitcoinRate)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(EXTRA_BITCOIN_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(EXTRA_BITCOIN_RATE, (float) bitcoinRate);
        editor.apply();
        editor.commit();

        BitcoinRateFragment bitcoinRateFragment = (BitcoinRateFragment) getSupportFragmentManager().findFragmentByTag("changeFragment");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, bitcoinRateFragment)
                .addToBackStack(null)
                .commit();
    }
}
