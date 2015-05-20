package be.howest.nmct.horoscoop;

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

import be.howest.nmct.horoscoop.data.Data;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class HoroscoopFragment extends ListFragment
{
    /*
    ** Fields
     */
    private Data.Horoscoop[] horoscopen;
    private HoroscoopAdapter adapter;
    private HoroscoopFragmentListener fragmentListener;

    /*
    ** Constructors
     */

    public HoroscoopFragment()
    { }

    public static HoroscoopFragment newInstance()
    {
        return new HoroscoopFragment();
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
            this.fragmentListener = (HoroscoopFragmentListener) activity;
        }

        catch(Exception ex)
        {
            throw new ClassCastException(activity.getClass() + " must implement HoroscoopFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.horoscopen = Data.Horoscoop.values();
        this.adapter = new HoroscoopAdapter(this.horoscopen);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Data.Horoscoop horoscoop = horoscopen[position];
        this.fragmentListener.showHomeFragment(horoscoop.getDrawable());
    }

    /*
    ** Adapter
     */

    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop>
    {
        private Data.Horoscoop[] horoscopen;

        public HoroscoopAdapter(Data.Horoscoop[] horoscopen)
        {
            super(getActivity(), R.layout.row_horoscoop, R.id.textViewHoroscoop, horoscopen);
            this.horoscopen = horoscopen;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }

            final Data.Horoscoop horoscoop = horoscopen[position];
            viewHolder.textViewHoroscoop.setText(horoscoop.getNaamHoroscoop());
            viewHolder.imageViewHoroscoop.setImageResource(horoscoop.getDrawable());
            viewHolder.buttonInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

    /*
    ** ViewHolder
     */

    class ViewHolder
    {
        public TextView textViewHoroscoop;
        public ImageView imageViewHoroscoop;
        public Button buttonInfo;

        public ViewHolder(View view)
        {
            this.textViewHoroscoop = (TextView) view.findViewById(R.id.textViewHoroscoop);
            this.imageViewHoroscoop = (ImageView) view.findViewById(R.id.imageViewHoroscoop);
            this.buttonInfo = (Button) view.findViewById(R.id.buttonInfo);
        }
    }

    /*
    ** Fragment Listener
     */

    interface HoroscoopFragmentListener
    {
        void showHomeFragment(int drawable);
    }
}
