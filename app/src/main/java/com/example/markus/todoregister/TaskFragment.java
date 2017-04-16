package com.example.markus.todoregister;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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

import com.example.markus.todoregister.data.NonTimedTask;
import com.example.markus.todoregister.data.Task;

/**
 * Created by Markus on 10.4.2017.
 * Part of the MainActivity
 * Handles all the logic behind the task fragment
 * Handles communication between the TaskAdapter(listview)
 * Handles the contextMenu
 * Handles adding/deleting/completing a task
 */

public class TaskFragment extends Fragment {
    private ListView taskList;
    private ImageButton taskButton;
    private TaskAdapter adapter;

    private OnTaskClickedListener tCallBack;

    public interface OnTaskClickedListener {
        void onTaskClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_layout, container, false);
        taskList = (ListView) view.findViewById(R.id.lvTasks);
        taskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
        registerContextMenu();
        showTaskList();
        createButtonListeners();
        getCreationData();
        return view;
    }

    /**
     * This makes it possible for the activity to take the data
     *
     * @param context that implements the interface
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try { //If interface is implemented
            tCallBack = (OnTaskClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    /**
     * Had to had this because the context overload is so
     * new that it didin't call on my older API version
     *
     * @param activity Activity that implements the interface
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try { //If interface is implemented
                tCallBack = (OnTaskClickedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString());
            }
        }
    }


    @Override
    public void onDetach() {
        tCallBack = null;
        super.onDetach();
    }

    /**
     * If any data has been passed from CreationActivity
     * Check if the data is valid to create a new task!
     */
    public void getCreationData() {
        //FIXME: Doesn't say null even if there is no data at all!
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            createTask(extras.getInt(CreationActivity.EXTRA_PRIORITY),
                    extras.getString(CreationActivity.EXTRA_TITLE),
                    extras.getString(CreationActivity.EXTRA_CONTENT));
        }
    }


    /**
     * Takes our context xml and fills it with inflater
     * Shows the popup
     *
     * @param menu     Our context menu
     * @param v        the view component e.g. we clicked on it
     * @param menuInfo gives some additional info about the component
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getActivity().getMenuInflater();
        mInflater.inflate(R.menu.contextual_menu, menu);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.delete) {
            adapter.remove(adapter.get(info.position));
            return true;
        }
        return super.onContextItemSelected(item);
    }


    //When pressed long show contextMenu
    public void registerContextMenu() {
        registerForContextMenu(taskList);
    }

    /**
     * When the activity starts,
     * initialize the listview of tasks
     */
    public void showTaskList() {
        adapter = new TaskAdapter();
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tCallBack.onTaskClick();
            }
        });
    }

    /**
     * When user click's the new task button
     */
    public void createButtonListeners() {
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreationActivity();
            }
        });
    }

    public void openCreationActivity() {
        ((MainActivity) getActivity()).openCreationActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    /**
     * Passed from the TaskCreationFragment
     *
     * @param priority priority that the user chose
     * @param title    title that the user chose
     * @param content  content that the user chose
     */
    public void createTask(int priority, String title, String content) {
        //FIXME: Make better validation
        if (title.length() < 10 && content.length() < 20) {
            Task task = new NonTimedTask(title, content, priority);
            adapter.add(task);
        }
    }
}
