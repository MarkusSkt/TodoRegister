package com.example.markus.todoregister;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.markus.todoregister.data.Task;
import com.example.markus.todoregister.data.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 8.4.2017.
 * Handles showing the tasks with fragments
 */

public class TaskAdapter extends BaseAdapter {

    private Tasks tasks = new Tasks();
    private int[] priorityImages = {R.drawable.man_standing, R.drawable.man_walking, R.drawable.man_running};
    private int[] priorityColors = new int[3];

    public Task newTask(Context context, String title, String content, int priority) {
        Task task = tasks.newTask(context, title, content, priority);
        return task;
    }

    private static class DataHandler {
        RelativeLayout priorityBackground;
        ImageView priorityImage;
        TextView title;
        TextView content;
        TextView date;
    }

    /**
     * Take all the active tasks
     * @param context context
     */
    public void readActive(Context context) {
        tasks.readAllOfState(0, context);
    }

    /**
     * Get all the finished tasks
     * @param context c
     */
    public void readFinished(Context context) {
        tasks.readAllOfState(1, context);
    }

    public void readAll(Context context) {
        tasks.readALl(context);
    }

    public Task find(int i) {
        return tasks.find(i);
    }

    public void add(@Nullable Task object) {
        tasks.add(object);
        notifyDataSetChanged();
    }


    public Task findByID(int id) {
        return tasks.findByID(id);
    }

    public void delete(Context context, int id) {
        tasks.delete(context, id);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
            return tasks.find(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.find(position).getID();
    }

    //Remove by task
    public void removeActive(Task task) {
        tasks.removeActive(task);
        notifyDataSetChanged();
    }

    //finish task
    public void finish(Context context, int id) {
        tasks.finish(context, id);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * Here we handle how the views are shown in the listview
     * @param position    pos
     * @param convertView the view we are looking(row)
     * @param parent      parent of the view
     * @return view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DataHandler handler;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row, parent, false); //GET THE SPECIFIC ROW with the inflater
            setColors(parent);
            //SET ALL THE DATA FROM THE IDS
            handler = new DataHandler();
            handler.priorityImage = (ImageView) row.findViewById(R.id.priorityImage);
            handler.priorityBackground = (RelativeLayout) row.findViewById(R.id.rowBackground);
            handler.title = (TextView) row.findViewById(R.id.taskTitle);
            handler.content = (TextView) row.findViewById(R.id.taskContent);
            handler.date = (TextView) row.findViewById(R.id.finishDate);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        Task task = (Task) this.getItem(position);
       // Log.e("TASK VIEW", ""+task.isFinished());
        if(task != null) {
            handler.title.setText(task.getTitle());
            handler.content.setText(task.getContent());
            handler.priorityImage.setImageResource(priorityImages[task.getPriority()]);
            handler.date.setText(task.getDate());
            if(!task.isFinished()) {
                handler.priorityBackground.setBackgroundColor(priorityColors[task.getPriority()]);
            }
            else {
                handler.priorityBackground.setBackgroundColor(Color.WHITE);
            }
        }
        return row;
    }

    /**
     * Set the colors that are used on showing the
     * tasks priority
     * @param parent wiewgroup
     */
    private void setColors(ViewGroup parent) {
        priorityColors[0] = ResourcesCompat.getColor(parent.getResources(), R.color.tabsScrollColor, null);
        priorityColors[1] = ResourcesCompat.getColor(parent.getResources(), R.color.medium_priority_color, null);
        priorityColors[2] = ResourcesCompat.getColor(parent.getResources(), R.color.high_priority_color, null);
    }
}
