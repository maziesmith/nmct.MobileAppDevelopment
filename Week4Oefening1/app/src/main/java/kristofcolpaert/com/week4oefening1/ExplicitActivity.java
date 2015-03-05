package kristofcolpaert.com.week4oefening1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExplicitActivity extends ActionBarActivity {

    public static final String EXTRA_INFO = "kristofcolpaert.com.week4oefening1.EXTRA_INFO";
    public static final int MY_RESULT_CODE = 3;
    private TextView textViewAndroid;
    private Button buttonOk;
    private Button buttonCancel;
    private Button buttonJoepie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        startSecondActivity();
    }

    private void startSecondActivity()
    {
        //Extra data opvragen.
        String extraInfo = getIntent().getStringExtra(ExplicitActivity.EXTRA_INFO);
        textViewAndroid = (TextView) findViewById(R.id.textViewAndroid);
        textViewAndroid.setText(extraInfo);

        //Button handlers.
        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Activity stoppen.
                finish();
            }
        });

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                //Activity stoppen.
                finish();
            }
        });

        buttonJoepie = (Button) findViewById(R.id.buttonJoepie);
        buttonJoepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.MY_LAST_NAME, "Colpaert");
                intent.putExtra(MainActivity.MY_AGE, 23);
                setResult(ExplicitActivity.MY_RESULT_CODE, intent);
                //Activity stoppen.
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explicit, menu);
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
