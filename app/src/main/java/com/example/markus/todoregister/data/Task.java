package com.example.markus.todoregister.data;

/**
 * Created by Markus Skytta on 7.4.2017.
 * Base class for all the different tasks
 */

public abstract class Task implements Comparable<Task> {

    private String title, content;
    private String date = "";
    private  int priority; //0-2
    private int ID;
    private boolean done;

    private static int nextN = 1;


    /**
     * Compare tasks to show them in the order of
     * highest priority
     * @param t2 task2
     * @return integer
     */
    @Override
    public int compareTo(Task t2) {
        return t2.getPriority() - this.getPriority();
    }


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


    /**
     * Get the state and return it as an integer for
     * the database
     * @return 0 = false, 1 = true
     */
    public int getState() {
        return isFinished() ? 1 : 0;
    }


    /**
     * Sett the state from int back to boolean
     * @param state state
     */
    public void setState(int state) {
        if(state == 1) {
            done = true;
            return;
        }
        done = false;
    }


    /**
     * Get the finish date of this task if it has one
     * @return today
     */
    public String getDate() {
        return this.date;
    }


    /**
     * Read the date from the database and set it
     */
    public void setDate(String date) {
       this.date = date;
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
     * @return toString
     */
    @Override
    public String toString() {
        return this.title;
    }


    //Check if the task is finished already
    public boolean isFinished() {
        return done;
    }


    //Getter
    public String getTitle() {
        return this.title;
    }


    //Getter
    public String getContent() {
        return this.content;
    }


    //Getter
    public int getPriority() {
        return this.priority;
    }
}
