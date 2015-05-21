package be.howest.nmct.scoresstudenten.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class StudentLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor studentCursor;
    private String[] columns;
    private Student.DIPLOMAGRAAD diplomagraad;

    public StudentLoader(Context context)
    {
        super(context);

        columns = new String[]
        {
            BaseColumns._ID,
            Contract.StudentContract.COLUMN_NAAM,
            Contract.StudentContract.COLUMN_VOORNAAM,
            Contract.StudentContract.COLUMN_EMAIL,
            Contract.StudentContract.COLUMN_SCORE_TOTAAL
        };
    }

    public StudentLoader(Context context, Student.DIPLOMAGRAAD diplomagraad) {
        super(context);
        this.diplomagraad = diplomagraad;

        columns = new String[]
        {
            BaseColumns._ID,
            Contract.StudentContract.COLUMN_NAAM,
            Contract.StudentContract.COLUMN_VOORNAAM,
            Contract.StudentContract.COLUMN_EMAIL,
            Contract.StudentContract.COLUMN_SCORE_TOTAAL
        };
    }

    @Override
    protected void onStartLoading()
    {
        if(studentCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        if(diplomagraad == null)
            return getStudenten();

        else
            return getStudenten(this.diplomagraad);
    }

    private Cursor getStudenten()
    {
        List<Student> studentList = StudentAdmin.getStudenten();
        MatrixCursor cursor = new MatrixCursor(columns);
        int id = 1;

        for(Student student : studentList)
        {
            cursor.newRow()
                    .add(id)
                    .add(student.getNaamStudent())
                    .add(student.getVoornaamStudent())
                    .add(student.getEmailStudent())
                    .add(student.getTotaleScoreStudent());
            id++;
        }

        this.studentCursor = cursor;
        return cursor;
    }

    private Cursor getStudenten(Student.DIPLOMAGRAAD tempDiplomagraad)
    {
        List<Student> studentList = StudentAdmin.getStudenten();
        MatrixCursor cursor = new MatrixCursor(columns);
        int id = 1;

        for(Student student : studentList)
        {
            if(student.getDiplomagraad() == tempDiplomagraad)
            {
                cursor.newRow()
                        .add(id)
                        .add(student.getNaamStudent())
                        .add(student.getVoornaamStudent())
                        .add(student.getEmailStudent())
                        .add(student.getTotaleScoreStudent());
                id++;
            }
        }

        this.studentCursor = cursor;
        return cursor;
    }
}
