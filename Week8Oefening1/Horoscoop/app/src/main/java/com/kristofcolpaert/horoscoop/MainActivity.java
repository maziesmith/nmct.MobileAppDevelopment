package com.kristofcolpaert.horoscoop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kristofcolpaert.horoscoop.Data.Data;


public class MainActivity extends ActionBarActivity
{
    /*
    ** Fields
    */

    private EditText editTextFirstname;
    private EditText editTextName;
    private Button buttonYear;
    private Button buttonHoroscope;
    private TextView textViewYear;
    private ImageView imageViewHoroscope;

    public static final String EXTRA_BIRTHYEAR = "com.kristofcolpaert.week6oefening1.BIRTHYEAR";
    public static final String EXTRA_HOROSCOPE = "com.kristofcolpaert.week6oefening1.HOROSCOPE";
    public static final int REQUEST_BIRTHYEAR = 1;
    public static final int REQUIST_HOROSCOPE = 2;

    private String birthYear;
    private int horoscopeImage;

    private ShareActionProvider shareActionProvider;

    /*
    ** Events
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        this.editTextName = (EditText) findViewById(R.id.editTextName);
        this.textViewYear = (TextView) findViewById(R.id.textViewYear);
        this.buttonYear = (Button) findViewById(R.id.buttonYear);
        this.buttonHoroscope = (Button) findViewById(R.id.buttonHoroscope);
        this.imageViewHoroscope = (ImageView) findViewById(R.id.imageViewHoroscope);

        this.buttonYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBirthYear(v);
            }
        });
        this.buttonHoroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHoroscope(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_BIRTHYEAR:
                this.birthYear = data.getStringExtra(EXTRA_BIRTHYEAR);
                showBirthYear();
                shareActionProvider.setShareIntent(getShareIntent());
                break;
            case REQUIST_HOROSCOPE:
                this.horoscopeImage = data.getIntExtra(EXTRA_HOROSCOPE, R.drawable.waterman);
                showHoroscopeImage();
                shareActionProvider.setShareIntent(getShareIntent());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // De ShareActionProvider aanmaken.
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        shareActionProvider.setShareIntent(getShareIntent());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    ** Methods
    */

    private Intent getShareIntent()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getTextInfo());
        return intent;
    }

    private String getTextInfo()
    {
        Data.Horoscoop horoscope = Data.Horoscoop.getHoroscopeByDrawable(this.horoscopeImage);
        if(birthYear != null && horoscope != null)
        {
            String text = "Geboortejaar: " + birthYear;
            text += "\n Horoscoop: " + horoscope.getNaamHoroscoop();
            return text;
        }
        return "";
    }

    private void selectBirthYear(View v)
    {
        Intent intent = new Intent(MainActivity.this, BirthYearActivity.class);
        startActivityForResult(intent, REQUEST_BIRTHYEAR);
    }

    private void selectHoroscope(View v)
    {
        Intent intent = new Intent(MainActivity.this, HoroscopeActivity.class);
        startActivityForResult(intent, REQUIST_HOROSCOPE);
    }

    private void showBirthYear()
    {
        this.textViewYear.setText("Uw geboortejaar: " + this.birthYear);
    }

    private void showHoroscopeImage()
    {
        this.imageViewHoroscope.setImageResource(this.horoscopeImage);
    }
}
