package com.example.sebastien.recproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Valentin on 17/11/2017.
 *
 * Description : Implementation of a recycler view for the listFragment to
 * list the previous results stored in the data base and the results of the
 * current research on google.
 */

public class Adapter extends ArrayAdapter<GoogleResultItem> {


    public Adapter(@NonNull Context context, @NonNull List<GoogleResultItem> objects, ListFragment.OnFragmentInteractionListener mListener) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rootView;

        if( convertView == null)
        {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.google_result_item, parent, false);
        }
        else
        {
            rootView = convertView;
        }
        //Description of the link
        final TextView txvTitle = rootView.findViewById(R.id.title);
        //Domain name
        TextView txvDisplayLink = rootView.findViewById(R.id.displayLink);
        //String research in the google custom search
        TextView txvResearch = rootView.findViewById(R.id.research);

        GoogleResultItem googleResultItem = getItem(position);

        //Display
        txvTitle.setText(googleResultItem.getTitle());
        txvDisplayLink.setText(googleResultItem.getLink());
        txvResearch.setText(googleResultItem.getResearch());

        return rootView;
    }





}
