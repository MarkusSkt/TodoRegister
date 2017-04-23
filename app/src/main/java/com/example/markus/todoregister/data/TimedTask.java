package com.example.markus.todoregister.data;

import android.util.Log;
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


    @SuppressWarnings("unused")
    public TimedTask(String title, String content, int priority, Date endDate) {
        super(title, content, priority);
        this.currentDate = getDate();
        //this.endDate = getDate(endDate);
    }

    //integer so don't need to worry about rounding
    @SuppressWarnings("unused")
    public void getTimeLeftDays() {
        Log.d("date: ", currentDate);
        //TODO: From currentDate remove endDate and the time left!

    }

    //integer so don't need to worry about rounding
    @SuppressWarnings("unused")
    public void getTimeLeftHours() {
        //TODO: From currentDate remove endDate and the time left!
    }

    //Update the current date each time user refreshes the app
    @SuppressWarnings("unused")
    public void updateDate() {
        getDate();
    }

}
