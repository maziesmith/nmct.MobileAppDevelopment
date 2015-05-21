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
import android.widget.Toast;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class ChangeFragment extends Fragment
{
    /*
    ** Fields
     */

    private double bitcoinRate;
    private ChangeFragmentListener fragmentListener;
    private EditText editTextBitcoinToEuro;
    private Button buttonWijzigen;

    /*
    ** Constructors
     */

    public ChangeFragment()
    { }

    public static ChangeFragment newInstance(double bitcoinRate)
    {
        ChangeFragment changeFragment = new ChangeFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(MainActivity.EXTRA_BITCOIN_RATE, bitcoinRate);
        changeFragment.setArguments(bundle);
        return changeFragment;
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
            this.fragmentListener = (ChangeFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement ChangeFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.bitcoinRate = bundle.getDouble(MainActivity.EXTRA_BITCOIN_RATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_change, container, false);

        this.editTextBitcoinToEuro = (EditText) view.findViewById(R.id.editTextBitcoinToEuro);
        this.buttonWijzigen = (Button) view.findViewById(R.id.buttonWijzigen);
        this.buttonWijzigen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    bitcoinRate = Double.parseDouble(editTextBitcoinToEuro.getText().toString());
                    fragmentListener.ShowBitcoinRateFragment(bitcoinRate);
                }

                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), "Geef een geldig bedrag op", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showBitcoinRate();

        return view;
    }

    /*
    ** Methods
     */

    private void showBitcoinRate()
    {
        this.editTextBitcoinToEuro.setText("" + this.bitcoinRate);
    }

    /*
    ** Fragment listener
     */

    interface ChangeFragmentListener
    {
        public void ShowBitcoinRateFragment(double bitcoinRate);
    }
}
