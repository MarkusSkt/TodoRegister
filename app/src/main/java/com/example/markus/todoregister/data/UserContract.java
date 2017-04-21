package com.example.markus.todoregister.data;

/**
 * Created by Markus on 17.4.2017.
 *
 */

public class UserContract {

    public static abstract class UserTaskInfo {
        public static final String TITLE = "task_title";
        public static final String CONTENT = "task_content";
        public static final String PRIORITY = "task_priority";
        public static final String ID = "task_ID";
        public static final String STATE = "task_state";
        public static final String DATE = "finish_date";
        public static final String TABLE_NAME = "task_info";
    }
}
