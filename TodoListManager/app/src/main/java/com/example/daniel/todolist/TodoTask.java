package com.example.daniel.todolist;

/**
 * Created by Daniel on 16/03/2017.
 */

public class TodoTask {
    protected String taskText;
    protected String taskDate;
    protected String taskBackground;

    public TodoTask(String taskText, String taskDate) {
        this.taskText = taskText;
        this.taskDate = taskDate;
    }
}
