package be.howest.nmct.studentenhuizen;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentHuizenFragment studentHuizenFragment = StudentHuizenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, studentHuizenFragment)
                .addToBackStack(null)
                .commit();
    }
}
