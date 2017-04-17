package com.example.markus.todoregister.data;

import android.os.Parcel;
import android.util.Log;

import com.example.markus.todoregister.data.Task;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Markus on 7.4.2017.
 * Task which has been given a specific time frame to complete
 * if the time goes to zero, the tasks will show an alert
 * and then delete itself after some time
 */
public class TimedTask extends Task {

    private String currentDate;
    private String endDate;
    private int timeLeftDays;
    private int timeLeftHours;


    public TimedTask(String title, String content, int priority, Date endDate) {
        super(title, content, priority);
        this.currentDate = getDate();
        this.endDate = getDate(endDate);
    }

    /**
     * Get the date this task was made
     *
     * @return date as a String
     */
    private String getDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date today = new Date();
        return dateFormat.format(today);
    }

    /**
     * Get the goal date when this task should be finished
     *
     * @param goal date
     * @return goal date as a string
     */
    private String getDate(Date goal) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return dateFormat.format(goal);
    }

    //integer so don't need to worry about rounding
    public void getTimeLeftDays() {
        Log.d("date: ", currentDate);
        //TODO: From currentDate remove endDate and the time left!

    }

    //integer so don't need to worry about rounding
    public void getTimeLeftHours() {
        //TODO: From currentDate remove endDate and the time left!
    }

    //Update the current date each time user refreshes the app
    public void updateDate() {
        getDate();
    }

}
