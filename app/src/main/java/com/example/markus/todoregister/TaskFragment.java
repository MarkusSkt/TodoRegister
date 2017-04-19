package com.example.markus.todoregister;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

import com.example.markus.todoregister.data.NonTimedTask;
import com.example.markus.todoregister.data.Task;
import com.example.markus.todoregister.data.Tasks;
import com.example.markus.todoregister.data.TimedTask;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Markus on 10.4.2017.
 * Part of the MainActivity
 * Handles all the logic behind the task fragment
 * Handles communication between the TaskAdapter(listview)
 * Handles the contextMenu -- THIS TO MAIN?
 * Handles communication to Tasks
 */

public class TaskFragment extends Fragment {
    private ListView taskList;
    private ImageButton taskButton;
    private TaskAdapter adapter;


    private OnTaskClickedListener tCallBack;

    public interface OnTaskClickedListener {
        void onTaskClick(String title, String content, int id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_layout, container, false);
        registerComponents(view);
        registerContextMenu();
        setAdapter();
        createButtonListeners();
        getPassedData();
        readActive();
        return view;
    }


    /**
     * Get all the active tasks
     * and show the in the view
     */
    private void readActive() {
        adapter.readActive(getContext());
    }


    private void registerComponents(View view) {
        taskList = (ListView) view.findViewById(R.id.lvTasks);
        taskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
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
    public void getPassedData() {
        //FIXME: Could I know easier if the data has been passed than using static booleans?
        //FIXME: Get rid of nested if's
        if (CreationActivity.create) {
            Bundle extras = getActivity().getIntent().getExtras();
            createTask(extras.getInt(CreationActivity.EXTRA_PRIORITY),
                    extras.getString(CreationActivity.EXTRA_TITLE),
                    extras.getString(CreationActivity.EXTRA_CONTENT));
            CreationActivity.create = false;
        } else if (ShowTaskActivity.showed) {
            int extra = getActivity().getIntent().getIntExtra(ShowTaskActivity.EXTRA_ID_FINISH, -1);
            int extra1 = getActivity().getIntent().getIntExtra(ShowTaskActivity.EXTRA_ID_DELETE, -1);
            Log.e("ID OF THE FINISHED TASK",Integer.toString(extra));
            Log.e("ID OF THE DELETED TASK",Integer.toString(extra1));
            if (extra != -1) {
                finishTask(extra);
            } else if (extra1 != -1) {
                Log.e("SHOULD DELETE", "SHOULD DELETE LOG NOW");
                delete(extra1);
            }
            ShowTaskActivity.showed = false;
        }
    }


    //Finish task
    private void finishTask(int extra) {
        adapter.finish(getContext(), extra);
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
        if(!getUserVisibleHint())
        {
            return false;
        }
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        Log.e("ID OF THE ITEM", ""+adapter.find(info.position).getID());
        Log.e("AMOUNT OF UNFINISHED", ""+adapter.getCount());
        if (id == R.id.delete) {
            delete(adapter.find(info.position).getID());
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
    public void setAdapter() {
        adapter = new TaskAdapter();
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tCallBack.onTaskClick(adapter.find(position).getTitle(), adapter.find(position).getContent(), adapter.find(position).getID());
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
        if (title.length() < 10 && content.length() < 100) {
            adapter.newTask(getContext(), title, content, priority);
        }
    }


    /**
     * Remove task by its id
     *
     * @param id of the task
     */
    public void delete(int id) {
        adapter.delete(getContext(), id);
    }
}
