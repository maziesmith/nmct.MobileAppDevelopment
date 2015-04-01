package com.kristofcolpaert.week6oefening1tris;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kristofcolpaert.week6oefening1tris.Data.Data;

/**
 * Created by kristofcolpaert on 01/04/15.
 */
public class MainFragment extends Fragment
{
    /*
    ** Fields
     */

    private EditText editTextName;
    private EditText editTextFirstname;
    private Button buttonYear;
    private Button buttonHoroscope;
    private TextView textViewYear;
    private ImageView imageViewHoroscope;

    private String year;
    private Data.Horoscoop horoscope;

    private MainFragmentListener mainFragmentListener;

    /*
    ** Constructor
     */

    public MainFragment()
    { }

    public static MainFragment newInstance(Data.Horoscoop horoscope, String year)
    {
        MainFragment mainFragment = new MainFragment();

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.EXTRA_HOROSCOPE, horoscope.getNaamHoroscoop());
        bundle.putString(MainActivity.EXTRA_YEAR, year);
        mainFragment.setArguments(bundle);

        return mainFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() != null)
        {
            Bundle bundle = getArguments();
            this.year = bundle.getString(MainActivity.EXTRA_YEAR);
            this.horoscope = Data.Horoscoop.getHoroscopeByName(bundle.getString(MainActivity.EXTRA_HOROSCOPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        this.editTextName = (EditText) v.findViewById(R.id.editTextName);
        this.editTextFirstname = (EditText) v.findViewById(R.id.editTextFirstname);
        this.textViewYear = (TextView) v.findViewById(R.id.textViewYear);
        this.imageViewHoroscope = (ImageView) v.findViewById(R.id.imageViewHoroscope);

        this.buttonYear = (Button) v.findViewById(R.id.buttonYear);
        this.buttonYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragmentListener.showYearFragment();
            }
        });

        this.buttonHoroscope = (Button) v.findViewById(R.id.buttonHoroscope);
        this.buttonHoroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragmentListener.showHoroscopeFragment();
            }
        });

        showYear();
        showHoroscope();

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            this.mainFragmentListener = (MainFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement MainFragmentListener");
        }
    }

    /*
    ** Methods
     */

    private void showYear()
    {
        this.textViewYear.setText("Uw geboortejaar: " + year);
    }

    private void showHoroscope()
    {
        this.imageViewHoroscope.setImageResource(horoscope.getDrawable());
    }

    /*
    ** Interface
    */
    public interface MainFragmentListener
    {
        public void showYearFragment();
        public void showHoroscopeFragment();
    }
}
