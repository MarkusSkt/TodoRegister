package com.example.markus.todoregister.data;

/**
 * Created by Markus on 23.4.2017.
 * Command pattern for removing some nested if's
 */

public interface Command {
    void execute(int id, String title, String content);
}
