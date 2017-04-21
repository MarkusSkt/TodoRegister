package com.example.markus.todoregister.GUI;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.markus.todoregister.R;

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


    /**
     * Register buttons/lists..
     * @param view view
     */
    private void registerComponents(View view) {
        setListView(view);
        taskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
    }

    /**
     * Check that interface is implemented before calling
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
            Log.e("ID OF THE FINISHED TASK", Integer.toString(extra));
            Log.e("ID OF THE DELETED TASK", Integer.toString(extra1));
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
        getAdapter().finish(getContext(), extra);
    }


    @Override
    protected void setListView(View view) {
        listView = (ListView) view.findViewById(R.id.lvTasks);
    }

    /**
     * When the activity starts,
     * initialize the listview of tasks
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
     * @param priority priority that the user chose
     * @param title    title that the user chose
     * @param content  content that the user chose
     */
    public void createTask(int priority, String title, String content) {
        //FIXME: Make a better validation
        if (title.length() < 10 && content.length() < 1000) {
            getAdapter().newTask(getContext(), title, content, priority);
        }
    }
}
