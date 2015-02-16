package be.howest.nmct.android.stopafstand;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopAfstandFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopAfstandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopAfstandFragment extends Fragment {
    //De views die we gaan gebruiken.
    private EditText uwSnelheidView;
    private EditText uwReactietijdView;
    private TextView uwStopafstandView;
    private RadioGroup wegTypeView;
    private Button buttonStopafstand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand, container, false);

        this.uwSnelheidView = (EditText) v.findViewById(R.id.jouw_snelheid);
        this.uwReactietijdView = (EditText) v.findViewById(R.id.jouw_reactietijd);
        this.uwStopafstandView = (TextView) v.findViewById(R.id.jouw_stopafstand);
        this.wegTypeView = (RadioGroup) v.findViewById(R.id.wegdek_type);
        this.buttonStopafstand = (Button) v.findViewById(R.id.bereken_stopafstand);

        this.buttonStopafstand.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View v)
          {
              toonStopAfstand(v);
          }
        });
        return v;
    }

    public void toonStopAfstand(View v)
    {
        int snelheid = Integer.parseInt(this.uwSnelheidView.getText().toString()) * 1000/3600;
        int reactietijd = Integer.parseInt(this.uwReactietijdView.getText().toString());

        int wegdekId = this.wegTypeView.getCheckedRadioButtonId();
        RadioButton wegdekType = (RadioButton) wegTypeView.findViewById(wegdekId);
        int wegdekIndex = this.wegTypeView.indexOfChild(wegdekType);
        Remvertraging remvertraging;

        if(wegdekIndex == 0)
            remvertraging = Remvertraging.DROOGWEGDEK;
        else
            remvertraging = Remvertraging.NATWEGDEK;


        double stopafstand = (double) snelheid * reactietijd + (Math.pow(snelheid, 2) / (2 * remvertraging.getRemvertragingSnelheid()));
        this.uwStopafstandView.setText("" + stopafstand + " meter");
    }

    public enum Remvertraging
    {
        DROOGWEGDEK(8),
        NATWEGDEK(5);

        private int remvertragingSnelheid;

        public int getRemvertragingSnelheid() {
            return remvertragingSnelheid;
        }

        Remvertraging(int tempRemvertragingSnelheid)
        {
            this.remvertragingSnelheid = tempRemvertragingSnelheid;
        }
    }
}
