package be.howest.nmct.scoresstudenten;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import be.howest.nmct.scoresstudenten.data.Contract;
import be.howest.nmct.scoresstudenten.data.Student;
import be.howest.nmct.scoresstudenten.data.StudentLoader;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class StudentenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private StudentenAdapter adapter;
    private final String DIPLOMAGRAAD = "be.howest.nmct.scoresstudenten.EXTRA_DIPLOMAGRAAD";
    private Student.DIPLOMAGRAAD diplomagraad;
    public void setDiplomagraad(Student.DIPLOMAGRAAD diplomagraad)
    {
        this.diplomagraad = diplomagraad;

        Bundle bundle = new Bundle();
        bundle.putInt(DIPLOMAGRAAD, diplomagraad.ordinal());
        getLoaderManager().restartLoader(1, bundle, this);
    }

    /*
    ** Constructors
     */

    public StudentenFragment()
    { }

    public static StudentenFragment newInstance()
    {
        return new StudentenFragment();
    }

    /*
    ** Events
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.adapter = new StudentenAdapter(getActivity(), R.layout.row_student, null, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);
    }

    /*
    ** Loader
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        if(args == null)
            return new StudentLoader(getActivity());

        else
        {
            Student.DIPLOMAGRAAD graad = Student.DIPLOMAGRAAD.values()[args.getInt(DIPLOMAGRAAD)];
            return new StudentLoader(getActivity(), graad);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        this.adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.swapCursor(null);
    }

    /*
    ** Adapter
     */

    class StudentenAdapter extends ResourceCursorAdapter
    {
        public StudentenAdapter(Context context, int layout, Cursor c, int flags)
        {
            super(context, layout, c, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if(viewHolder == null)
            {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }

            int col1 = cursor.getColumnIndex(Contract.StudentContract.COLUMN_NAAM);
            int col2 = cursor.getColumnIndex(Contract.StudentContract.COLUMN_VOORNAAM);
            int col3 = cursor.getColumnIndex(Contract.StudentContract.COLUMN_EMAIL);
            int col4 = cursor.getColumnIndex(Contract.StudentContract.COLUMN_SCORE_TOTAAL);

            viewHolder.textViewNaam.setText(cursor.getString(col1));
            viewHolder.textViewVoornaam.setText(cursor.getString(col2));
            viewHolder.textviewEmail.setText(cursor.getString(col3));
            int totaalScore = cursor.getInt(col4);
            viewHolder.textViewScore.setText("Score: " + totaalScore);

            if(totaalScore < 8)
            {
                viewHolder.imageViewIcon.setImageResource(R.drawable.student_red);
            }

            else if(totaalScore < 10)
            {
                viewHolder.imageViewIcon.setImageResource(R.drawable.student_orange);
            }

            else
            {
                viewHolder.imageViewIcon.setImageResource(R.drawable.student_green);
            }
        }
    }

    class ViewHolder
    {
        public TextView textViewNaam;
        public TextView textViewVoornaam;
        public TextView textViewScore;
        public TextView textviewEmail;
        public ImageView imageViewIcon;

        public ViewHolder(View view)
        {
            this.textViewNaam = (TextView) view.findViewById(R.id.textViewNaam);
            this.textViewVoornaam = (TextView) view.findViewById(R.id.textViewVoornaam);
            this.textViewScore = (TextView) view.findViewById(R.id.textViewScore);
            this.textviewEmail = (TextView) view.findViewById(R.id.textViewEmail);
            this.imageViewIcon = (ImageView) view.findViewById(R.id.imageViewIcon);
        }
    }
}
