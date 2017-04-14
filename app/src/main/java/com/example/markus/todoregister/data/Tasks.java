package com.example.markus.todoregister.data;

import com.example.markus.todoregister.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 8.4.2017.
 * Holds all the tasks and keeps track of them(finished and unfinished)
 */
public class Tasks {

    private boolean changed = false;
    private List<Task> activeTasks = new ArrayList<>();
    private List<Task> finishedTasks = new ArrayList<>();


    public void newTask(Task task) {
        activeTasks.add(task);
        changed = true;

    }

    public void finishTask(Task task) {
        finishedTasks.add(task);
        changed = true;

    }

    public void deleteActive(Task task) {
        activeTasks.remove(task);
        changed = true;
    }

    public void deleteFinished(Task task) {
        finishedTasks.remove(task);
        changed = true;
    }

    public void save() {
        changed = false;
    }
}
