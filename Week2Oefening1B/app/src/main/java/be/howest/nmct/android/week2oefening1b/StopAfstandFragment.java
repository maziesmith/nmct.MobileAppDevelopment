package be.howest.nmct.android.week2oefening1b;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kristofcolpaert on 19/02/15.
 */
public class StopAfstandFragment extends Fragment
{
    private EditText editTextSpeed;
    private EditText editTextReaction;
    private RadioGroup radioGroupTrack;
    private Button buttonDistance;
    private TextView textViewDistance;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand, container, false);

        editTextSpeed = (EditText) v.findViewById(R.id.editTextSpeed);
        editTextReaction = (EditText) v.findViewById(R.id.editTextReaction);
        radioGroupTrack = (RadioGroup) v.findViewById(R.id.radioGroupTrack);
        buttonDistance = (Button) v.findViewById(R.id.buttonUpdate);
        textViewDistance = (TextView) v.findViewById(R.id.textViewDistance1);

        buttonDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDistance();
            }
        });

        return v;
    }

    private void ShowDistance()
    {
        double speed = Double.parseDouble(editTextSpeed.getText().toString()) / 3.6;
        double reaction = Double.parseDouble(editTextReaction.getText().toString());

        int selectedId = radioGroupTrack.getCheckedRadioButtonId();
        RadioButton selectedTrack = (RadioButton) radioGroupTrack.findViewById(selectedId);
        int selectedIndex = radioGroupTrack.indexOfChild(selectedTrack);

        Track trackType = Track.DRY;
        switch(selectedIndex)
        {
            case 0:
                trackType = Track.DRY;
                break;
            case 1:
                trackType = Track.WET;
                break;
        }

        //Stopafstand = snelheid * reactietijd + (snelheid)2 / (2 * remvertraging )
        double distance = (speed * reaction + (Math.pow(speed, 2) / (2 * trackType.getBrakingDistance()))) * 100;
        distance = Math.round(distance);
        distance = distance / 100;

        textViewDistance.setText(distance + " meter");

        showToast(distance);
    }

    private void showToast(double distance)
    {
        Context context = getActivity().getApplicationContext();
        String text = distance + " meter";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private enum Track
    {
        DRY(8.0), WET(5.0);

        private double brakingDistance;

        public double getBrakingDistance()
        {
            return brakingDistance;
        }

        Track(double tempTrackingDistance)
        {
            this.brakingDistance = tempTrackingDistance;
        }
    }
}