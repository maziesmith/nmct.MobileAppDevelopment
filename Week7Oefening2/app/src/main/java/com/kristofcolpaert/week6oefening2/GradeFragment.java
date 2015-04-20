package com.kristofcolpaert.week6oefening2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kristofcolpaert.week6oefening2.data.Student;

import org.w3c.dom.Text;

/**
 * Created by kristofcolpaert on 02/04/15.
 */
public class GradeFragment extends ListFragment
{
    /*
    ** Fields
     */
    private ListAdapter listAdapter;
    private GradeFragmentListener gradeFragmentListener;

    /*
    ** Constructor
     */
    public GradeFragment()
    { }

    public static GradeFragment newInstance()
    {
        return new GradeFragment();
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.listAdapter = new GradeAdapter();
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Student.DIPLOMAGRAAD tempDiplomagraad = Student.DIPLOMAGRAAD.values()[position];
        this.gradeFragmentListener.showStudentsFragment(tempDiplomagraad);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            this.gradeFragmentListener = (GradeFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement GradeFragmentListener");
        }
    }

    /*
        ** Adapter
        */
    public class GradeAdapter extends ArrayAdapter<Student.DIPLOMAGRAAD>
    {
        public GradeAdapter()
        {
            super(getActivity(), R.layout.row_grade, R.id.textViewGrade, Student.DIPLOMAGRAAD.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);

            final Student.DIPLOMAGRAAD diplomagraad = Student.DIPLOMAGRAAD.values()[position];
            ViewHolder viewHolder = (ViewHolder) row.getTag();
            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            }

            TextView textViewGrade = viewHolder.textViewGrade;
            textViewGrade.setText(diplomagraad.getNaam());

            ImageView imageViewGrade = viewHolder.imageViewGrade;
            imageViewGrade.setImageResource(R.drawable.grade);

            return row;
        }
    }

    public class ViewHolder
    {
        public TextView textViewGrade;
        public ImageView imageViewGrade;

        public ViewHolder(View row)
        {
            this.textViewGrade = (TextView) row.findViewById(R.id.textViewGrade);
            this.imageViewGrade = (ImageView) row.findViewById(R.id.imageViewGrade);
        }
    }

    /*
    ** Interface
     */
    public interface GradeFragmentListener
    {
        public void showStudentsFragment(Student.DIPLOMAGRAAD diplomagraad);
    }
}
