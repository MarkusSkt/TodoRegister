package com.example.markus.todoregister.gui;

import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.markus.todoregister.R;
import com.example.markus.todoregister.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 16.4.2017.
 * Base class for fragments that are controlled by the PagerAdapater
 * Makes it easier to add new pages(e.g. TimedTasksFragment) if needed
 * Each PageFragment:
 * - Has a TaskAdapter
 * - Fills the ListView by reading the database
 * - Can delete a task
 * - Has a SearchView
 */

public abstract class PageFragment extends Fragment {


    private TaskAdapter adapter = new TaskAdapter();
    private List<Task> tmpTasks = new ArrayList<>();
    //Keep a reference of all the tasks so it is easier to handle the search

    private boolean searched = false;

    /**
     * Set the listView for the fragment
     * @param view view
     */
    protected abstract void setListView(View view);


    /**
     * Set the adapater for a specific listView
     */
    protected abstract void setAdapter();

    /**
     * Delete a task by id
     *
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
     *
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


    /**
     * Create search bar
     *
     * @param menu     menu of the fragment
     * @param inflater inflates the menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        saveAllCurrentTasks();
        setSearchBar(menu);

    }
    /**
     * Set a search bar for all the page fragments
     * Search for tasks that contain the typed text "newText" and
     * show them only in the listview
     * @param menu menu of the fragment
     */
    private void setSearchBar(Menu menu) {
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
                //No need to do anything
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Task> matches = new ArrayList<>();
                for (Task task : tmpTasks) {
                    if (task.getTitle().contains(newText)) {
                        matches.add(task);
                    }
                }
                adapter.setMatches(matches);
                return true;
            }
        });
    }

    /**
     * Save all the current tasks so handling the search
     * is easier
     */
    public void saveAllCurrentTasks() {
        tmpTasks.clear();
        tmpTasks.addAll(adapter.getAll());
    }
}
