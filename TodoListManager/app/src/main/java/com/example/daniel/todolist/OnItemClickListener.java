package com.example.daniel.todolist;

import android.view.View;

/**
 * Created by Daniel on 16/03/2017.
 */

public interface OnItemClickListener {
    void onItemClick(int i,View v);
    void onItemLongClick(int i);
}