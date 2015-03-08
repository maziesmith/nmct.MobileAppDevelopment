package com.kristofcolpaert.week4oefening3;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by kristofcolpaert on 08/03/15.
 */
public class MainFragment extends Fragment
{
    private Button buttonBrowser;
    private Button buttonCall;
    private Button buttonDial;
    private Button buttonGeo;
    private Button buttonContacts;
    private Button buttonEditFirst;
    private Button buttonBMI;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        buttonBrowser = (Button) v.findViewById(R.id.buttonBrowser);
        buttonBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse();
            }
        });

        buttonCall = (Button) v.findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        buttonDial = (Button) v.findViewById(R.id.buttonDial);
        buttonDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dial();
            }
        });

        buttonGeo = (Button) v.findViewById(R.id.buttonGeo);
        buttonGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locate();
            }
        });

        buttonContacts = (Button) v.findViewById(R.id.buttonContacts);
        buttonContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact();
            }
        });

        buttonEditFirst = (Button) v.findViewById(R.id.buttonEditFirst);
        buttonEditFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactFirst();
            }
        });

        buttonBMI = (Button) v.findViewById(R.id.buttonBMI);
        buttonBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi();
            }
        });

        return v;
    }

    //Browse to www.nmct.be
    private void browse()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nmct.be"));
        startActivity(intent);
    }

    //Call to 0478000000
    private void call()
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://0032478000000"));
        startActivity(intent);
    }

    //Dial to 0478000000
    private void dial()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://0032478000000"));
        startActivity(intent);
    }

    //Geo location
    private void locate()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:50.8280300,3.2648700"));
        startActivity(intent);
    }

    //Open contacts list
    private void contact()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people/"));
        startActivity(intent);
    }

    //Show first contact
    //Werkt niet, maar is wel de oplossing zoals het moet volgens developers.android.com
    private void contactFirst()
    {
        Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
        startActivity(intent);
    }

    //Launch BMI activity
    private void bmi()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
