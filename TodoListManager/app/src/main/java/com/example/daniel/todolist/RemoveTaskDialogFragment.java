package com.example.daniel.todolist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


/**
 * Created by Daniel on 26/03/2017.
 */

public class RemoveTaskDialogFragment extends DialogFragment {
    private static int i;

    public interface YesNoRemoveListener {
        void onRemoveYes(int i );
        void onRemoveNo();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof YesNoRemoveListener)) {
            throw new ClassCastException(context.toString() + " must implement YesNoListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((YesNoRemoveListener) getActivity()).onRemoveYes(i);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((YesNoRemoveListener) getActivity()).onRemoveNo();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();
    }

    public void setData(int index){
        i = index;
    }
}
