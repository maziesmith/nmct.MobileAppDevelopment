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
import android.widget.TextView;

/**
 * Created by kristofcolpaert on 12/03/15.
 */
public class ChangeFragment extends Fragment
{
    private EditText editTextEuro;
    private EditText editTextBitcoin;
    private Button buttonEuro;
    private Button buttonBitcoin;

    private Button buttonChange;
    private TextView textViewRate;

    private float currentRateBitcoinInEuro;
    private float amountEuro = 1;
    private float amountBitcoin = 1;

    public static final String BITCOIN_RATE = "com.kristofcolpaert.week5Oefening1.NEW_BITCOIN_RATE";
    public static final String AMOUNT_EURO = "com.kristofcolpaert.week5oefening1.AMOUNT_EURO";
    public static final String AMOUNT_BITCOIN = "com.kristofcolpaert.week5oefening1.AMOUNT_BITCOIN";
    public ChangeFragmentListener changeFragmentListener;

    /*
    ** Make instance
     */
    public ChangeFragment()
    { }

    public static ChangeFragment newInstance(float bitcoinRate)
    {
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinRate);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    ** Getters and setters
     */
    public void setCurrentRateBitcoinInEuro(float currentRateBitcoinInEuro) {
        this.currentRateBitcoinInEuro = currentRateBitcoinInEuro;
    }

    /*
    ** Events
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null)
        {
            currentRateBitcoinInEuro = getArguments().getFloat(BITCOIN_RATE, 100f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_change, container, false);

        editTextEuro = (EditText) v.findViewById(R.id.editTextEuro);
        editTextBitcoin = (EditText) v.findViewById(R.id.editTextBitcoin);

        buttonEuro = (Button) v.findViewById(R.id.buttonEuro);
        buttonEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToEuro();
            }
        });

        buttonBitcoin = (Button) v.findViewById(R.id.buttonBitcoin);
        buttonBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBitcoin();
            }
        });

        buttonChange = (Button) v.findViewById(R.id.buttonChange);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentListener.showFragmentBitcoinRate(currentRateBitcoinInEuro);
            }
        });

        textViewRate = (TextView) v.findViewById(R.id.textViewRate);

        showRate();

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //This makes sure that the container activity has implemented
        //the callback interface. If not, it throws an exception.
        try
        {
            changeFragmentListener = (ChangeFragmentListener) activity;
        }

        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()+ " must implement ChangeFragmentListener");
        }
    }

    @Override
    public void onStart() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(this.currentRateBitcoinInEuro == 0.0)
        {
            this.currentRateBitcoinInEuro = sharedPreferences.getFloat(BITCOIN_RATE, 100.0f);
            Log.d("hier", "" + currentRateBitcoinInEuro);
        }

        showRate();
        super.onStart();
    }

    @Override
    public void onStop() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(BITCOIN_RATE, this.currentRateBitcoinInEuro);
        editor.commit();
        super.onStop();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null)
        {
            this.amountEuro = savedInstanceState.getFloat(AMOUNT_EURO, 1.0f);
            this.amountBitcoin = savedInstanceState.getFloat(AMOUNT_BITCOIN, 1.0f);

            this.editTextBitcoin.setText("" + this.amountBitcoin);
            this.editTextEuro.setText("" + this.amountEuro);
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putFloat(AMOUNT_BITCOIN, amountBitcoin);
        outState.putFloat(AMOUNT_EURO, amountEuro);
        super.onSaveInstanceState(outState);
    }

    /*
    ** Methods
    */
    private void showRate()
    {
        textViewRate.setText("1 bitcoin = " + currentRateBitcoinInEuro + " euro");
    }

    private void changeToEuro()
    {
        amountBitcoin = Float.parseFloat(editTextBitcoin.getText().toString());
        float tempAmountEuro = amountBitcoin * currentRateBitcoinInEuro;
        editTextEuro.setText("" + tempAmountEuro);
    }

    private void changeToBitcoin()
    {
        amountEuro = Float.parseFloat(editTextEuro.getText().toString());
        float tempAmountBitcoint = amountEuro / currentRateBitcoinInEuro;
        editTextBitcoin.setText("" + tempAmountBitcoint);
    }

    /*
    ** Interface
     */
    public interface ChangeFragmentListener
    {
        public void showFragmentBitcoinRate(float rate);
    }
}
