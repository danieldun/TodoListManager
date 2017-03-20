package com.example.daniel.todolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private static final String TAG_RETAINED_FRAGMENT = "TasksRetainFragment";
    private TasksRetainFragment mRetainedFragment;
    private ArrayList<TodoTask> taskListArg;
    private RecyclerView mRecyclerView;
    private TodoTaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private FragmentManager fm;
    FloatingActionButton mFab;

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

        if (mRetainedFragment == null){
            taskListArg = new ArrayList<>();
        }
        else{
            taskListArg = mRetainedFragment.getData();
        }
        mAdapter = new TodoTaskAdapter(taskListArg, new OnItemClickListener() {
            @Override
            public void onItemClick(final TodoTask item) {
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
        });

        if (mRetainedFragment == null || mAdapter.getTasksList() != mRetainedFragment.getData()) {
            mRetainedFragment = new TasksRetainFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            mRetainedFragment.setData(mAdapter.getTasksList());
        }

        mRecyclerView.setAdapter(mAdapter);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View inf = inflater.inflate(R.layout.dialog_add, null);
                new AlertDialog.Builder(context)
                        .setView(inf)
                        .setTitle("Add Task")
                        .setMessage("Enter the content of todo task.")
                        .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                                String formattedDate = df.format(c.getTime());
                                EditText et = (EditText)inf.findViewById(R.id.add_task);
                                if(!et.getText().toString().equals("")) {
                                    mAdapter.addItemToList(new TodoTask(et.getText().toString(), formattedDate));
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "You can not submit empty task.", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.ic_content_paste_black_24dp)
                        .show();
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
            LayoutInflater inflater = getLayoutInflater();
            final View inf = inflater.inflate(R.layout.dialog_add, null);
            new AlertDialog.Builder(context)
                    .setView(inf)
                    .setTitle("Add Task")
                    .setMessage("Enter the content of todo task.")
                    .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                            String formattedDate = df.format(c.getTime());
                            EditText et = (EditText)inf.findViewById(R.id.add_task);
                            if(!et.getText().toString().equals("")) {
                                mAdapter.addItemToList(new TodoTask(et.getText().toString(), formattedDate));
                            }
                            else{
                                Toast.makeText(MainActivity.this, "You can not submit empty task.", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(R.drawable.ic_content_paste_black_24dp)
                    .show();
            return true;
        }
        else if (itemThatWasClickedId == R.id.menu_clear_btn) {
            mAdapter.clearList();
        }
        return super.onOptionsItemSelected(item);
    }

}
