package com.example.markus.todoregister;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.markus.todoregister.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 13.4.2017.
 * Fragment for the list of tasks that are finished
 */

public class FinishedTasksFragment extends Fragment {
    private ListView finishedTasksList;
    private TaskAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finished_tasks, container, false);
        registerComponents(view);
        registerAdapter();
        showFinishedTaskList();
        return view;
    }

    private void registerComponents(View view) {
        finishedTasksList = (ListView) view.findViewById(R.id.finishedTasksList);
    }

    private void registerAdapter() {
        adapter = new TaskAdapter();
        finishedTasksList.setAdapter(adapter);
    }

    /**
     * When the activity starts,
     * initialize the listview of finishedTasks
     */
    public void showFinishedTaskList() {
        adapter.readFinished(getContext());
    }

}
