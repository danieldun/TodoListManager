package com.example.daniel.todolist;

/**
 * Created by Daniel on 16/03/2017.
 */

public class TodoTask {
    protected String taskText;
    protected String taskCreationDate;
    protected String taskRemainderDate;
    protected boolean taskCheck;


    public TodoTask(String taskText, String taskCreationDate, String taskRemainderDate) {
        this.taskText = taskText;
        this.taskCreationDate = taskCreationDate;
        this.taskRemainderDate = taskRemainderDate;
        this.taskCheck = false;
    }
    public void setCheck(boolean value){
        taskCheck = value;
    }
    public boolean getCheck(){
        return taskCheck;
    }
}
