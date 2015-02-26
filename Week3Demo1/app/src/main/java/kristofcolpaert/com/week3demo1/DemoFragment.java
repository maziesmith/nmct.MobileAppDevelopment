package kristofcolpaert.com.week3demo1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by kristofcolpaert on 26/02/15.
 */
public class DemoFragment extends Fragment
{
    private Button btnFinish;

    //Hier wordt voor het eerst een fragment aan een activity toegevoegd.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("DemoFragment","DemoFragment > onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DemoFragment", "DemoFragment > onCreate");
    }

    //Deze methode is verantwoordelijk voor het inladen van de layout.
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo, container, false);
        Log.d("DemoFragment","DemoFragment > onCreateView");

        btnFinish = (Button) v.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("DemoFragment", "DemoFragment > onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("DemoFragment", "DemoFragment > onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DemoFragment", "DemoFragment > onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("DemoFragment", "DemoFragment > onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("DemoFragment", "DemoFragment > onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("DemoFragment", "DemoFragment > onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DemoFragment", "DemoFragment > onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("DemoFragment", "DemoFragment > onDetach");
    }
}
