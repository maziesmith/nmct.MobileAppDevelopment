package com.kristofcolpaert.week5demo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements ShowPersonFragment.EditPersonListener, EditPersonFragment.SavePersonListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Indien null, eerste keer dat fragment gemaakt wordt. Anders restore.
        if(savedInstanceState == null) {
            ShowPersonFragment spf = ShowPersonFragment.newInstance("Kristof Colpaert", "Kriscolp@gmail.com");

            //Nieuwe manier
            FragmentManager fmgr = getFragmentManager();
            fmgr.beginTransaction().add(R.id.container, spf, "show_person").commit();

            //Klassieke manier
            /*
            FragmentTransaction ftr = fmgr.beginTransaction();
            ftr.add(R.id.container, new ShowPersonFragment());
            ftr.commit();
            */
        }
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

    public void onEditPerson()
    {
        FragmentManager fmgr = getFragmentManager();
        fmgr.beginTransaction().replace(R.id.container, EditPersonFragment.newInstance("Kristof Colpaert", "kriscolp@gmail.com")).addToBackStack(null).commit();
    }

    public void onSavePerson()
    {
        FragmentManager fmgr = getFragmentManager();
        fmgr.popBackStack();

        ShowPersonFragment spf = (ShowPersonFragment) fmgr.findFragmentByTag("show_person");
        spf.updateUser(name, email);

    }
}
