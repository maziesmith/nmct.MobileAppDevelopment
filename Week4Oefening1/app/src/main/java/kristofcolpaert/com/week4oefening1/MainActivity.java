package kristofcolpaert.com.week4oefening1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button buttonLaunch;
    private Button buttonPrompt;
    private EditText userInput;
    private TextView textViewResult;
    private final Context context = this;
    public static final int REQUEST_CODE_EXPLICIT = 1;
    public static final String MY_LAST_NAME = "Joskens";
    public static final String MY_AGE = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSecondActivity();
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

    //Code to start second activity.
    public void startSecondActivity()
    {
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonLaunch = (Button) findViewById(R.id.buttonLaunch);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
                intent.putExtra(ExplicitActivity.EXTRA_INFO, "2NMCT");

                //Gewone startup van de activity.
                //startActivity(intent);

                startActivityForResult(intent, MainActivity.REQUEST_CODE_EXPLICIT);
            }
        });

        buttonPrompt = (Button) findViewById(R.id.buttonPrompt);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View prompt = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(prompt);

                userInput = (EditText) prompt.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        textViewResult.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertdialog = alertDialogBuilder.create();
                alertdialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode)
        {
            case REQUEST_CODE_EXPLICIT:
                switch(resultCode)
                {
                    case RESULT_OK:
                        Toast.makeText(MainActivity.this, "User selects OK", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "User canceled", Toast.LENGTH_SHORT).show();
                        break;
                    case ExplicitActivity.MY_RESULT_CODE:
                        String lastName = data.getStringExtra(MainActivity.MY_LAST_NAME);
                        int age = data.getIntExtra(MainActivity.MY_AGE, 0);
                        Toast.makeText(MainActivity.this, "User joepied " + lastName + ", " + age , Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }
}
