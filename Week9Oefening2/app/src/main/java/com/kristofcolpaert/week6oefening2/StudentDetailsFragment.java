package com.kristofcolpaert.week6oefening2;



import android.content.Context;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kristofcolpaert.week6oefening2.data.Student;
import com.kristofcolpaert.week6oefening2.data.StudentAdmin;
import com.kristofcolpaert.week6oefening2.loader.Contract;
import com.kristofcolpaert.week6oefening2.loader.ModulePuntLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /*
    ** Fields
     */

    private TextView textViewName;
    private TextView textViewFirstname;
    private GridView gridViewScore;
    private TextView textViewScore;

    private String email;
    private Student student;

    private ModulePuntAdapter adapter;

    /*
    ** Constructor
     */

    public StudentDetailsFragment()
    { }

    public static StudentDetailsFragment newInstance(String email)
    {
        StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(StudentsActivity.EXTRA_EMAIL, email);
        studentDetailsFragment.setArguments(bundle);

        return studentDetailsFragment;
    }

    /*
    ** Events
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getActivity() != null)
        {
            Log.d("Activity:", getArguments() + "");
            Bundle bundle = getArguments();
            this.email = bundle.getString(StudentsActivity.EXTRA_EMAIL);
            this.student = StudentAdmin.getStudent(this.email);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_student_details, container, false);

        this.textViewName = (TextView) v.findViewById(R.id.textViewName);
        this.textViewFirstname = (TextView) v.findViewById(R.id.textViewFirstname);
        this.gridViewScore = (GridView) v.findViewById(R.id.gridViewScore);
        this.textViewScore = (TextView) v.findViewById(R.id.textViewScore);

        showStudentDetails();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[]
        {
            Contract.ModulePuntColumns.COLUMN_MODULE_NAAM,
            Contract.ModulePuntColumns.COLUMN_MODULE_SCORE
        };

        int[] viewIds = new int[]{R.id.textViewModule, R.id.textViewModulePunt};

        adapter = new ModulePuntAdapter(getActivity(), R.layout.cel_module, null, columns, viewIds, 0);
        gridViewScore.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new ModulePuntLoader(getActivity(), this.student);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        adapter.swapCursor(null);
    }

    /*
    ** Methods
     */

    private void showStudentDetails()
    {
        this.textViewName.setText(student.getNaamStudent());
        this.textViewFirstname.setText(student.getVoornaamStudent());

        Student.DIPLOMAGRAAD grade = student.getDiplomagraad();

        this.textViewScore.setText(grade.getOmschrijving());

    }

    /*
    ** Adapter
     */

    public class ModulePuntAdapter extends SimpleCursorAdapter
    {
        private int layout;

        public ModulePuntAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent, false);

            TextView textViewModule = (TextView) row.findViewById(R.id.textViewModule);
            TextView textViewModulePunt = (TextView) row.findViewById(R.id.textViewModulePunt);

            int colnrModule = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_NAAM);
            int colnrModulePunt = cursor.getColumnIndex(Contract.ModulePuntColumns.COLUMN_MODULE_SCORE);

            textViewModule.setText(cursor.getString(colnrModule));
            textViewModulePunt.setText("" + cursor.getDouble(colnrModulePunt));

            return row;
        }
    }
}
