package com.example.markus.todoregister.GUI;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.markus.todoregister.R;

/**
 * Created by Markus on 16.4.2017.
 * Base class for fragments that are controlled by the PagerAdapater
 * Makes it easier to add new pages(e.g. TimedTasksFragment) if needed
 * Each Pagefragment:
 * - Has a TaskAdapter
 * - Fills the listview by reading the database
 * - Can delete a task
 */

public abstract class PageFragment extends Fragment{


    private TaskAdapter adapter = new TaskAdapter();


    /**
     * Set the listView for the fragment
     * @param view       view
     */
    protected abstract void setListView(View view);


    /**
     * Set the adapater for a specific listView
     */
    protected abstract void setAdapter();

    /**
     * Delete a task by id
     * @param id id of the task
     */
    protected void delete(int id) {
        adapter.delete(getContext(), id);
    }

    /**
     * Get the current adapter
     *
     * @return TaskAdapter
     */
    public TaskAdapter getAdapter() {
        return this.adapter;
    }


    /**
     * Fill the listView by reading the right data from the database
     * *right = active or finished tasks
     *
     * @param state for reading the db
     */
    protected void fillListView(int state) {
        adapter.readAllOfState(getContext(), state);
    }


    /**
     * Get the item we just selected, and get its position
     * then remove it from the adapter
     * @param item we clicked on "Delete"
     * @return true if we deleted
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (!getUserVisibleHint()) return false;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.delete) {
            delete(getAdapter().find(info.position).getID());
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
