package com.kristofcolpaert.week6oefening1bis;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kristofcolpaert.week6oefening1bis.data.Data;

/**
 * Created by kristofcolpaert on 31/03/15.
 */
public class MainFragment extends Fragment
{
    /*
    ** Fields
     */

    private EditText editTextFirstname;
    private EditText editTextName;
    private Button buttonYear;
    private Button buttonHoroscope;
    private TextView textViewYear;
    private ImageView imageViewHoroscope;

    private Data.Horoscoop horoscope;
    private String year;

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
        bundle.putString(MainActivity.EXTRA_BIRTHYEAR, year);
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
            this.year = getArguments().getString(MainActivity.EXTRA_BIRTHYEAR, "1900");
            String tempHoroscope = getArguments().getString(MainActivity.EXTRA_HOROSCOPE);
            this.horoscope = Data.Horoscoop.getHoroscoopByName(tempHoroscope);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        editTextFirstname = (EditText) v.findViewById(R.id.editTextFirstname);
        editTextName = (EditText) v.findViewById(R.id.editTextName);
        textViewYear = (TextView) v.findViewById(R.id.textViewYear);
        imageViewHoroscope = (ImageView) v.findViewById(R.id.imageViewHoroscope);

        buttonYear = (Button) v.findViewById(R.id.buttonYear);
        buttonYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragmentListener.showBirthYearFragment();
            }
        });

        buttonHoroscope = (Button) v.findViewById(R.id.buttonHoroscope);
        buttonHoroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragmentListener.showHoroscopeFragment();
            }
        });

        showBirthYear();
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

    private void showBirthYear()
    {
        this.textViewYear.setText("Uw geboortejaar: " + this.year);
    }

    private void showHoroscope()
    {
        this.imageViewHoroscope.setImageResource(this.horoscope.getDrawable());
    }

    /*
    ** Interface
     */

    public interface MainFragmentListener
    {
        public void showBirthYearFragment();
        public void showHoroscopeFragment();
    }
}
