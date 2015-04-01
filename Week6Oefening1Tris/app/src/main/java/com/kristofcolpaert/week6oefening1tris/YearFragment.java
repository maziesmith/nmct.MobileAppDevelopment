package com.kristofcolpaert.week6oefening1tris;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kristofcolpaert on 01/04/15.
 */
public class YearFragment extends ListFragment
{
    /*
    ** Fields
     */

    private final static List<String> BIRTHYEARS;
    static
    {
        BIRTHYEARS = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for(int jaar = 1900; jaar < Calendar.getInstance().get(Calendar.YEAR); jaar++)
        {
            BIRTHYEARS.add("" + jaar);
        }
    }

    private ListAdapter listAdapter;
    private YearFragmentListener yearFragmentListener;

    /*
    ** Constructor
     */

    public YearFragment()
    { }

    public static YearFragment newInstance()
    {
        return new YearFragment();
    }

    /*
    ** Events
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.listAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, BIRTHYEARS);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        String birthYear = BIRTHYEARS.get(position);
        this.yearFragmentListener.showMainFragment(birthYear);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            this.yearFragmentListener = (YearFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement YearFragmentListerner");
        }
    }

    /*
    ** Interfaces
    */
    public interface YearFragmentListener
    {
        public void showMainFragment(String year);
    }
}
