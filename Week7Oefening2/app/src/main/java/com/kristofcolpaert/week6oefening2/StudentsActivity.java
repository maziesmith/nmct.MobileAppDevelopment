package com.kristofcolpaert.week6oefening2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class StudentsActivity extends ActionBarActivity implements StudentsFragment.StudentsFragmentListener
{
    /*
    ** Fields
     */

    public static final String EXTRA_EMAIL = "com.kristofcolpaert.week6oefening2.EXTRA_EMAIL";

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, StudentsFragment.newInstance())
                    .commit();
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
}
