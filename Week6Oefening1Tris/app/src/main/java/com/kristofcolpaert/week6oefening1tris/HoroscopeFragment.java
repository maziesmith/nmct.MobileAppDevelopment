package com.kristofcolpaert.week6oefening1tris;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kristofcolpaert.week6oefening1tris.Data.Data;

/**
 * Created by kristofcolpaert on 01/04/15.
 */
public class HoroscopeFragment extends ListFragment
{
    /*
    ** Fields
     */

    private ListAdapter listAdapter;
    private HoroscopeFragmentListner horoscopeFragmentListner;

    /*
    ** Constructor
     */
    public HoroscopeFragment()
    { }

    public static HoroscopeFragment newInstance()
    {
        HoroscopeFragment horoscopeFragment = new HoroscopeFragment();
        return horoscopeFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.listAdapter = new HoroscopeAdapter();
        setListAdapter(listAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            this.horoscopeFragmentListner = (HoroscopeFragmentListner) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement HoroscopeFragment");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Data.Horoscoop horoscope = Data.Horoscoop.values()[position];
        this.horoscopeFragmentListner.showMainFragment(horoscope);
    }

    /*
    ** Class
     */

    public class HoroscopeAdapter extends ArrayAdapter<Data.Horoscoop>
    {
        public HoroscopeAdapter()
        {
            super(getActivity(), R.layout.row_layout, R.id.textViewName, Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();
            if(holder == null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewName = holder.textViewName;
            textViewName.setText(horoscope.getNaamHoroscoop());

            ImageView imageViewIcon = holder.imageViewIcon;
            imageViewIcon.setImageResource(horoscope.getDrawable());

            Button buttonInfo = holder.buttonInfo;
            buttonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), horoscope.getBeginDatum() + " - " + horoscope.getEindDatum(), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }
    }

    public class ViewHolder
    {
        public TextView textViewName;
        public ImageView imageViewIcon;
        public Button buttonInfo;

        public ViewHolder(View row)
        {
            this.textViewName = (TextView) row.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
            this.buttonInfo = (Button) row.findViewById(R.id.buttonInfo);
        }
    }

    /*
    ** Interface
     */
    public interface HoroscopeFragmentListner
    {
        public void showMainFragment(Data.Horoscoop horoscope);
    }
}
