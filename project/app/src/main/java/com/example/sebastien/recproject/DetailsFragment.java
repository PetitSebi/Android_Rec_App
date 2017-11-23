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
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button buttonMap;
    private RequestQueue queue;

    private GoogleResultItem googleItem;
    private TextView textTitle;
    private TextView textDomain;
    private WhoisResult whoisResult;
    private ListView listView;
    private WhoisAdapter whoisAdapter;
    private ArrayList<WhoisResultItem> whoisResultArrayList;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        //Initialize "map" button to locate the targeted server on the map
        buttonMap = (Button) view.findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);

        // Link TextView variables to the design
        textTitle = (TextView) view.findViewById(R.id.textTitle);
        textDomain = (TextView) view.findViewById(R.id.textDomain);
        listView = (ListView) view.findViewById(R.id.listView);

        // Create an HTTP Request queue
        queue = Volley.newRequestQueue((MainActivity)getActivity());
        // Send the Whois Request
        whoisRequest(googleItem.getLink());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            ArrayList<String> list = new ArrayList<>();
            mListener.callMapsActivity(list);
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
        // Convert WhoisAdapter to a list of addresse
        ArrayList<String> listAddresses = whoisResultToListAddresses(whoisResult);
        // Call MapsActivity
        mListener.callMapsActivity(listAddresses);
    }


    public void whoisRequest(String domain){
        // We are using the Whois API to gather information about the targeted domain
        String url = "http://api.whoapi.com/?apikey=a0a4b7e8e99083730033e34df1db4efc&r=whois&domain="+domain+"&ip=";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Setup the topbar : title + domain displayed
                        textTitle.setText(googleItem.getTitle());
                        textDomain.setText(googleItem.getLink());
                        // Get the JSON file from WhoisAPI and put it into an Object "whoisResult"
                        Gson gson = new GsonBuilder().create();
                        whoisResult = gson.fromJson( response, WhoisResult.class);

                        // Use of an ArrayAdapter to fill out the listView
                        whoisAdapter = new WhoisAdapter(getContext(), whoisResult.getContacts(), mListener);
                        listView.setAdapter(whoisAdapter);
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

    public ArrayList<String> whoisResultToListAddresses(WhoisResult whoisResult){
        // Get the list of contacts (a contact contains a type, a name, a phone, an address, an email, and an organization)
        ArrayList<WhoisResultItem> contacts = whoisResult.getContacts();
        // Get the addresses for each contact in the list
        ArrayList<String> listAddresses = new ArrayList<>();
        for(WhoisResultItem item: contacts){
            if(!item.getFull_address().equals("")){
                // We need to remove the spaces before sending the request
                listAddresses.add(item.getFull_address().replace(" ","+").replace(",",""));
                // Remove the addresses that appear multiple times
                Set set = new HashSet() ;
                set.addAll(listAddresses) ;
                listAddresses = null;
                listAddresses = new ArrayList(set) ;
            }
        }
        return listAddresses;
    }

    // Method to transfer a googleItem between ListFragment and DetailsFragment
    public void saveGoogleItem(GoogleResultItem googleItem){
        this.googleItem = googleItem;
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
        void callMapsActivity(ArrayList<String> listAddresses);
    }
}
