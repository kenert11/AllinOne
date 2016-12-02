package com.example.kenert.allinoneapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kenert.allinoneapp.TaskTable.TaskContract;
import com.example.kenert.allinoneapp.TaskTable.TaskDbHelper;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper cusHelper;
    private ListView todo_list;
    private ArrayAdapter<String> cusAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        cusHelper = new TaskDbHelper(this);
        todo_list= (ListView) findViewById(R.id.todo_list);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //Update the task list when app is opened
        updateTasks();



    }
    //Updates the list
    private void updateTasks(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = cusHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TaskContract.TaskEntry.TABLE,new String[]{
                TaskContract.TaskEntry._ID, TaskContract.TaskEntry.TASK_TITLE
        },null,null,null,null,null);
        while(cursor.moveToNext()){
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.TASK_TITLE);
            taskList.add(cursor.getString(idx));

        }
        if(cusAdapter == null){
            cusAdapter = new ArrayAdapter<>(this,R.layout.item_todo,R.id.task_title,
                    taskList);
            todo_list= (ListView) findViewById(R.id.todo_list);
            todo_list.setAdapter(cusAdapter);
        }else{
            cusAdapter.clear();
            cusAdapter.addAll(taskList);
            cusAdapter.notifyDataSetChanged();
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText= new EditText(this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Add a new task");
                dialog.setMessage("What do you wish to do next?");
                dialog.setView(taskEditText);
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = String.valueOf(taskEditText.getText());
                        SQLiteDatabase sqLiteDatabase = cusHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.TASK_TITLE, task);
                        sqLiteDatabase.insertWithOnConflict(TaskContract.TaskEntry.TABLE,null,
                                values, SQLiteDatabase.CONFLICT_REPLACE);
                        sqLiteDatabase.close();
                        updateTasks();
                    }
                });
                        dialog.setNegativeButton("Cancel",null);
                        dialog.create();
                dialog.show();
                Log.d(TAG, "Add a new task");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
    public void deleteTask(View view){
        View parent =(View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase sqLiteDatabase = cusHelper.getWritableDatabase();
        sqLiteDatabase.delete(TaskContract.TaskEntry.TABLE,TaskContract.TaskEntry.TASK_TITLE +
        " = ?",new String[]{task});
        sqLiteDatabase.close();
        updateTasks();
    }
}
