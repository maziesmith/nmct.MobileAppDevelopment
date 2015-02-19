package be.howest.nmct.android.week2demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kristofcolpaert on 19/02/15.
 */
public class WelkomFragment extends Fragment
{
    private Button buttonOK;
    private EditText editTextNaamDocent;
    private TextView textViewWelkom;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo, container, false);

        buttonOK = (Button) v.findViewById(R.id.buttonOK);
        editTextNaamDocent = (EditText) v.findViewById(R.id.editTextNaamDocent);
        textViewWelkom = (TextView) v.findViewById(R.id.textViewWelkom);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toonInfo();
            }
        });

        return v;
    }

    private void toonInfo()
    {
        String naam = editTextNaamDocent.getText().toString();
        if(naam.toLowerCase().startsWith("stijn") || naam.toLowerCase().startsWith("jef"))
            textViewWelkom.setText("Correct antwoord");

        else
            textViewWelkom.setText("Helemaal verkeerd");
    }
}
