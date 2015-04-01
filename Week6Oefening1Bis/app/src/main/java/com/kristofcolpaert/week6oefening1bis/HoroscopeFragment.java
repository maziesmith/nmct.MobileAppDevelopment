package com.kristofcolpaert.week6oefening1bis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kristofcolpaert.week6oefening1bis.data.Data;

/**
 * Created by kristofcolpaert on 01/04/15.
 */
public class HoroscopeFragment extends ListFragment
{
    /*
    ** Fields
     */
    private HoroScopeFragmentListener horoScopeFragmentListener;
    private HoroscopeAdapter horoscopeAdapter;

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

        this.horoscopeAdapter = new HoroscopeAdapter();

        setListAdapter(horoscopeAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            horoScopeFragmentListener = (HoroScopeFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement HoroscopeFragmentListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

        horoScopeFragmentListener.showMainFragment(horoscope);
    }

    /*
    ** Interface
    */
    public interface HoroScopeFragmentListener
    {
        public void showMainFragment(Data.Horoscoop horoscope);
    }

    /*
    ** Adapter
    */
    public class HoroscopeAdapter extends ArrayAdapter<Data.Horoscoop>
    {
        public HoroscopeAdapter() {
            super(getActivity(), R.layout.row_layout, R.id.textViewName, Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

            ViewHolder viewHolder = (ViewHolder) row.getTag();

            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            }

            TextView textviewName = viewHolder.textViewName;
            textviewName.setText("" + horoscope.getNaamHoroscoop());

            ImageView imageViewIcon = viewHolder.imageViewIcon;
            imageViewIcon.setImageResource(horoscope.getDrawable());

            Button buttonInfo = viewHolder.buttonInfo;
            buttonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "" + horoscope.getBeginDatum() + " - " + horoscope.getEindDatum(), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }
    }

    /*
    ** Viewholder class
     */
    public class ViewHolder
    {
        public TextView textViewName = null;
        public Button buttonInfo = null;
        public ImageView imageViewIcon = null;

        public ViewHolder(View row)
        {
            textViewName = (TextView) row.findViewById(R.id.textViewName);
            buttonInfo = (Button) row.findViewById(R.id.buttonInfo);
            imageViewIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
        }
    }
}
