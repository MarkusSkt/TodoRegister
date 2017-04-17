package com.example.markus.todoregister.data;

import android.os.Parcel;

import com.example.markus.todoregister.data.Task;

/**
 * Created by Markus on 8.4.2017.
 * Task that has no time limit, so it just stays on the
 * list until user removes it
 */

public class NonTimedTask extends Task {

    public NonTimedTask(String title, String content, int priority) {
        super(title, content, priority);
    }

}
