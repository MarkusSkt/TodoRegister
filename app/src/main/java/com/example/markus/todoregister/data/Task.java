package com.example.markus.todoregister.data;

/**
 * Created by Markus Skytta on 7.4.2017.
 * Base class for all the different tasks
 */

public abstract class Task {

    //Can't change task once made
    private final String title;
    private final String content;
    private final int priority; //0-2
    private boolean done = false; //CANNOT create a task that is already done

    //Task Constructor
    public Task(String title, String content, int priority) {
        this.title = title;
        this.content = content;
        this.priority = setPriority(priority);
}
    //Makes sure the task priority is never too high or low
    private int setPriority(int priority) {
        int maxPriority = 2;
        int minPriority = 0;
        if (priority > maxPriority)
            return maxPriority;

        return Math.max(minPriority, priority);
    }

    /**
     * ToString
     * @return tostring
     */
    @Override
    public String toString() {
        return this.title;
    }

    //When we want to finish the task
    public boolean finish() {
        return done = true;
    }

    public String getTitle() {
        return this.title;
    }
    public String getContent() { return this.content; }
    public int getPriority() {return this.priority;}
}
