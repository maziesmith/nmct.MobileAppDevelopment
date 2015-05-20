package be.howest.nmct.horoscoop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class GeboortejaarFragment extends ListFragment
{
    /*
    ** Fields
     */

    private ArrayList<String> years;
    private ListAdapter adapter;
    private GeboortejaarFragmentListener fragmentListener;

    /*
    ** Constructors
     */

    public GeboortejaarFragment()
    { }

    public static GeboortejaarFragment newInstance()
    {
        return new GeboortejaarFragment();
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
            this.fragmentListener = (GeboortejaarFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement GeboortejaarFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addYears();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, years);
        setListAdapter(this.adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        String jaar = years.get(position);
        this.fragmentListener.showHomeFragment(jaar);
    }

    /*
    ** Methods
     */

    private void addYears()
    {
        years = new ArrayList<>();
        int currentYear = new Date().getYear();

        for(int i = 1900; i <= 1900 + currentYear; i++)
        {
            years.add("" + i);
        }
    }

    /*
    ** Fragment listner
     */

    interface GeboortejaarFragmentListener
    {
        void showHomeFragment(String jaar);
    }
}
