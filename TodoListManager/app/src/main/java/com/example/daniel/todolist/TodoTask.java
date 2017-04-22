package com.example.daniel.todolist;


import java.sql.Timestamp;

/**
 * Created by Daniel on 16/03/2017.
 */

public class TodoTask {
    protected String taskText;
    protected String taskCreationDate;
    protected String taskRemainderDate;
    protected boolean taskCheck;
    protected long id;


    public TodoTask(String taskText, String taskCreationDate, String taskRemainderDate) {
        this.taskText = taskText;
        this.taskCreationDate = taskCreationDate;
        this.taskRemainderDate = taskRemainderDate;
        this.taskCheck = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.id = timestamp.getTime();
    }
    public void setCheck(boolean value){
        taskCheck = value;
    }
    public boolean getCheck(){
        return taskCheck;
    }
    public void setId(long id){
        this.id = id;
    }

}
