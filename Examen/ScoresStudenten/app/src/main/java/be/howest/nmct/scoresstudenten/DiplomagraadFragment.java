package be.howest.nmct.scoresstudenten;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import be.howest.nmct.scoresstudenten.data.Student;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class DiplomagraadFragment extends ListFragment
{
    /*
    ** Fields
     */

    private ListAdapter adapter;

    /*
    ** Events
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_diplomagraad, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Student.DIPLOMAGRAAD[] graden = Student.DIPLOMAGRAAD.values();
        this.adapter = new DiplomagraadAdapter(graden);
        setListAdapter(this.adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Student.DIPLOMAGRAAD[] graden = Student.DIPLOMAGRAAD.values();

        //Clicks doorzenden
        StudentenFragment studentenFragment = (StudentenFragment) getFragmentManager().findFragmentByTag("studentenFragment");
        studentenFragment.setDiplomagraad(graden[position]);

        //Sluit drawer
        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
    }

    /*
    ** Adapter
     */

    class DiplomagraadAdapter extends ArrayAdapter<Student.DIPLOMAGRAAD>
    {
        private Student.DIPLOMAGRAAD[] graden;

        public DiplomagraadAdapter(Student.DIPLOMAGRAAD[] objects)
        {
            super(getActivity(), R.layout.row_diplomagraad, R.id.textViewDiplomagraadItem, objects);
            this.graden = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);

            TextView textViewDiplomagraadItem = (TextView) view.findViewById(R.id.textViewDiplomagraadItem);
            textViewDiplomagraadItem.setText(graden[position].getNaam());

            return view;
        }
    }
}
