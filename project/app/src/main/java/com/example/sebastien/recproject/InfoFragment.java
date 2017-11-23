package com.example.sebastien.recproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{
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
    private TextView textInfo;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        //Initialize buttons to switch between fragments in the MainCActivity
        buttonList = (Button)view.findViewById(R.id.buttonList);
        buttonList.setOnClickListener(this);
        buttonInfo = (Button) view.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(this);
        buttonMap = (Button) view.findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);

        // Set the source text
        textInfo = (TextView) view.findViewById(R.id.textInfo);
        textInfo.setText("Infos générales sur l'application :\n" +
                "L'application permet de rechercher le nom d'une entreprise sur Google et retourne les noms de domaine des 10 premiers résulats.\n" +
                "En cliquant sur l'un des résultats, une recherche est effectuée via l'API WhoAPI qui retourne les informations publiques sur le domaine ciblé.\n" +
                "En cliquant sur le bouton 'Map' dans la fenêtre Détails, on peut localiser l'entreprise disposant du nom de domaine ciblé.\n" +
                "Si on clique sur le bouton 'Map' directement à partir de la liste, on localise l'ensemble des domaines de la liste.\n" +
                "\n" +
                "Le bouton 'actualiser' raffraichit l'affichage des données à partir de la base de données locale.\n" +
                "Dans le menu 'option' il est possible de supprimer l'historique de la base de données locale.\n\n" +
                "API Google Custom Search :\n" +
                "   - https://www.googleapis.com/customsearch/v1?key=API_KEY&cx=searchEngine&q=stringsearched\n" +
                "API Whois :\n" +
                "   - http://api.whoapi.com/?apikey=API_KEY&r=whois&domain=domain&ip=\n" +
                "API Google Map :\n" +
                "   - https://maps.googleapis.com/maps/api/geocode/json?address=position&key=API_KEY" +
                "\n" +
                "Les termes d'utilisation de ces API sont disponibles sur :\n" +
                "   - https://whoapi.com/page/terms_of_use\n" +
                "   - https://developers.google.com/terms");

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.callListFragment();
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
                //Replace the fragment with ListFragment
                mListener.callListFragment();
                break;
            case R.id.buttonInfo :
                break;
            case R.id.buttonMap :
                //Call MapsActivity
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
        void callListFragment();
    }
}
