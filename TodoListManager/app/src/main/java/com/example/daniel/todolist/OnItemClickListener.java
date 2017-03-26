package com.example.daniel.todolist;

import android.view.View;

/**
 * Created by Daniel on 16/03/2017.
 */

public interface OnItemClickListener {
    void onItemClick(TodoTask item,View v);
    void onItemLongClick(TodoTask item);
}