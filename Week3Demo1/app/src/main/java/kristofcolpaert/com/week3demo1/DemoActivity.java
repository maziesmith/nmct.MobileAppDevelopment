package kristofcolpaert.com.week3demo1;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class DemoActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DemoActivity", "DemoActivity > onCreate");
        setContentView(R.layout.activity_demo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DemoFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DemoActivity","DemoActivity > onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DemoActivity","DemoActivity > onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DemoActivity","DemoActivity > onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DemoActivity","DemoActivity > onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DemoActivity","DemoActivity > onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DemoActivity","DemoActivity > onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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
}
