package com.example.markus.todoregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Markus on 17.4.2017.
 *
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TASKS_DB5";
    private static final int DATABASE_VERSION = 5;
    private static final String CREATE_QUERY = "CREATE TABLE " +
            UserContract.UserTaskInfo.TABLE_NAME +
            "("+ UserContract.UserTaskInfo.TITLE + " TEXT," +
            UserContract.UserTaskInfo.CONTENT + " TEXT," +
            UserContract.UserTaskInfo.PRIORITY + " TEXT," +
            UserContract.UserTaskInfo.STATE + " TEXT," +
            UserContract.UserTaskInfo.ID + " TEXT);";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public Cursor getTask(String ID, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {UserContract.UserTaskInfo.TITLE, UserContract.UserTaskInfo.CONTENT};
        String selection = UserContract.UserTaskInfo.ID+ " LIKE ?";
        String[] selection_args = {ID};
        return sqLiteDatabase.query(UserContract.UserTaskInfo.TABLE_NAME, projections, selection, selection_args, null, null, null);
        //THEN ON SOME searchTask() METHOD
            // getTask(id.toString(), sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }

    public void addTask(String title, String content, String priority, String id, String state, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, title);
        contentValues.put(UserContract.UserTaskInfo.CONTENT, content);
        contentValues.put(UserContract.UserTaskInfo.PRIORITY, priority);
        contentValues.put(UserContract.UserTaskInfo.ID, id);
        contentValues.put(UserContract.UserTaskInfo.STATE, state);
        db.insert(UserContract.UserTaskInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "Row inserted...");
    }

    public void deleteTask(String ID, SQLiteDatabase sqLiteDatabase) {
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        sqLiteDatabase.delete(UserContract.UserTaskInfo.TABLE_NAME, selection, selection_args);
    }

    public void updateTask(String oldTitle, String newTitle, String oldContent, String newContent, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, newTitle);
        contentValues.put(UserContract.UserTaskInfo.CONTENT, newContent);
    }
    public int updateTaskState(String ID, String newState, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.STATE, newState);
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        int count = sqLiteDatabase.update(UserContract.UserTaskInfo.TABLE_NAME, contentValues, selection, selection_args);
        //RETURNS updated amount of rows
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DATABASE UPDATE", "Upgrading database from version" + oldVersion + "to" + newVersion + "which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);

    }
}
