package com.example.daniel.todolist;


import android.app.Fragment;
import android.os.Bundle;


/**
 * Created by Daniel on 16/03/2017.
 */

public class TasksRetainFragment extends Fragment {

    private TodoTaskAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(TodoTaskAdapter adapter) {
        this.adapter = adapter;
    }

    public TodoTaskAdapter getData() {
        return adapter;
    }

}