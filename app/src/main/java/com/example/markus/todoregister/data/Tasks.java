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

    private boolean changed = false;
    private List<Task> tasks = new ArrayList<>();

    private UserDbHelper userDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Tasks() {

    }

    public Cursor getTasks(SQLiteDatabase db) {
        String[] projections = {
                UserContract.UserTaskInfo.TITLE,
                UserContract.UserTaskInfo.CONTENT,
                UserContract.UserTaskInfo.PRIORITY,
                UserContract.UserTaskInfo.ID,
                UserContract.UserTaskInfo.STATE};
        if (projections != null)
            return db.query(UserContract.UserTaskInfo.TABLE_NAME, projections, null, null, null, null, null);

        return null;
    }


    /**
     * Write to the database
     *
     * @param task     task
     * @param context  context
     * @param title    title of the task
     * @param content  content of the task
     * @param priority priority of the task
     */
    public void writeDb(Task task, Context context, String title, String content, int priority) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addTask(title, content, Integer.toString(priority), Integer.toString(task.getID()), task.getState(), sqLiteDatabase);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show();
        userDbHelper.close();
    }

    /**
     * Read from the database
     *
     * @param context context
     */
    public void readDb(Context context) {
        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        Cursor cursor = getTasks(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
                Task task = new NonTimedTask(
                        cursor.getString(0),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)));
                //FIXME: TRY - PARSE
                //FIXME: GET ID AND STATE
                //    task.setState(cursor.getString(3));
                task.setID(Integer.parseInt(cursor.getString(3)));
                Log.e("ID ", cursor.getString(3));
                //Log.e("STATE ", cursor.getString(4));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        userDbHelper.close();
    }

    public void updateState(Context context, int id, String state) {
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
        for (Task task : tasks) {
            if (task.getID() == id)
                return task;
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
        changed = true;

    }

    public int size() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    /**
     * Finish a task
     *
     * @param task task to be finised
     */
    public void finish(Task task) {
        if (!task.isFinished()) {
            task.finish();
            changed = true;
        }
    }

    /**
     * Finish task by its id
     *
     * @param id id of the task
     */
    public void finish(int id) {
        for (Task task : tasks) {
            if (task.getID() == id) {
                task.finish();
                return;
            }
        }
    }

    public void removeActive(Task task) {
        tasks.remove(task);
        changed = true;
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
            changed = true;
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

    public void save() {
        changed = false;
    }

    public Task newTask(Context context, String title, String content, int priority) {
        Task task = new NonTimedTask(title, content, priority);
        task.register();
        writeDb(task, context, title, content, priority);
        return task;

    }

}
