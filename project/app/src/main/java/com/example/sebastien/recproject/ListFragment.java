package com.example.sebastien.recproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //Buttons to switch from a fragment to another in the MainActivity
    private Button buttonList;
    private Button buttonInfo;
    private Button buttonMap;


    //ListView with a Recycler
    private ListView listGoogleResults;
    private Adapter googleResultsAdapter;
    private ArrayList<GoogleResultItem> googleResultArrayList;

    public static final String BUNDLE_PARAM_GOOGLERESULTITEM = "BUNDLE_PARAM_GOOGLERESULTITEM";


    public ListFragment() {
        // Required empty public constructors
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(ArrayList<GoogleResultItem> googleResultArrayList) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PARAM_GOOGLERESULTITEM, googleResultArrayList );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            googleResultArrayList = (ArrayList<GoogleResultItem>)getArguments().getSerializable((BUNDLE_PARAM_GOOGLERESULTITEM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //Initialize buttons to switch between fragments in the MainCActivity
        buttonList = (Button)view.findViewById(R.id.buttonList);
        buttonList.setOnClickListener(this);
        buttonInfo = (Button) view.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(this);
        buttonMap = (Button) view.findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);

        //Initialize listView
        if( googleResultArrayList != null)
        {
            listGoogleResults = (ListView) view.findViewById(R.id.listView);
            googleResultsAdapter = new Adapter(getContext(), googleResultArrayList , mListener);
            listGoogleResults.setAdapter(googleResultsAdapter);
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.callInfoFragment();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonList :
                break;
            case R.id.buttonInfo :
                //Replace the fragment with InfoFragment
                mListener.callInfoFragment();
                break;
            case R.id.buttonMap :
                //Call MapsActivity
                mListener.callMapsActivity();
                break;
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void callInfoFragment();
        void callMapsActivity();
    }
}
