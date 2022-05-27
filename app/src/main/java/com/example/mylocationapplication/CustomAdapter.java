package com.example.mylocationapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomAdapter extends ArrayAdapter<Location> {

    Context c;
    DatabaseHandler db;
    ArrayList<Location> contactModelArrayList;

    public CustomAdapter(@NonNull Context context, ArrayList<Location> contactModelArrayList) {
        super(context, 0, contactModelArrayList);
        this.c = context;
        this.contactModelArrayList = contactModelArrayList;
        db = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item, parent, false);
        }
        Location contactModel = getItem(position);
        TextView empName = listitemView.findViewById(R.id.txtName);
        empName.setText(contactModel.getName());
        /*TextView empNumber = listitemView.findViewById(R.id.txtNumber);
        TextView empEmail = listitemView.findViewById(R.id.txtEmail);
        TextView empAddress = listitemView.findViewById(R.id.txtAddress);
        CircleImageView propic = listitemView.findViewById(R.id.profile_image);
        Button btnEdit = listitemView.findViewById(R.id.btnEdit);
        Button btnDelete = listitemView.findViewById(R.id.btnDelete);

        empNumber.setText(contactModel.get_phone_number());
        empEmail.setText(contactModel.getEmail());
        empAddress.setText(contactModel.getAddress());
        Log.v("DS","111"+ Arrays.toString(contactModel.getImage()));
        if(contactModel.getImage()!=null){
        propic.setImageBitmap(Utils.getImage(contactModel.getImage()));
        }*/

        return listitemView;
    }
}
