package com.example.markus.todoregister.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Markus on 17.4.2017.
 * Database helper
 * FIXME: GENERATE ALL THE UserTaskInfo with loops,
 * FIXME: so adding new column is easier!
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TASKS_DB6";
    private static final int DATABASE_VERSION = 6;
    private static final String CREATE_QUERY = "CREATE TABLE " +
            UserContract.UserTaskInfo.TABLE_NAME +
            "(" + UserContract.UserTaskInfo.TITLE + " TEXT," +
            UserContract.UserTaskInfo.CONTENT + " TEXT," +
            UserContract.UserTaskInfo.PRIORITY + " TEXT," +
            UserContract.UserTaskInfo.STATE + " TEXT," +
            UserContract.UserTaskInfo.DATE + " TEXT," +
            UserContract.UserTaskInfo.ID + " TEXT);";


    /**
     * Constructor to give the db a name and version
     *
     * @param context context
     */
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    //When the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }


    /**
     * Get all the projections
     *
     * @return What 1 db item consists of
     */
    private String[] getProjections() {
        return new String[]{
                UserContract.UserTaskInfo.TITLE,
                UserContract.UserTaskInfo.CONTENT,
                UserContract.UserTaskInfo.PRIORITY,
                UserContract.UserTaskInfo.ID,
                UserContract.UserTaskInfo.STATE,
                UserContract.UserTaskInfo.DATE};
    }


    /**
     * Used to get a specific task from the database
     *
     * @param ID id of the task
     * @param db database
     * @return task as a Cursor object
     */
    @SuppressWarnings("unused")
    public Cursor getTask(String ID, SQLiteDatabase db) {
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return db.query(UserContract.UserTaskInfo.TABLE_NAME, getProjections(), selection, selection_args, null, null, null);
    }


    /**
     * Get a task by its state(finished or not finished)
     *
     * @param state search condition
     * @param db    database
     * @return tasks with the specific state
     */
    public Cursor getTasksOfState(int state, SQLiteDatabase db) {
        String selection = UserContract.UserTaskInfo.STATE + " LIKE ?";
        String[] selection_args = {Integer.toString(state)};
        return db.query(UserContract.UserTaskInfo.TABLE_NAME, getProjections(), selection, selection_args, null, null, null);
    }


    /**
     * Add a task to the database
     *
     * @param title    title of the task
     * @param content  of the task
     * @param priority of the task
     * @param id       of the task
     * @param state    of the task
     * @param date     of the task
     * @param db       database
     */
    public void addTask(String title, String content, String priority, String id, String state, String date, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, title);
        contentValues.put(UserContract.UserTaskInfo.CONTENT, content);
        contentValues.put(UserContract.UserTaskInfo.PRIORITY, priority);
        contentValues.put(UserContract.UserTaskInfo.ID, id);
        contentValues.put(UserContract.UserTaskInfo.STATE, state);
        contentValues.put(UserContract.UserTaskInfo.DATE, date);
        db.insert(UserContract.UserTaskInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE: ", "Row inserted...");
    }


    /**
     * Delete a task from the database by ID
     *
     * @param ID             id of the task
     * @param sqLiteDatabase db
     */
    public void deleteTask(String ID, SQLiteDatabase sqLiteDatabase) {
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        sqLiteDatabase.delete(UserContract.UserTaskInfo.TABLE_NAME, selection, selection_args);
    }


    /**
     * Update task from the database
     *
     * @param ID             id of the updating
     * @param newTitle       new title
     * @param newContent     new content
     * @param sqLiteDatabase db
     * @return how many rows changed
     */
    public int updateTask(String ID, String newTitle, String newContent, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, newTitle);
        contentValues.put(UserContract.UserTaskInfo.CONTENT, newContent);
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return sqLiteDatabase.update(UserContract.UserTaskInfo.TABLE_NAME, contentValues, selection, selection_args);
    }


    /**
     * When task is finished, update it's state on the database
     * and give the task a proper finish date
     *
     * @param ID             id of the task
     * @param newState       0 = false, 1 = true
     * @param finishDate     finishDate
     * @param sqLiteDatabase db
     * @return how many rows updated
     */
    public int updateTaskState(String ID, int newState, String finishDate, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.STATE, Integer.toString(newState));
        contentValues.put(UserContract.UserTaskInfo.DATE, finishDate);
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return sqLiteDatabase.update(UserContract.UserTaskInfo.TABLE_NAME, contentValues, selection, selection_args);
        //RETURNS updated amount of rows
    }


    /**
     * When the database is upgraded
     *
     * @param db         database
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DATABASE UPDATE", "Upgrading database from version" + oldVersion + "to" + newVersion + "which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);

    }
}
