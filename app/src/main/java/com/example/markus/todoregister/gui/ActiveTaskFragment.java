package com.example.markus.todoregister.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.markus.todoregister.R;
import com.example.markus.todoregister.data.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Markus on 10.4.2017.
 * Part of the MainActivity
 * Handles all the logic behind the task fragment
 * Handles communication between the TaskAdapter(listview)
 * Handles the contextMenu -- THIS TO MAIN?
 * Handles communication to Tasks
 */

public class ActiveTaskFragment extends PageFragment {

    private ListView listView;
    private ImageButton taskButton;
    private Map<String, Command> commandMap;

    private OnClickedListener tCallBack;

    interface OnClickedListener {
        void onTaskClick(String title, String content, int id);

        void onCreateClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_layout, container, false);
        registerComponents(view);
        registerForContextMenu(listView);
        setAdapter();
        createButtonListeners();
        getPassedData();
        final int notFinished = 0;
        fillListView(notFinished);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCommands();
    }

    /**
     * Create the commands to execute
     */
    public void createCommands() {
        commandMap = new HashMap<>();
        commandMap.put("finish", new Command() {
            @Override
            public void execute(int id, String title, String content) {
                finishTask(id, title, content);
            }
        });
        commandMap.put("delete", new Command() {
            @Override
            public void execute(int id, String title, String content) {
                delete(id);
            }
        });
        commandMap.put("update", new Command() {
            @Override
            public void execute(int id, String title, String content) {
                updateTask(id, title, content);
            }
        });
    }


    /**
     * Register buttons/lists..
     *
     * @param view view
     */
    private void registerComponents(View view) {
        setListView(view);
        taskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
    }


    /**
     * Check that interface is implemented before calling
     *
     * @param context that implements the interface
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try { //If interface is implemented
            tCallBack = (OnClickedListener) context;
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
                tCallBack = (OnClickedListener) activity;
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
        getCreationData();
        getUpdateData();
    }


    /**
     * Get the data that would be passed
     * from CreationActivity
     */
    private void getCreationData() {
        if (CreationActivity.create) {
            Bundle extras = getActivity().getIntent().getExtras();
            createTask(extras.getInt(CreationActivity.EXTRA_PRIORITY),
                    extras.getString(CreationActivity.EXTRA_TITLE),
                    extras.getString(CreationActivity.EXTRA_CONTENT));
            CreationActivity.create = false;
        }
    }


    /**
     * Get the data that would be passed
     * from ShowTaskActivity
     */
    private void getUpdateData() {
        if (ShowTaskActivity.showed) {
            int id = getActivity().getIntent().getIntExtra(ShowTaskActivity.EXTRA_ID, -1);
            Bundle extras = getActivity().getIntent().getExtras();
            String cmd = extras.getString(ShowTaskActivity.EXTRA_COMMAND);
            commandMap.get(cmd).execute(id, extras.getString(ShowTaskActivity.EXTRA_TITLE), extras.getString(ShowTaskActivity.EXTRA_CONTENT));
            ShowTaskActivity.showed = false;
        }
    }


    //Finish task
    private void finishTask(int extra, String title, String content) {
        getAdapter().finish(getContext(), extra, title, content);
    }


    /**
     * Tell the adapter to update our task
     *
     * @param id      id of the task we are updating
     * @param title   new title
     * @param content new content
     */
    private void updateTask(int id, String title, String content) {
        getAdapter().updateTask(getContext(), id, title, content);
    }


    @Override
    protected void setListView(View view) {
        listView = (ListView) view.findViewById(R.id.lvTasks);
    }


    /**
     * When the activity starts,
     * initialize the listview of tasks and set listener
     * of when one row is being clicked to open ShowTaskActivity
     */
    public void setAdapter() {
        listView.setAdapter(getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tCallBack.onTaskClick(
                        getAdapter().find(position).getTitle(),
                        getAdapter().find(position).getContent(),
                        getAdapter().find(position).getID());
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
                tCallBack.onCreateClick();
            }
        });
    }


    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }


    /**
     * Passed from the TaskCreationFragment
     * Check if the data was correct so the task could be created
     * @param priority priority that the user chose
     * @param title    title that the user chose
     * @param content  content that the user chose
     */
    public void createTask(int priority, String title, String content) {
        //FIXME: Make a better validation(This is temporary)
        if (title.length() < 17 && content.length() < 1000) {
            getAdapter().newTask(getContext(), title, content, priority);
            return;
        }
        Toast.makeText(getContext(), "Invalid Creation Input", Toast.LENGTH_LONG).show();
    }
}
