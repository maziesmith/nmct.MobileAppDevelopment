package com.kristofcolpaert.week4oefening2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private Button buttonLaunch;
    private Button buttonLaunch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        buttonLaunch = (Button) findViewById(R.id.buttonLaunch);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWithAction(v);
            }
        });

        buttonLaunch2 = (Button) findViewById(R.id.buttonLaunch2);
        buttonLaunch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onderzoek4();
            }
        });
    }

    private void launchWithAction(View v)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        //intent.addCategory(Intent.CATEGORY_CAR_DOCK);

        //Verify if it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        Boolean isIntentSafe = activities.size() > 0;

        //Start activity if it resolves
        if(isIntentSafe)
            startActivity(intent);

        else
            Toast.makeText(MainActivity.this, "Activity could not be loaded", Toast.LENGTH_SHORT).show();
    }

    private void onderzoek4()
    {
        Intent intent = new Intent(Constants.ACTION_IMPLY);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        Boolean isIntentSafe = activities.size() > 0;

        //Start activity if it resolves
        if(isIntentSafe)
            startActivity(intent);

        else
            Toast.makeText(MainActivity.this, "Activity could not be loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
