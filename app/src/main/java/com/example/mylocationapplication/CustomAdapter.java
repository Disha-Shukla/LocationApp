package com.example.mylocationapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        Button btnEdit = listitemView.findViewById(R.id.btnEdit);
        Button btnDelete = listitemView.findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, MainActivity.class);
                // Log.v("!!!!1", "" + contactModelArrayList.get(position).get_id());
                i.putExtra("flag", "edit");
                i.putExtra("id", contactModelArrayList.get(position).get_id());
                i.putExtra("name", "" + contactModelArrayList.get(position).getName());
                i.putExtra("lat", "" + contactModelArrayList.get(position).getLat());
                i.putExtra("lng", "" + contactModelArrayList.get(position).getLng());
                c.startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(position);
            }
        });

        return listitemView;
    }
    private void createDialog(final int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                db.deleteContact(new Location
                                        (contactModelArrayList.get(position).get_id(),
                                                contactModelArrayList.get(position).getName(),
                                                contactModelArrayList.get(position).getLat(),
                                                contactModelArrayList.get(position).getLng()));
                                ((Activity) getContext()).finish();
                                Intent i = new Intent(c, LocationListActivity.class);
                                getContext().startActivity(i);

                            }
                        })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

   /* private void updateDialog() {
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.updatelist);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        EditText updatedCity = (EditText) dialog.findViewById(R.id.text);
        updatedCity.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher_foreground);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnUpdate);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateContact(new Location
                        (bundle.getInt("id", 0),
                                edtName.getText().toString(),
                                edtNumber.getText().toString(),
                                edtEmail.getText().toString(),
                                edtAddress.getText().toString()
                        ));
                dialog.dismiss();
            }
        });

        dialog.show();

    }*/

}
