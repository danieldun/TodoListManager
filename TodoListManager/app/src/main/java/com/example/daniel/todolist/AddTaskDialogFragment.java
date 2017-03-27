package com.example.daniel.todolist;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 23/03/2017.
 */

public class AddTaskDialogFragment extends DialogFragment{

    private View v;

    public interface YesNoAddListener {
        void onAddYes(View v, String creationDate, String remainderDate);
        void onAddNo();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof YesNoAddListener)) {
            throw new ClassCastException(context.toString() + " must implement YesNoListener");
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_add, null);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        final String formattedDate = "Created on: " + df.format(c.getTime());


        DatePicker datePicker = (DatePicker)v.findViewById(R.id.date_picker);
        datePicker.setMinDate(0);
        datePicker.setMinDate(System.currentTimeMillis()-1000);

        int   day  = datePicker.getDayOfMonth();
        int   month = datePicker.getMonth();
        int   year = datePicker.getYear() -1900;
        final String remainderDate = "Remind on: " + df.format(new Date(year, month, day));


        builder.setView(v)
                .setTitle("Add Task")
                .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((YesNoAddListener) getActivity()).onAddYes(v, formattedDate, remainderDate);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((YesNoAddListener) getActivity()).onAddNo();

                    }
                })
                .setIcon(R.drawable.ic_content_paste_black_24dp);

        return builder.create();

    }

}
