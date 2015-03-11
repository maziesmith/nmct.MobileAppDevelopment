package com.kristofcolpaert.week5demo;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowPersonFragment extends Fragment {

    public interface EditPersonListener
    {
        void onEditPerson();
    }

    public static final String ARG_NAME = "com.kristofcolpaert.week5demo.ARG_NAME";
    public static final String ARG_EMAIL = "com.kristofcolpaert.week5demo.ARG_EMAIL";
    private String mName;
    private String mEmail;
    private TextView mTvName;
    private TextView mTvEmail;
    private EditPersonListener mActivity;

    public ShowPersonFragment()
    {
        //Default empty constructor.
    }

    public static ShowPersonFragment newInstance(String name, String email)
    {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_NAME, name);
        arguments.putString(ARG_EMAIL, email);

        ShowPersonFragment spf = new ShowPersonFragment();
        spf.setArguments(arguments);

        return spf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Niet vergeten om deze altijd mee te geven.
        super.onCreate(savedInstanceState);
        //Argumenten opvragen en indien er zijn halen we deze op.
        //Propere manier om zaken door te geven aan een fragment.
        Bundle argument = getArguments();

        if(argument != null)
        {
            mName = argument.getString(ARG_NAME);
            mEmail = argument.getString(ARG_EMAIL);
        }

        //Options menu aanmaken.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_show_person, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mni_edit_person)
        {
            mActivity.onEditPerson();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = (EditPersonListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_person, container, false);

        mTvName = (TextView) v.findViewById(R.id.tvName);
        mTvName.setText(mName);

        mTvEmail = (TextView) v.findViewById(R.id.tvEmail);
        mTvEmail.setText(mEmail);

        return v;
    }

    public void updateUser(String name, String email)
    {
        mEmail = email;
        mName = name;

        mTvName.setText(mName);
        mTvEmail.setText(mEmail);
    }
}
