package be.howest.nmct.bitcoin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class BitcoinRateFragment extends Fragment
{
    /*
    ** Fields
     */

    private EditText editTextEuroBedrag;
    private EditText editTextBitcoinBedrag;
    private Button buttonToBitcoin;
    private Button buttonToEuro;
    private TextView textViewBitcoinInEuro;
    private Button buttonWijzigKoers;

    private double bitcoinRate;
    public void setBitcoinRate(double bitcoinRate)
    {
        this.bitcoinRate = bitcoinRate;
    }
    private BitcoinRateFragmentListener fragmentListener;

    /*
    ** Constructors
     */

    public BitcoinRateFragment()
    { }

    public static BitcoinRateFragment newInstance(double bitcoinRate)
    {
        BitcoinRateFragment bitcoinRateFragment = new BitcoinRateFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(MainActivity.EXTRA_BITCOIN_RATE, bitcoinRate);
        bitcoinRateFragment.setArguments(bundle);

        return bitcoinRateFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            this.fragmentListener = (BitcoinRateFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement BitcoinRateFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.bitcoinRate = bundle.getDouble(MainActivity.EXTRA_BITCOIN_RATE);

        if(savedInstanceState != null)
        {
            this.editTextEuroBedrag.setText("" + savedInstanceState.getFloat(MainActivity.EXTRA_EURO));
            this.editTextBitcoinBedrag.setText("" + savedInstanceState.getFloat(MainActivity.EXTRA_BITCOIN));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        this.editTextEuroBedrag = (EditText) view.findViewById(R.id.editTextEuroBedrag);
        this.editTextBitcoinBedrag = (EditText) view.findViewById(R.id.editTextBitcoinBedrag);
        this.textViewBitcoinInEuro = (TextView) view.findViewById(R.id.textViewBitcoinInEuro);

        this.buttonToBitcoin = (Button) view.findViewById(R.id.buttonToBitcoin);
        this.buttonToBitcoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calculateBitcoin();
            }
        });

        this.buttonToEuro = (Button) view.findViewById(R.id.buttonToEuro);
        this.buttonToEuro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calculateEuro();
            }
        });

        this.buttonWijzigKoers = (Button) view.findViewById(R.id.buttonWijzigKoers);
        this.buttonWijzigKoers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.ShowChangeFragment(bitcoinRate);
            }
        });

        showBitcoinKoers();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putFloat(MainActivity.EXTRA_EURO, Float.parseFloat(this.editTextEuroBedrag.getText().toString()));
        outState.putFloat(MainActivity.EXTRA_BITCOIN, Float.parseFloat(this.editTextBitcoinBedrag.getText().toString()));

        super.onSaveInstanceState(outState);
    }

    /*
    ** Methods
     */

    private void showBitcoinKoers()
    {
        this.textViewBitcoinInEuro.setText("1 bitcoin = " + bitcoinRate);
    }

    private void calculateBitcoin()
    {
        double euro;
        double bitcoin;

        try
        {
            euro = Double.parseDouble(this.editTextEuroBedrag.getText().toString());
            bitcoin = euro / this.bitcoinRate;

            this.editTextBitcoinBedrag.setText("" + bitcoin);
        }

        catch(Exception ex)
        {
            Toast.makeText(getActivity(), "Het opgegeven bedrag is niet geldig", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateEuro()
    {
        double euro;
        double bitcoin;

        try
        {
            bitcoin = Double.parseDouble(this.editTextBitcoinBedrag.getText().toString());
            euro = bitcoin * this.bitcoinRate;

            this.editTextEuroBedrag.setText("" + euro);
        }

        catch(Exception ex)
        {
            Toast.makeText(getActivity(), "Het opgegeven bedrag is niet geldig", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    ** Fragment listener
     */

    interface BitcoinRateFragmentListener
    {
        public void ShowChangeFragment(double bitcoinRate);
    }
}
