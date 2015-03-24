package com.kristofcolpaert.week5oefening1;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kristofcolpaert on 12/03/15.
 */
public class BitcoinRateFragment extends Fragment
{
    private EditText editTextBitcoin;
    private Button buttonChange;
    private float rate1BitcoinInEuros;
    public static final String BITCOIN_RATE = "com.kristofcolpaert.week5Oefening1.NEW_BITCOIN_RATE";
    public BitcoinRateFragmentListener bitcoinRateFragmentListener;

    /*
    ** Make instance
     */
    public BitcoinRateFragment()
    { }

    public static BitcoinRateFragment newInstance(float bitcoinRate)
    {
        BitcoinRateFragment fragment = new BitcoinRateFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinRate);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    ** Events
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bitcoinrate, container, false);

        editTextBitcoin = (EditText) v.findViewById(R.id.editTextBitcoin);
        editTextBitcoin.setText("" + rate1BitcoinInEuros);

        buttonChange = (Button) v.findViewById(R.id.buttonChange);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRate();
                bitcoinRateFragmentListener.showFragmentChange(rate1BitcoinInEuros);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            bitcoinRateFragmentListener = (BitcoinRateFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString()+ " must implement BitcoinRateFragmentListener");
        }
    }

    /*
            ** Methods
             */
    private void getRate()
    {
        rate1BitcoinInEuros = Float.parseFloat(editTextBitcoin.getText().toString());
    }

    /*
    ** Interface
     */
    public interface BitcoinRateFragmentListener
    {
        public void showFragmentChange(float rate);
    }
}
