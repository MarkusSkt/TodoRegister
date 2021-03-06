package com.example.markus.todoregister.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.markus.todoregister.R;
import com.example.markus.todoregister.data.Task;
import com.example.markus.todoregister.data.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 8.4.2017.
 * Link between Tasks class and the fragments
 * that use this adapter(pages)
 * Handles showing the tasks with fragments
 */

public class TaskAdapter extends BaseAdapter  {

    private Tasks tasks = new Tasks();
    private int[] priorityImages = {R.drawable.man_standing, R.drawable.man_walking, R.drawable.man_running};


    /**
     * Class for handing the views of the row
     */
    private static class DataHandler {
        // private RelativeLayout priorityBackground;
        private ImageView priorityImage;
        private TextView title;
        private TextView content;
        private TextView date;
    }


    /**
     * New task
     * @param context context
     * @param title title of thetask
     * @param content content of the task
     * @param priority task priority
     * @return task
     */
    public Task newTask(Context context, String title, String content, int priority) {
        return tasks.newTask(context, title, content, priority);
    }


    /**
     * Update task
     * @param context context
     * @param id id of the task
     * @param title new title
     * @param content new content
     */
    public void updateTask(Context context, int id, String title, String content) {
        tasks.updateTask(context, id, title, content);
    }


    /**
     * Read all tasks with the wanted state
     * @param context context
     * @param state 0 = not finished, 1 = finished
     */
    public void readAllOfState(Context context, int state) {
        tasks.readAllOfState(state, context);
    }


    /**
     * Find task by index
     * @param i index
     * @return task
     */
    public Task find(int i) {
        return tasks.find(i);
    }


    /**
     * Delete task
     * @param context context
     * @param id id of the task to be deleted
     */
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


    //finish task
    public void finish(Context context, int id, String title, String content) {
        tasks.finish(context, id, title, content);
        notifyDataSetChanged();
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    /**
     * Here we handle how the views are shown in the listview
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
            row = inflater.inflate(R.layout.row, parent, false);
            handler = new DataHandler();
            getListComponents(handler, row);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        Task task = (Task) this.getItem(position);
        setListComponents(handler, task);
        return row;
    }


    /**
     * Get the components of one ROW, to be shown on the list
     * @param handler class for keeping the views
     * @param row row
     */
    public void getListComponents(DataHandler handler, View row) {
        handler.priorityImage = (ImageView) row.findViewById(R.id.priorityImage);
       // handler.priorityBackground = (RelativeLayout) row.findViewById(R.id.base);
        handler.title = (TextView) row.findViewById(R.id.taskTitle);
        handler.content = (TextView) row.findViewById(R.id.taskContent);
        handler.date = (TextView) row.findViewById(R.id.finishDate);
    }


    /**
     * Get the data from the task and set them to show in the row
     * components
     * @param handler handler
     * @param task task data that we show as a row
     */
    public void setListComponents(DataHandler handler, Task task) {
        handler.title.setText(task.getTitle());
        handler.content.setText(task.getContent());
        handler.priorityImage.setImageResource(priorityImages[task.getPriority()]);
        handler.date.setText(task.getDate());
    }

    public void setMatches(ArrayList<Task> taskList) {
        tasks.addAll(taskList);
        notifyDataSetChanged();

    }

    public List<Task> getAll() {
        return tasks.getAll();
    }

//    private void setColors(ViewGroup parent) {
//        priorityColors[0] = ResourcesCompat.getColor(parent.getResources(), R.color.tabsScrollColor, null);
//        priorityColors[1] = ResourcesCompat.getColor(parent.getResources(), R.color.medium_priority_color, null);
//        priorityColors[2] = ResourcesCompat.getColor(parent.getResources(), R.color.high_priority_color, null);
//    }
}
