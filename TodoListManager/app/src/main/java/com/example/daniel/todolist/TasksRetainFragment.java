package com.example.daniel.todolist;


import android.app.Fragment;
import android.os.Bundle;
import java.util.ArrayList;


/**
 * Created by Daniel on 16/03/2017.
 */

public class TasksRetainFragment extends Fragment {

    private ArrayList<TodoTask> taskList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(ArrayList<TodoTask> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<TodoTask> getData() {
        return taskList;
    }



}