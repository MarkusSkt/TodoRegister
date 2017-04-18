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

import java.util.ArrayList;
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
     * @param task     task
     * @param context  context
     */
    public void writeDb(Task task, Context context) {
        userDbHelper = new UserDbHelper(context);
        //CANNOT DO WITH TRY-CATCH SINCE IT REQUIRES API KITKAT
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addTask(task.getTitle(), task.getContent(), Integer.toString(task.getPriority()), Integer.toString(task.getID()), Integer.toString(task.getState()), sqLiteDatabase);
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
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        userDbHelper.close();
        Log.e("HOW MANY TASKS WE HAVE", ""+ tasks.size());
    }

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
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        userDbHelper.close();
        Log.e("TASKS FOUND", ""+ tasks.size());

    }

    public void updateState(Context context, int id, int state) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTaskState(Integer.toString(id), state, sqLiteDatabase);
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
        tasks.remove(find(id));
        Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
    }


    /**
     * Get all tasks that are not finished
     *
     * @return not finished tasks
     */
    public List<Task> getAllActive() {
        List<Task> tmp = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isFinished())
                tmp.add(task);
        }
        return tmp;
    }

    /**
     * Get all tasks that are finished
     *
     * @return finished tasks
     */
    public List<Task> getAllFinished() {
        List<Task> tmp = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isFinished())
                tmp.add(task);
        }
        return tmp;
    }


    /**
     * Find a task by its id
     *
     * @param id id of the task
     * @return task
     */
    public Task find(int id) {
        Log.e("HOW MANY TASKS WE HAVE", ""+ tasks.size());
        for (Task task : tasks) {
            if (task.getID() == id) {
                Log.e("FOUND ID", ""+ task.getID());
                return task;
            }
        }
        return null;
    }

    /**
     * Add new task
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

    public Task get(int i) {
        return tasks.get(i);
    }

    /**
     * Finish a task and update its
     * state in the database
     * @param id task to be finised
     */
    public void finish(Context context, int id) {
       // Task task = find(id);
        Log.e("TO DELETE ID", ""+id);
      //  if (!task.isFinished()) {
      //      task.finish();
            updateState(context, id, 1);
        //}
    }


    public void removeActive(Task task) {
        tasks.remove(task);
    }

    /**
     * Remove task by its ID
     *
     * @param id id of the task
     */
    public void removeActive(int id) {
        tasks.remove(find(id));
    }

    public void removeFinished(Task task) {
        if (task.isFinished()) {
            tasks.remove(task);
        }
    }

    /**
     * Remove task by its ID
     *
     * @param id id of the task
     */
    public void removeFinished(int id) {
        tasks.remove(find(id));
    }

    /**
     * Create a new task and write it to the database
     * @param context context
     * @param title title of the task
     * @param content content of the task
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
