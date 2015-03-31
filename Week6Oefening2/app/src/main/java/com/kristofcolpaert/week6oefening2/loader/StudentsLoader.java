package com.kristofcolpaert.week6oefening2.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import com.kristofcolpaert.week6oefening2.data.Student;
import com.kristofcolpaert.week6oefening2.data.StudentAdmin;

/**
 * Created by kristofcolpaert on 31/03/15.
 */
public class StudentsLoader extends AsyncTaskLoader<Cursor>
{
    /*
    ** Fields
     */
    private Cursor mCursor;

    private final String[] mColumnNames = new String[]
    {
            BaseColumns._ID,
            Contract.StudentColumns.COLUMN_STUDENT_NAME,
            Contract.StudentColumns.COLUMN_STUDENT_FIRSTNAME,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAL
    };

    private static Object lock = new Object();

    /*
    ** Constructor
     */
    public StudentsLoader(Context context)
    {
        super(context);
    }

    /*
    ** Events
     */
    @Override
    protected void onStartLoading() {
        if(mCursor != null)
        {
            deliverResult(mCursor);
        }

        if(takeContentChanged() || mCursor == null)
        {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if(mCursor == null)
        {
            loadCursor();
        }
        return mCursor;
    }

    /*
    ** Methods
     */
    private void loadCursor()
    {
        synchronized (lock)
        {
            if(mCursor != null)
            {
                return;
            }

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;
            for(Student student : StudentAdmin.getInstance().getStudenten())
            {
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(student.getNaamStudent());
                row.add(student.getVoornaamStudent());
                row.add(student.getEmailStudent());
                row.add(student.getTotaleScoreStudent());
                id++;
            }
            mCursor = cursor;
        }
    }
}
