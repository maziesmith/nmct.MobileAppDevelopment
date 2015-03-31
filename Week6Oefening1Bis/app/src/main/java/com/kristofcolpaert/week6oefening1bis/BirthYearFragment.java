package com.kristofcolpaert.week6oefening1bis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kristofcolpaert on 31/03/15.
 */
public class BirthYearFragment extends ListFragment
{
    /*
    ** Fields
     */

    private static final List<String> BIRTHYEARS;
    static
    {
        BIRTHYEARS = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for(int year = 1900; year <= Calendar.getInstance().get(Calendar.YEAR); year++)
        {
            BIRTHYEARS.add("" + year);
        }
    }

    private ListAdapter listAdapter;
    private BirthYearFragmentListener birthYearFragmentListener;

    /*
    ** Constructor
     */

    public BirthYearFragment()
    { }

    public static BirthYearFragment newInstance()
    {
        BirthYearFragment birthYearFragment = new BirthYearFragment();
        return birthYearFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.listAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, BIRTHYEARS);

        setListAdapter(this.listAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            birthYearFragmentListener = (BirthYearFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement BirthYearFragmentListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String birthYear = BIRTHYEARS.get(position);

        birthYearFragmentListener.showMainFragment(birthYear);
    }

    /*
            ** Interface
             */
    public interface BirthYearFragmentListener
    {
        public void showMainFragment(String year);
    }
}
