package com.kristofcolpaert.week5demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.kristofcolpaert.week5demo.EditPersonFragment.SavePersonListener} interface
 * to handle interaction events.
 * Use the {@link EditPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPersonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAME = "com.kristofcolpaert.week5demo.ARG_NAME";
    private static final String ARG_EMAIL = "com.kristofcolpaert.week5demo.ARG_EMAIL";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mEmail;

    private SavePersonListener mListener;
    private EditText mEtName;
    private EditText mEtEmail;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param email Parameter 2.
     * @return A new instance of fragment EditPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPersonFragment newInstance(String name, String email) {
        EditPersonFragment fragment = new EditPersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    public EditPersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mEmail = getArguments().getString(ARG_EMAIL);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_person, container, false);
        mEtName = (EditText) v.findViewById(R.id.etName);
        mEtEmail = (EditText) v.findViewById(R.id.etEmail);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (SavePersonListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
            String name = mEtName.getText().toString();
            String email = mEtEmail.getText().toString();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SavePersonListener {
        public void onSavePerson();
    }

}
