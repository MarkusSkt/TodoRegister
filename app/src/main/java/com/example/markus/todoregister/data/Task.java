package com.example.markus.todoregister.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Markus Skytta on 7.4.2017.
 * Base class for all the different tasks
 */

public abstract class Task {

    //Can't change task once made
    private String title, content;
    private  int priority; //0-2
    private int ID;
    private boolean done;
    //CANNOT create a task that is already done


    private static int nextN = 1;

    //Task Constructor
    public Task(String title, String content, int priority) {
        this.title = title;
        this.content = content;
        this.priority = setPriority(priority);
        this.done = false;
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
     * Register the Task by giving it an ID
     * @return id of the task
     */
    public int register() {
        if (ID != 0)
            return ID;

        this.ID = nextN++;
        return this.ID;
    }


    /**
     * Set an ID to the project and make
     * sure the nextN is up to date!
     * @param n number
     */
    public int setID(int n) {
        this.ID = n;
        if (this.ID >= nextN)
            nextN = this.ID + 1;
        // Eli jos tämän projektin ID > Seuraava ID
        // Niin laitetaan seuraava ID +1 tämä
        return this.ID;
    }


    //FIXME: DO SOMETHING BETTER WITH THIS
    public String getState() {
        if(isFinished())
            return "True";

        return "False";
    }

    public void setState(String state) {
        if(state == "True")
            done = true;

        done = false;
    }


    /**
     * Get the ID of the curent task
     * @return ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * ToString
     *
     * @return toString
     */
    @Override
    public String toString() {
        return this.title;
    }

    //When we want to finish the task
    public boolean finish() {
        return done = true;
    }

    //Check if the task is finished already
    public boolean isFinished() {
        return done;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public int getPriority() {
        return this.priority;
    }
}
