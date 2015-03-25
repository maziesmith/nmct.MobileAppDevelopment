package com.kristofcolpaert.week5oefening3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kristofcolpaert on 25/03/15.
 */
public class ChangeFragment extends Fragment
{
    /*
    ** Fields
     */
    private EditText editTextRate;
    private Button buttonChange;

    /*
    ** Events
     */

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_rate, container, false);

        this.editTextRate = (EditText) view.findViewById(R.id.editTextRate);
        this.buttonChange = (Button) view.findViewById(R.id.buttonChangeRate);
        return view;
    }
}
