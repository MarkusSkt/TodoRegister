package com.example.markus.todoregister.GUI;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.markus.todoregister.R;

import java.util.List;

/**
 * Created by Markus on 21.4.2017.
 * Base class for fragments that are controlled by the pagerAdapater
 * Each Pagefragment:
 *  - Can delete task, has listview and taskadapter
 *  - Fills the listview by reading database
 */

public abstract class PageFragment extends Fragment {

    private TaskAdapter adapter = new TaskAdapter();
    private ListView listView;

    /**
     * Delete a task by id
     * @param id id of the task
     */
    public void delete(int id) {
        adapter.delete(getContext(), id);
    }

    /**
     * Set the adapater for a specific listView
     * @param listView that is shown by the adapter
     */
    public void setAdapter(ListView listView) {
        listView.setAdapter(adapter);
    }

    /**
     * Get the current adapter
     * @return TaskAdapter
     */
    public TaskAdapter getAdapter() {
        return this.adapter;
    }


    /**
     * Fill the listView by reading the right data from the database
     * *right = active or finished tasks
     * @param iRead
     */
    public void fillListView(IRead iRead) {
        iRead.read(getContext());
    }

    /**
     * Set the listView for the fragment
     * @param view view
     * @param ResourceID id of the listview
     */
    public void setListView(View view, int ResourceID) {
        listView = (ListView)view.findViewById(ResourceID);
    }


    /**
     * Get refrence of the current listView
     * @return this.listView
     */
    public ListView getListView() {
        return this.listView;
    }

    /**
     * Register a context menu for the fragment
     * @param listView the contextMenu activates on
     */
    public void registerContextMenu(ListView listView) {
        registerForContextMenu(listView);
    }


    /**
     * Get the item we just selected, and get its position
     * then remove it from the adapter
     * @param item we clicked on "Delete"
     * @return true if we deleted
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(!getUserVisibleHint()) return false;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.delete) {
            delete(adapter.find(info.position).getID());
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
