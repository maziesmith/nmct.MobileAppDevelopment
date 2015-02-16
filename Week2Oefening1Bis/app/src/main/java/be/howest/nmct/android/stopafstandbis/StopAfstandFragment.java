package be.howest.nmct.android.stopafstandbis;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopAfstandFragment extends Fragment {

    private SeekBar speedView0;
    private TextView speedView1;
    private SeekBar reactionView0;
    private TextView reactionView1;
    private RadioGroup trackView;
    private Button updateView;
    private TextView distanceView;

    public StopAfstandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand, container, false);

        this.speedView1 = (TextView) v.findViewById(R.id.txbSpeed2);
        this.speedView0 = (SeekBar) v.findViewById(R.id.sbSpeed);
        this.speedView0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.reactionView1 = (TextView) v.findViewById(R.id.txbReaction2);
        this.reactionView0 = (SeekBar) v.findViewById(R.id.sbReaction);
        this.reactionView0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showReaction(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.trackView = (RadioGroup) v.findViewById(R.id.radTrack);
        this.updateView = (Button) v.findViewById(R.id.btnUpdate);
        this.updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDistance(v);
            }
        });

        this.distanceView = (TextView) v.findViewById(R.id.txbDistance2);

        return v;
    }

    private void showSpeed(int speed)
    {
        this.speedView1.setText("" + speed + " km/h");
    }

    private void showReaction(int reaction)
    {
        float tempReaction = (float) reaction / 10;
        this.reactionView1.setText("" + tempReaction + " sec");
    }

    private void showDistance(View v)
    {
        double speed = (double) speedView0.getProgress() / 3.6;
        float reaction = (float) reactionView0.getProgress() / 10;

        int trackId = trackView.getCheckedRadioButtonId();
        RadioButton trackType = (RadioButton) trackView.findViewById(trackId);
        int trackIndex = trackView.indexOfChild(trackType);

        Track track = Track.WETTRACK;
        if(trackIndex == 0)
            track = Track.DRYTRACK;

        System.out.println(speed + "  " + reaction);

        double distance = speed * reaction + (Math.pow(speed, 2) / (2 * track.getBrakespeed()));

        this.distanceView.setText("" + Math.round(distance) + " meter");
    }

    private enum Track
    {
        DRYTRACK(8),
        WETTRACK(5);

        private int brakespeed;

        public int getBrakespeed() {
            return brakespeed;
        }

        Track(int tempBrakespeed)
        {
            this.brakespeed = tempBrakespeed;
        }
    }
}
