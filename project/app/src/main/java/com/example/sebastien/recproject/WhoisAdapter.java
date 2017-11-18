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
 * Created by Sebastien on 18/11/2017.
 */

public class WhoisAdapter extends ArrayAdapter<WhoisResultItem>{

    public WhoisAdapter(@NonNull Context context, @NonNull List<WhoisResultItem> objects, DetailsFragment.OnFragmentInteractionListener mListener) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.whois_result_item, parent, false);
        // Get a contact
        WhoisResultItem whoisResultItem = getItem(position);

        // Display the information about the contact
        TextView textType = rootView.findViewById(R.id.textType);
        textType.setText(whoisResultItem.getType());

        TextView textName = rootView.findViewById(R.id.textName);
        textName.setText(whoisResultItem.getName());

        TextView textOrganization = rootView.findViewById(R.id.textOrganization);
        textOrganization.setText(whoisResultItem.getOrganization());

        TextView textPhone = rootView.findViewById(R.id.textPhone);
        textPhone.setText(whoisResultItem.getPhone());

        TextView textEmail = rootView.findViewById(R.id.textEmail);
        textEmail.setText(whoisResultItem.getEmail());

        TextView textFull_address = rootView.findViewById(R.id.textFull_address);
        textFull_address.setText(whoisResultItem.getFull_address());

        return rootView;
    }
}
