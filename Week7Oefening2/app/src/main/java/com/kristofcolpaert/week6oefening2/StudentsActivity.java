package com.kristofcolpaert.week6oefening2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.kristofcolpaert.week6oefening2.data.Student;


public class StudentsActivity extends ActionBarActivity implements StudentsFragment.StudentsFragmentListener
    , GradeFragment.GradeFragmentListener
{
    /*
    ** Fields
     */

    public static final String EXTRA_EMAIL = "com.kristofcolpaert.week6oefening2.EXTRA_EMAIL";
    public static final String EXTRA_GRADE = "com.kristofcolpaert.week6oefening2.EXTRA_GRADE";
    public static final String STUDENTSFRAGMENTTAG = "com.kristofcolpaert.week6oefening2.STUDENTSFRAGMENTTAG";

    public Student.DIPLOMAGRAAD diplomagraad;

    public DrawerLayout drawerLayout;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, StudentsFragment.newInstance(), STUDENTSFRAGMENTTAG)
                .addToBackStack(null).commit();
        }
    }

    /*
    ** Interface implementations
     */

    @Override
    public void showStudentDetails(String email)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        StudentDetailsFragment studentDetailsFragment = StudentDetailsFragment.newInstance(email);
        fragmentTransaction.replace(R.id.container, studentDetailsFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void showStudentsFragment(Student.DIPLOMAGRAAD diplomagraad)
    {
        this.diplomagraad = diplomagraad;

        StudentsFragment studentsFragment = (StudentsFragment) getSupportFragmentManager().findFragmentByTag(STUDENTSFRAGMENTTAG);
        studentsFragment.setDiplomagraad(this.diplomagraad);

        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
