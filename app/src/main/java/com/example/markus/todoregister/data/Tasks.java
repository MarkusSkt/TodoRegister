package com.example.markus.todoregister.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.markus.todoregister.UserContract;
import com.example.markus.todoregister.UserDbHelper;
import com.example.markus.todoregister.data.Task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

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
    public void writeDb(Task task, Context context) {
        userDbHelper = new UserDbHelper(context);
        //CANNOT DO WITH TRY-CATCH SINCE IT REQUIRES API KITKAT
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addTask(task.getTitle(), task.getContent(), Integer.toString(task.getPriority()),
                Integer.toString(task.getID()), Integer.toString(task.getState()), task.getDate(), sqLiteDatabase);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show();
        userDbHelper.close();
    }

    /**
     * Read all the tasks from the database
     *
     * @param context context
     */
    public void readALl(Context context) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        Cursor cursor = userDbHelper.getTasks(sqLiteDatabase);
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
        userDbHelper.close();
        Log.e("HOW MANY TASKS WE HAVE", "" + tasks.size());
        ;
    }

    /**
     * Read all tasks from the database that have
     * finished or !finished state
     *
     * @param state   0 = false, 1 = true
     * @param context context
     */
    public void readAllOfState(int state, Context context) {
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
        userDbHelper.close();
        Log.e("TASKS FOUND", "" + tasks.size());
        Collections.sort(tasks);
    }

    /**
     * Update the state (when task is finished) of a task
     *
     * @param context context
     * @param id      id of the task
     * @param state   0 = false, 1 = true
     */
    public void updateState(Context context, int id, int state, String date) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTaskState(Integer.toString(id), state, date, sqLiteDatabase);
        Toast.makeText(context, "State Updated", Toast.LENGTH_LONG).show();
        userDbHelper.close();
    }


    /**
     * Delete a specific task by its ID
     *
     * @param context context
     * @param id      id of the task
     */
    public void delete(Context context, int id) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        userDbHelper.deleteTask(Integer.toString(id), sqLiteDatabase);
        tasks.remove(findByID(id));
        Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
        sqLiteDatabase.close();
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

    public String date() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date today = new Date();
        return dateFormat.format(today);
    }


    /**
     * Find a task by its position in the list
     *
     * @param i index
     * @return task
     */
    public Task find(int i) {
        return tasks.get(i);
    }

    /**
     * Add new task to the tasks list
     *
     * @param task task to be added
     */
    public void add(Task task) {
        tasks.add(task);
        task.register();
    }


    public int size() {
        return tasks.size();
    }


    public void finish(Context context, int id) {
        updateState(context, id, 1, date());
    }


    public void removeActive(Task task) {
        tasks.remove(task);
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
