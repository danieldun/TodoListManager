package com.example.daniel.todolist;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RemoveTaskDialogFragment.YesNoRemoveListener, AddTaskDialogFragment.YesNoAddListener {

    private static final String TAG_RETAINED_FRAGMENT = "TasksRetainFragment";
    private TasksRetainFragment mRetainedFragment;
    private RecyclerView mRecyclerView;
    private TodoTaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager fm;
    FloatingActionButton mFab;
    private PopupMenu popup;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        mRetainedFragment = (TasksRetainFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<TodoTask> taskList;

        if(mRetainedFragment == null){
            taskList = new ArrayList<>();
        }
        else {
            taskList = mRetainedFragment.getData();
        }

        mAdapter = new TodoTaskAdapter(taskList,new OnItemClickListener() {

            @Override
            public void onItemLongClick(final int i) {
                RemoveTaskDialogFragment deleteTaskDialog = new RemoveTaskDialogFragment();
                deleteTaskDialog.setData(i);
                deleteTaskDialog.show(getFragmentManager(), "tag");
            }

            @Override
            public void onItemClick(final int i, View v) {
                popupOnList(i,v);
            }
        });

        if (mRetainedFragment == null || mAdapter.getTaskList() != mRetainedFragment.getData()) {
            mRetainedFragment = new TasksRetainFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            mRetainedFragment.setData(mAdapter.getTaskList());
        }

        mRecyclerView.setAdapter(mAdapter);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddTaskDialogFragment createTaskDialog = new AddTaskDialogFragment();
                createTaskDialog.show(getFragmentManager(), "addDialog2");
            }
        });
    }

    @Override
    public void onRemoveYes(int i){
        mAdapter.removeItemToList(mAdapter.getTaskList().get(i));
    }
    @Override
    public void onRemoveNo(){}

    @Override
    public void onAddYes(View v, String creationDate, String remainderDate){

        EditText et = (EditText)v.findViewById(R.id.add_task);
        if(!et.getText().toString().equals("")) {
            mAdapter.addItemToList(new TodoTask(et.getText().toString(), creationDate, remainderDate));
        }
        else {

            Toast.makeText(this, "You can not submit empty task.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onAddNo(){}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.menu_add_btn) {
            AddTaskDialogFragment createTaskDialog = new AddTaskDialogFragment();
            createTaskDialog.show(getFragmentManager(), "addDialog3");
            return true;
        } else if (itemThatWasClickedId == R.id.menu_clear_btn) {
            mAdapter.clearList();
        }
        return super.onOptionsItemSelected(item);
    }

    public void popupOnList(final int i, View v){
        //RecyclerView v = (RecyclerView) findViewById(R.id.recycler_view);
        popup = new PopupMenu(MainActivity.this,v);
        Menu menu = popup.getMenu();
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        menu.findItem(R.id.popup_title).setTitle(mAdapter.getTaskList().get(i).taskText);
        if (mAdapter.getTaskList().get(i).taskText.toLowerCase().contains("call")){
            menu.findItem(R.id.popup_call).setVisible(true);
            menu.findItem(R.id.popup_call).setTitle("Dial this number:" + mAdapter.getTaskList().get(i).taskText.replace("call", ""));
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Delete task")){
                    RemoveTaskDialogFragment deleteTaskDialog = new RemoveTaskDialogFragment();
                    deleteTaskDialog.setData(i);
                    deleteTaskDialog.show(getFragmentManager(), "tag");
                }
                else if (menuItem.getTitle().equals("Dial this number:" + mAdapter.getTaskList().get(i).taskText.replace("call", ""))){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+mAdapter.getTaskList().get(i).taskText.replace("call ", "")));
                    startActivity(intent);
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isFinishing()){
            if (popup!= null) {
                popup.dismiss();
                popup= null;
            }
        }
    }

}


