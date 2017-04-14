package com.example.markus.todoregister;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.markus.todoregister.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 8.4.2017.
 * This Would be like the "Tasks" Class!!
 */

public class TaskAdapter extends BaseAdapter {

    private int[] priorityImages = {R.drawable.man_standing, R.drawable.man_walking, R.drawable.man_running};
    private int[] priorityColors = new int[3];

    private static class DataHandler {
        RelativeLayout priorityBackground;
        ImageView priorityImage;
        TextView title;
        TextView content;
    }


    private List<Task> tasks = new ArrayList();

    public Task get(int i) {
        return tasks.get(i);
    }

    public void add(@Nullable Task object) {
        tasks.add(object);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void remove(Task task) {
        tasks.remove(task);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Here we handle the UI View - the data
     *
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
            priorityColors[0] = ResourcesCompat.getColor(parent.getResources(), R.color.tabsScrollColor, null);
            priorityColors[1] = ResourcesCompat.getColor(parent.getResources(), R.color.medium_priority_color, null);
            priorityColors[2] = ResourcesCompat.getColor(parent.getResources(), R.color.high_priority_color, null);


            //SET ALL THE DATA FROM THE IDS
            handler = new DataHandler();
            handler.priorityImage = (ImageView) row.findViewById(R.id.priorityImage);
            handler.priorityBackground = (RelativeLayout) row.findViewById(R.id.rowBackground);
            handler.title = (TextView) row.findViewById(R.id.taskTitle);
            handler.content = (TextView) row.findViewById(R.id.taskContent);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        Task task = (Task) this.getItem(position);
        handler.priorityImage.setImageResource(priorityImages[task.getPriority()]);
        handler.priorityBackground.setBackgroundColor(priorityColors[task.getPriority()]);
        handler.title.setText(task.getTitle());
        handler.content.setText(task.getContent());
        return row;
    }
}
