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

import com.example.markus.todoregister.data.NonTimedTask;
import com.example.markus.todoregister.data.Task;

/**
 * Created by Markus on 10.4.2017.
 * //  Bundle bundle = getArguments();
 * //  String message = Integer.toString(bundle.getInt("count"));
 */

public class TaskFragment extends Fragment {
    private ListView taskList;
    private ImageButton taskButton;
    private TaskAdapter adapter;

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


    public void getCreationData() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            createTask(extras.getInt(CreationActivity.EXTRA_PRIORITY), extras.getString(CreationActivity.EXTRA_TITLE), extras.getString(CreationActivity.EXTRA_CONTENT));
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
        Log.d("id", "item id: " + id + "Delete id: " + R.id.delete);
        if (id == R.id.delete) {
            Log.d("info", "Item: " + adapter.get(info.position).toString());
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

    public void openCreationFragment() {
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        TaskCreationFragment taskCreationFragment = new TaskCreationFragment();
//        transaction.replace(R.id.fragmentContainer, taskCreationFragment);
//        transaction.commit();
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

    public TaskAdapter getAdapter() {
        return this.adapter;
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
       // if (title.length() < 10 && content.length() < 20) {
            Task task = new NonTimedTask(title, content, priority);
            adapter.add(task);
      //  }
    }

//    @Override
//    public void taskCreated(int priority, String title, String content) {
//        createTask(priority, title, content);
//
//    }
}
