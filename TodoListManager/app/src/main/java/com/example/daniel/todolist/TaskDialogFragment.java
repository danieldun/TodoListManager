package com.example.daniel.todolist;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 23/03/2017.
 */

public class TaskDialogFragment extends DialogFragment{

    private View v;
    private TodoTaskAdapter adapter;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_add, null);

        builder.setView(v)
                .setTitle("Add Task")
                .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                        String formattedDate = "Created on: " + df.format(c.getTime());

                        DatePicker datePicker = (DatePicker)v.findViewById(R.id.date_picker);
                        int   day  = datePicker.getDayOfMonth();
                        int   month = datePicker.getMonth();
                        int   year = datePicker.getYear() -1900;
                        String remainderDate = "Remind on: " + df.format(new Date(year, month, day));

                        EditText et = (EditText)v.findViewById(R.id.add_task);
                        if(!et.getText().toString().equals("")) {
                            if (adapter == null){
                                adapter = new TodoTaskAdapter(null);
                                adapter.addItemToList(new TodoTask(et.getText().toString(), formattedDate, remainderDate));
                            }
                            else {
                                adapter.addItemToList(new TodoTask(et.getText().toString(), formattedDate, remainderDate));
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "You can not submit empty task.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(R.drawable.ic_content_paste_black_24dp);

        return builder.create();

    }

    public void setData(TodoTaskAdapter adapter){
        this.adapter = adapter;
    }


}
