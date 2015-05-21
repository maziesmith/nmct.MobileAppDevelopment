package be.howest.nmct.scoresstudenten;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity
{
    public static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        StudentenFragment studentenFragment = StudentenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, studentenFragment, "studentenFragment")
                .addToBackStack(null)
                .commit();
    }
}
