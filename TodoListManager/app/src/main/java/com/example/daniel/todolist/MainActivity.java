package com.example.daniel.todolist;

import android.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static final String TAG_RETAINED_FRAGMENT = "TasksRetainFragment";
    private TasksRetainFragment mRetainedFragment;
    private RecyclerView mRecyclerView;
    private TodoTaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private FragmentManager fm;
    FloatingActionButton mFab;
    private PopupMenu popup;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        fm = getFragmentManager();
        mRetainedFragment = (TasksRetainFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(mAdapter == null) {

            mAdapter = new TodoTaskAdapter(new OnItemClickListener() {

                @Override
                public void onItemLongClick(final TodoTask item) {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Task")
                            .setMessage("Are you sure you want to delete this task?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mAdapter.removeItemToList(item);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

                @Override
                public void onItemClick(final TodoTask item, View v) {
                    popupOnList(item,v);
                }

            });

        }
        else{
            mAdapter = mRetainedFragment.getData();
        }
        if (mRetainedFragment == null || mAdapter != mRetainedFragment.getData()) {
            mRetainedFragment = new TasksRetainFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            mRetainedFragment.setData(mAdapter);
        }

        mRecyclerView.setAdapter(mAdapter);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TaskDialogFragment createTaskDialog = new TaskDialogFragment();
                createTaskDialog.setData(mAdapter);
                createTaskDialog.show(getFragmentManager(), "addDialog1");

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.menu_add_btn) {
            TaskDialogFragment createTaskDialog = new TaskDialogFragment();
            createTaskDialog.setData(mAdapter);
            createTaskDialog.show(getFragmentManager(), "addDialog2");
            return true;
        } else if (itemThatWasClickedId == R.id.menu_clear_btn) {
            mAdapter.clearList();
        }
        return super.onOptionsItemSelected(item);
    }
    public void popupOnList(final TodoTask item, View v){
        //RecyclerView v = (RecyclerView) findViewById(R.id.recycler_view);
        popup = new PopupMenu(MainActivity.this,v);
        Menu menu = popup.getMenu();
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        menu.findItem(R.id.popup_title).setTitle(item.taskText);
        if (item.taskText.toLowerCase().contains("call")){
            System.out.println(item.taskText.replace("call ", ""));
            menu.findItem(R.id.popup_call).setVisible(true);
            menu.findItem(R.id.popup_call).setTitle("Dial this number:" + item.taskText.replace("call", ""));
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Delete task")){
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Task")
                            .setMessage("Are you sure you want to delete this task?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mAdapter.removeItemToList(item);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else if (menuItem.getTitle().equals("Dial this number:" + item.taskText.replace("call", ""))){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+item.taskText.replace("call ", "")));
                    startActivity(intent);
                }
                return true;
            }
        });
        v.post(new Runnable() {
            public void run() {
                popup.show();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(popup != null){
            popup.dismiss();
        }
    }



}


