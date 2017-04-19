package com.example.markus.todoregister;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TaskAdapter adapter = new TaskAdapter();
    private ListView finishedTasksList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finished_tasks, container, false);
        registerComponents(view);
        registerContextMenu();
        setAdapter();
        showFinishedTaskList();
        return view;
    }

    /**
     * Get the item we just selected, and get its position
     * then remove it from the adapter
     * @param item we clicked on "Delete"
     * @return true if we deleted
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(!getUserVisibleHint())
        {
            return false;
        }
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        Log.e("AMOUNT OF FINISHED", ""+adapter.getCount());
        if (id == R.id.deleteFinished) {
            delete(adapter.find(info.position).getID());
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Remove task by its id
     *
     * @param id of the task
     */
    public void delete(int id) {
        adapter.delete(getContext(), id);
    }


    //When pressed long show contextMenu
    public void registerContextMenu() {
        registerForContextMenu(finishedTasksList);
    }

    public void registerComponents(View view) {
        finishedTasksList = (ListView) view.findViewById(R.id.finishedTasksList);
    }

    /**
     * When the activity starts,
     * initialize the listview of finishedTasks
     */
    public void showFinishedTaskList() {
        adapter.readFinished(getContext());
    }

    public void setAdapter() {
        finishedTasksList.setAdapter(adapter);
    }

}
