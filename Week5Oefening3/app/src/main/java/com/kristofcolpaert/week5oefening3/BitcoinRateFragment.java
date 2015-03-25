package com.kristofcolpaert.week5oefening3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kristofcolpaert on 25/03/15.
 */
public class BitcoinRateFragment extends Fragment
{
    /*
    ** Fields
     */
    private EditText editTextEuro;
    private EditText editTextBitcoin;
    private Button buttonChange;
    private Button buttonToEuro;
    private Button buttonToBitcoin;
    private TextView textViewRate;

    private float currentRate;

    private final static String BITCOIN_RATE = "com.kristofcolpaert.week5oefening3.BITCOIN_RATE";

    /*
    ** Instances and constructors
     */
    public BitcoinRateFragment()
    {}

    public static BitcoinRateFragment newInstance(float rate)
    {
        BitcoinRateFragment bitcoinRateFragment = new BitcoinRateFragment();
        Bundle arguments = new Bundle();
        arguments.putFloat(BITCOIN_RATE, rate);
        bitcoinRateFragment.setArguments(arguments);
        return bitcoinRateFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!getArguments().isEmpty())
            currentRate = getArguments().getFloat(BITCOIN_RATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        this.editTextEuro = (EditText) view.findViewById(R.id.editTextEuro);
        this.editTextBitcoin = (EditText) view.findViewById(R.id.editTextBitcoin);
        this.buttonChange = (Button) view.findViewById(R.id.buttonChange);
        this.buttonToEuro = (Button) view.findViewById(R.id.buttonToEuro);
        this.buttonToBitcoin = (Button) view.findViewById(R.id.buttonToBitcoin);
        this.textViewRate = (TextView) view.findViewById(R.id.textViewRate);

        this.buttonToEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertToEuro();
            }
        });

        this.buttonToBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertToBitcoin();
            }
        });

        showCurrentRate();

        return view;
    }

    /*
    ** Methods
     */

    private void showCurrentRate()
    {
        this.textViewRate.setText("1 bitcoin = " + currentRate + " euro");
    }

    private void convertToEuro()
    {
        float bitcoinAmount = Float.parseFloat(this.editTextBitcoin.getText().toString());
        float euroAmount = bitcoinAmount * currentRate;

        this.editTextEuro.setText("" + euroAmount);
    }

    private void convertToBitcoin()
    {
        float euroAmount = Float.parseFloat(this.editTextEuro.getText().toString());
        float bitcoinAmount = euroAmount / currentRate;

        this.editTextBitcoin.setText("" + bitcoinAmount);
    }
}
