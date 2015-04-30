package com.kristofcolpaert.week6oefening2;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kristofcolpaert.week6oefening2.data.Student;
import com.kristofcolpaert.week6oefening2.data.StudentAdmin;
import com.kristofcolpaert.week6oefening2.loader.Contract;
import com.kristofcolpaert.week6oefening2.loader.StudentsLoader;

/**
 * Created by kristofcolpaert on 31/03/15.
 */
public class StudentsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private StudentAdapter mAdapter;
    private StudentsFragmentListener studentsFragmentListener;

    /*
    ** Constructor
     */

    public StudentsFragment()
    { }

    public static StudentsFragment newInstance()
    {
        StudentsFragment studentsFragment = new StudentsFragment();
        return studentsFragment;
    }

    /*
    ** Events
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_students, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[]{
                Contract.StudentColumns.COLUMN_STUDENT_NAME,
                Contract.StudentColumns.COLUMN_STUDENT_FIRSTNAME,
                Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
                Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAL
        };

        int[] viewIds = new int[]{R.id.textViewName, R.id.textViewFirstname, R.id.textViewMail, R.id.textViewScore};

        mAdapter = new StudentAdapter(getActivity(), R.layout.row_student, null, columns, viewIds, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            this.studentsFragmentListener = (StudentsFragmentListener) activity;
        }

        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement StudentsFragmentListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Student student = StudentAdmin.getStudenten().get(position);
        this.studentsFragmentListener.showStudentDetails(student.getEmailStudent());
    }

    /*
    ** Adapter
     */

    public class StudentAdapter extends SimpleCursorAdapter
    {
        private int layout;

        public StudentAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
        {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ImageView imageViewStudent = (ImageView) view.findViewById(R.id.imageViewStudent);
            int colnr = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAL);

            if(cursor.getDouble(colnr) < 8)
            {
                imageViewStudent.setImageResource(R.drawable.student_red);
            }

            else if(cursor.getDouble(colnr) < 10)
            {
                imageViewStudent.setImageResource(R.drawable.student_orange);
            }

            else
            {
                imageViewStudent.setImageResource(R.drawable.student_green);
            }
        }

        /*@Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent, false);

            ImageView imageViewStudent = (ImageView) row.findViewById(R.id.imageViewStudent);

            int colnr = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAL);

            if(cursor.getDouble(colnr) < 8)
            {
                imageViewStudent.setImageResource(R.drawable.student_red);
            }

            else if(cursor.getDouble(colnr) < 10)
            {
                imageViewStudent.setImageResource(R.drawable.student_orange);
            }

            else
            {
                imageViewStudent.setImageResource(R.drawable.student_green);
            }

            return row;
        }*/
    }

    /*
    ** Interface
     */

    public interface StudentsFragmentListener
    {
        public void showStudentDetails(String email);
    }
}
