package com.kristofcolpaert.week6oefening2;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

public class StudentsActivity extends FragmentActivity implements StudentsFragment.StudentsFragmentListener
{
    /*
    ** Fields
     */

    public static final String EXTRA_EMAIL = "com.kristofcolpaert.week6oefening2.EXTRA_EMAIL";
    private StudentsFragment studentenFragment = null;
    private StudentDetailsFragment detailsFragment = null;
    private DrawerLayout drawerLayout = null;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_students);

        if (isTablet(this))
        {
            studentenFragment = (StudentsFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
            detailsFragment = (StudentDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        }

        else
        {
            if(savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, StudentsFragment.newInstance())
                        .addToBackStack(null).commit();
            }
        }
    }

    /*
    ** Methods
     */

    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
