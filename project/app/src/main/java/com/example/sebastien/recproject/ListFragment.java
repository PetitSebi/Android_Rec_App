package com.example.sebastien.recproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private TextView searchBar;


    //ListView with a Recycler
    private ListView listGoogleResults;
    private Adapter googleResultsAdapter;
    private ArrayList<GoogleResultItem> googleResultArrayList;
    private GoogleResult googleResult = new GoogleResult();

    public static final String BUNDLE_PARAM_GOOGLERESULTITEM = "BUNDLE_PARAM_GOOGLERESULTITEM";


    private RequestQueue queue;

    public ListFragment() {
        // Required empty public constructors
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
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

        // Create an HTTP Request queue
        queue = Volley.newRequestQueue((MainActivity)getActivity());

        //Initialize buttons to switch between fragments in the MainCActivity
        buttonList = (Button)view.findViewById(R.id.buttonList);
        buttonList.setOnClickListener(this);
        buttonInfo = (Button) view.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(this);
        buttonMap = (Button) view.findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);

        // Edit Text to search information on Google
        searchBar = (TextView) view.findViewById(R.id.searchBar);
        // Set an editorActionListener replacing a "search" button
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Search what has been typed on Google
                    searchGoogleGETRequest(String.valueOf(searchBar.getText()));
                    return true;
                }
                return false;
            }
        });
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


    public void searchGoogleGETRequest(String stringSearched){
        // We are using the Google Custom Search API to get the first 10 results of Google formatted in JSON
        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCvojZ4WyDacrj4-papbJrCFCcrIXf_Trk&cx=013421088620583939471:wcsjj9jp5ki&q="+stringSearched;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        googleResult = gson.fromJson( response, GoogleResult.class);
                        ArrayList<GoogleResultItem> list = new ArrayList<>();
                        list = googleResult.getItems();
                        for( GoogleResultItem item : list)
                        {
                            mListener.addToBDDGoogleResultItem(item);
                        }
                        mListener.displayGoogleResult(list);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Todo
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
        void addToBDDGoogleResultItem(GoogleResultItem item);
        void displayGoogleResult(ArrayList<GoogleResultItem> list);
    }
}
