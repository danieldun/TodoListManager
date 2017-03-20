package com.example.daniel.todolist;

/**
 * Created by Daniel on 16/03/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/03/2017.
 */


public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private final ArrayList<TodoTask> taskList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task;
        public TextView date;

        public MyViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.task_text);
            date = (TextView) view.findViewById(R.id.date_text);

        }
        public void bind(final TodoTask item, final OnItemClickListener listener) {
            task.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    listener.onItemClick(item);
                    return false;
                }
            });
        }
    }

    public TodoTaskAdapter(ArrayList<TodoTask>taskList, OnItemClickListener listener) {
        this.taskList = new ArrayList<>();
        this.listener = listener;
        for(TodoTask t:taskList){
            addItemToList(t);
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TodoTask m = taskList.get(position);
        if(position%2 == 0){
            holder.task.setBackgroundResource(R.drawable.task_background_box_green);
        }
        else{
            holder.task.setBackgroundResource(R.drawable.task_background_box_pink);

        }
        holder.task.setText(m.taskText);
        holder.date.setText(m.taskDate);
        holder.bind(taskList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent, false);
        return new MyViewHolder(v);
    }

    public void addItemToList(TodoTask item){
        taskList.add(item);
        notifyDataSetChanged();
    }
    public void removeItemToList(TodoTask item){
        taskList.remove(item);
        notifyDataSetChanged();
    }
    public void clearList(){
        taskList.clear();
        notifyDataSetChanged();
    }
    public ArrayList<TodoTask> getTasksList(){
        return taskList;
    }

}

