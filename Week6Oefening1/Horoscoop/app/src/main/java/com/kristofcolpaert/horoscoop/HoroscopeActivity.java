package com.kristofcolpaert.horoscoop;

import android.app.ListActivity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kristofcolpaert.horoscoop.Data.*;


public class HoroscopeActivity extends ListActivity {

    /*
    ** Fields
     */
    private HoroscopeAdapter horoscopeAdapter;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.horoscopeAdapter = new HoroscopeAdapter();

        setListAdapter(this.horoscopeAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_HOROSCOPE, horoscope.getDrawable());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_horoscope, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    ** Adapter
     */
    public class HoroscopeAdapter extends ArrayAdapter<Data.Horoscoop>
    {
        public HoroscopeAdapter()
        {
            super(HoroscopeActivity.this, R.layout.row_layout, R.id.textViewName, Data.Horoscoop.values());
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

            TextView textViewName = holder.textviewName;
            textViewName.setText("" + horoscope.getNaamHoroscoop());

            ImageView imageViewIcon = holder.imageViewIcon;
            imageViewIcon.setImageResource(horoscope.getDrawable());

            Button buttonInfo = holder.buttonInfo;
            buttonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), horoscope.getBeginDatum() + " - " + horoscope.getEindDatum(), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }
    }

    /*
    ** ViewHolder class
     */
    public class ViewHolder
    {
        public TextView textviewName = null;
        public ImageView imageViewIcon = null;
        public Button buttonInfo = null;

        public ViewHolder(View row)
        {
            this.textviewName = (TextView) row.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
            this.buttonInfo = (Button) row.findViewById(R.id.buttonInfo);
        }
    }
}
