package be.howest.nmct.horoscoop;

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

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class HomeFragment extends Fragment
{
    /*
    ** Fields
     */

    private EditText editTextNaam;
    private EditText editTextVoornaam;
    private Button buttonGeboortejaar;
    private Button buttonHoroscoop;
    private TextView textViewGeboortejaar;
    private ImageView imageViewIcon;

    private HomeFragmentListener fragmentListener;
    private String jaar;
    public void setJaar(String jaar)
    {
        this.jaar = jaar;
    }

    private int horoscoop;
    public void setHoroscoop(int horoscoop)
    {
        this.horoscoop = horoscoop;
    }

    /*
    ** Constructors
     */

    public HomeFragment()
    { }

    public static HomeFragment newInstance(String jaar, int drawable)
    {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.EXTRA_JAAR, jaar);
        bundle.putInt(MainActivity.EXTRA_HOROSCROOP, drawable);
        homeFragment.setArguments(bundle);
        return homeFragment;
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
            this.fragmentListener = (HomeFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.jaar = bundle.getString(MainActivity.EXTRA_JAAR);
        this.horoscoop = bundle.getInt(MainActivity.EXTRA_HOROSCROOP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.editTextNaam = (EditText) view.findViewById(R.id.editTextNaam);
        this.editTextVoornaam = (EditText) view.findViewById(R.id.editTextVoornaam);

        this.textViewGeboortejaar = (TextView) view.findViewById(R.id.textViewGeboortejaar);
        this.textViewGeboortejaar.setText("Geboortejaar: " + this.jaar);

        this.imageViewIcon = (ImageView) view.findViewById(R.id.imageViewIcon);
        this.imageViewIcon.setImageResource(horoscoop);

        this.buttonGeboortejaar = (Button) view.findViewById(R.id.buttonGeboortejaar);
        this.buttonGeboortejaar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fragmentListener.showGeboortejaarFragment();
            }
        });

        this.buttonHoroscoop = (Button) view.findViewById(R.id.buttonHoroscoop);
        this.buttonHoroscoop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fragmentListener.showHoroscoopFragment();
            }
        });

        return view;
    }

    /*
    ** Fragment listener
     */

    interface HomeFragmentListener
    {
        void showGeboortejaarFragment();
        void showHoroscoopFragment();
    }
}
