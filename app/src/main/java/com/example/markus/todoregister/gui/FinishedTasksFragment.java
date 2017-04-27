package com.example.markus.todoregister.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.markus.todoregister.R;

/**
 * Created by Markus on 13.4.2017.
 * Fragment for the list of tasks that are finished
 * Can show and delete the finished tasks
 */

public class FinishedTasksFragment extends PageFragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finished_tasks, container, false);
        setListView(view);
        setAdapter();
        registerForContextMenu(listView);
        final int finished = 1;
        fillListView(finished);
        setHasOptionsMenu(true);
        return view;
    }

    // Set the listview
    @Override
    protected void setListView(View view) {
        listView = (ListView) view.findViewById(R.id.finishedTasksList);
    }

    //Set the adapter to the listview
    @Override
    protected void setAdapter() {
        listView.setAdapter(getAdapter());
    }

}
