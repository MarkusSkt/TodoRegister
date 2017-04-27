package com.example.markus.todoregister.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Markus on 8.4.2017.
 * Holds all the tasks and keeps track of them(finished and unfinished)
 */
public class Tasks {

    private List<Task> tasks = new ArrayList<>();
    private UserDbHelper userDbHelper;
    private SQLiteDatabase sqLiteDatabase;


    /**
     * Write a new task to the database
     *
     * @param task    task
     * @param context context
     */
    private void writeDb(Task task, Context context) throws SQLException {
        userDbHelper = new UserDbHelper(context);
        //CANNOT DO WITH TRY()-CATCH SINCE IT REQUIRES API KITKAT
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addTask(task.getTitle(), task.getContent(), Integer.toString(task.getPriority()),
                Integer.toString(task.getID()), Integer.toString(task.getState()), task.getDate(), sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Task Created", Toast.LENGTH_LONG).show();
    }

    /**
     * Read all tasks from the database that have
     * finished or !finished state
     *
     * @param state 0 = false, 1 = true
     * @param context context
     */
    public void readAllOfState(int state, Context context) throws SQLException {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        Cursor cursor = userDbHelper.getTasksOfState(state, sqLiteDatabase);
        //FIXME: TRY - CATCH
        if (cursor.moveToFirst()) {
            do {
                Task task = new NonTimedTask(
                        cursor.getString(0),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)));
                task.setID(Integer.parseInt(cursor.getString(3)));
                task.setState(Integer.parseInt(cursor.getString(4)));
                task.setDate(cursor.getString(5));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        close(userDbHelper, sqLiteDatabase);
        Collections.sort(tasks);
    }


    /**
     * Update the state (when task is finished) of a task
     *
     * @param context context
     * @param id      id of the task
     * @param state   0 = false, 1 = true
     */
    private void updateState(Context context, int id, int state, String date) throws SQLException {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTaskState(Integer.toString(id), state, date, sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Task Finished", Toast.LENGTH_LONG).show();
    }


    /**
     * Update the task by first getting it from the database by the id
     * and then updating it
     * to here
     * @param context context
     * @param id      id of the task
     */
    public void updateTask(Context context, int id, String title, String content) throws SQLException {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTask(Integer.toString(id), title, content, sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Task Updated", Toast.LENGTH_LONG).show();
    }


    /**
     * Delete a specific task by its ID
     * @param context context
     * @param id      id of the task
     */
    public void delete(Context context, int id) throws SQLException {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        userDbHelper.deleteTask(Integer.toString(id), sqLiteDatabase);
        tasks.remove(findByID(id));
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
    }


    /**
     * Close the database after reading/writing
     * @param helper dbHelper
     * @param db database
     */
    private void close(UserDbHelper helper, SQLiteDatabase db) {
        helper.close();
        db.close();
    }


    /**
     * Find a task from list by its id
     *
     * @param id id of the task
     * @return task
     */
    public Task findByID(int id) {
        for (Task task : tasks) {
            if (task.getID() == id)
                return task;
        }
        return null;
    }


    //Get the date of today
    private String date() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date today = new Date();
        return dateFormat.format(today);
    }


    /**
     * Find a task by its index in the list
     * @param i index
     * @return task
     */
    public Task find(int i) {
        return tasks.get(i);
    }



    public void addAll(ArrayList<Task> taskList) {
        tasks.clear();
        for (Task task: taskList) {
            tasks.add(task);
            task.register();
        }
    }

    public List<Task> getAll() {
        return tasks;
    }


    /**
     * Get the amount of tasks we have in a list
     * @return task amount
     */
    public int size() {
        return tasks.size();
    }


    /**
     * Finish task by updating it first
     * if there has been any channges
     * @param context context
     * @param id id of the task
     * @param title new title
     * @param content new content
     */
    public void finish(Context context, int id, String title, String content) {
        updateTask(context, id, title, content);
        updateState(context, id, 1, date());
    }


    /**
     * Create a new task and write it to the database
     *
     * @param context  context
     * @param title    title of the task
     * @param content  content of the task
     * @param priority priority of the task
     * @return new Task
     */
    public Task newTask(Context context, String title, String content, int priority) {
        Task task = new NonTimedTask(title, content, priority);
        task.register();
        writeDb(task, context);
        return task;
    }
}
