package com.example.daniel.todolist;

/**
 * Created by Daniel on 16/03/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        public TextView creationDate;
        public TextView reminderDate;
        public CheckBox taskCheckbox;

        public MyViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.task_text);
            creationDate = (TextView) view.findViewById(R.id.creation_date_text);
            reminderDate = (TextView) view.findViewById(R.id.remainder_date_text);
            taskCheckbox = (CheckBox) view.findViewById(R.id.task_checkbox);

        }
        public void bind(final int i, final OnItemClickListener listener) {
            task.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    listener.onItemLongClick(i);
                    return false;
                }
            });

            task.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(i,v);
                }
            });
        }
    }

    public TodoTaskAdapter(ArrayList<TodoTask> taskList, OnItemClickListener listener) {
        this.listener = listener;
        this.taskList = taskList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TodoTask m = taskList.get(position);
        if(position%2 == 0){
            holder.task.setBackgroundResource(R.drawable.task_background_box_blue);
        }
        else{
            holder.task.setBackgroundResource(R.drawable.task_background_box_pink);

        }
        holder.task.setText(m.taskText);
        holder.creationDate.setText(m.taskCreationDate);
        holder.reminderDate.setText(m.taskRemainderDate);
        if(m.getCheck()){
            holder.task.setAlpha(0.4f);
            holder.creationDate.setAlpha(0.4f);
            holder.reminderDate.setAlpha(0.4f);
            holder.taskCheckbox.setChecked(true);
        }
        else{
            holder.taskCheckbox.setChecked(false);
        }

        holder.taskCheckbox.setOnCheckedChangeListener(null);

        holder.taskCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.task.setAlpha(0.4f);
                    holder.creationDate.setAlpha(0.4f);
                    holder.reminderDate.setAlpha(0.4f);
                    m.setCheck(true);
                }
                else{
                    holder.task.setAlpha(1.0f);
                    holder.creationDate.setAlpha(1.0f);
                    holder.reminderDate.setAlpha(1.0f);
                    m.setCheck(false);
                }
            }
        });
        holder.bind(position, listener);
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
        item.setCheck(false);
        taskList.remove(item);
        notifyDataSetChanged();
    }

    public void clearList(){
        taskList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<TodoTask> getTaskList(){
        return taskList;
    }

}

