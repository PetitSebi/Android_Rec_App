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
import android.widget.AdapterView;
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
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    //
    private OnFragmentInteractionListener mListener;

    //Buttons to switch from a fragment to another in the MainActivity
    private Button buttonList;
    private Button buttonInfo;
    private Button buttonMap;
    //Search field
    private TextView searchBar;

    // We count the number of domains to be sure that we call the Map when all the domains have been processed
    private int countDomains = 0;
    private ArrayList<String> listDomains;


    //ListView with a Recycler
    private ListView listGoogleResults;
    private Adapter googleResultsAdapter;
    //List from the MainActivity set as parameter in the newInstance() function
    private ArrayList<GoogleResultItem> googleResultArrayList = new ArrayList<GoogleResultItem>();
    //Used to parse the Json after the GET request
    private GoogleResult googleResult = new GoogleResult();

    public static final String BUNDLE_PARAM_GOOGLERESULTITEM = "BUNDLE_PARAM_GOOGLERESULTITEM";

    private ArrayList<String> listAddresses;
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


        listGoogleResults = (ListView) view.findViewById(R.id.listView);
        googleResultsAdapter = new Adapter(getContext(), googleResultArrayList, mListener);
        listGoogleResults.setAdapter(googleResultsAdapter);

        // Add a Listener on each Item to call the DetailsFragment
        listGoogleResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Get the item that has been clicked
                GoogleResultItem googleItem = (GoogleResultItem) parent.getItemAtPosition(position);
                // Send the item to the DetailsFragment
                mListener.callDetailsFragment(googleItem);
            }
        });




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
                listDomains = mListener.getListDomains();
                getAddressesFromDomains(listDomains);
                break;
        }
    }


    public void getAddressesFromDomains(ArrayList<String> listDomains){
        listAddresses = new ArrayList<>();
        for(String domain : listDomains){
            whoisRequest(domain);
        }
    }

    public void searchGoogleGETRequest(final String stringSearched){
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
                            item.setResearch(stringSearched);
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

    public void updateList(ArrayList<GoogleResultItem> list)
    {
        googleResultArrayList = list;
        if( googleResultsAdapter != null)
            googleResultsAdapter.notifyDataSetChanged();

    }

    public void whoisRequest(String domain){
        // We are using the Whois API to gather information about the targeted domain
        String url = "http://api.whoapi.com/?apikey=a0a4b7e8e99083730033e34df1db4efc&r=whois&domain="+domain+"&ip=";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Get the JSON file from WhoisAPI and put it into an Object "whoisResult"
                        Gson gson = new GsonBuilder().create();
                        WhoisResult whoisResult = gson.fromJson( response, WhoisResult.class);
                        ArrayList<String> list = whoisResultToListAddresses(whoisResult);
                        // Concatenate the new addresses with the list of addresses
                        listAddresses.addAll(list);
                        // Loop until all the domains are processed
                        // When it's done, call the MapActivity with the full list of addresses as a parameter
                        countDomains += 1;
                        if(countDomains==listDomains.size()){
                            // The last domain has been processed, we can call the MapActivity
                            mListener.callMapsActivity(listAddresses);
                        }
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
        ArrayList<String> getListDomains();
        void callMapsActivity(ArrayList<String> listAddresses);
        void addToBDDGoogleResultItem(GoogleResultItem item);
        void displayGoogleResult(ArrayList<GoogleResultItem> list);
        void callDetailsFragment(GoogleResultItem googleItem);
    }
}
