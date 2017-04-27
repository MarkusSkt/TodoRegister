package com.example.markus.todoregister.data;

/**
 * Created by Markus on 23.4.2017.
 * Functional interface for basic commands
 */

public interface Command {
    void execute(int id, String title, String content);
}
