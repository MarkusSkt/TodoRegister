package com.example.markus.todoregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Created by Markus on 13.4.2017.
 * Activity for creating a new task
 */

public class CreationActivity extends Activity {

    public static final String EXTRA_TITLE = "com.example.markus.todoregister.EXTRA_T";
    public static final String EXTRA_CONTENT = "com.example.markus.todoregister.EXTRA_C";
    public static final String EXTRA_PRIORITY = "com.example.markus.todoregister.EXTRA_P";
    public static boolean create = false;

    private Button createButton, backButton;
    private EditText title, content;
    private RadioGroup priorityGroup;
    private int priority = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creation);
        registerComponents();
        createButtonListeners();
        createRadioGroupListener();
    }

    //Find all the views used in this activity
    private void registerComponents() {
        createButton = (Button) findViewById(R.id.createButton);
        backButton = (Button) findViewById(R.id.backButton);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        priorityGroup = (RadioGroup) findViewById(R.id.priorityChooser);
    }

    /**
     * When user is selecting the priority
     */
    public void createRadioGroupListener() {
        priorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.priority0:
                        setPriority(0);
                        break;
                    case R.id.priority1:
                        setPriority(1);
                        break;
                    case R.id.priority2:
                        setPriority(2);
                        break;
                    default:
                        setPriority(0);
                        break;
                }
            }
        });
    }

    // Create listeners for "Create" and "Return" buttons
    public void createButtonListeners() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateTask(v);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancel(v);
            }
        });
    }

    /**
     * When user clicks on "Return"
     *
     * @param view we are clicking on
     */
    public void handleCancel(View view) {
        cancel();
    }

    /**
     * When user clicks on "Create"
     * @param view we are clicking on
     */
    public void handleCreateTask(View view) {
        createTask();
    }


    public int getPriority() {
        return priority;
    }

    //Get the user title input
    public String getTaskTitle() {
        return title.getText().toString();
    }

    //Get the user content input
    public String getContent() {
        return content.getText().toString();
    }

    //Creates a new Task and returns to Main Activity
    public void createTask() {
        create = true;
        changeActivity(getPriority(), getTaskTitle(), getContent());
    }

    private void setPriority(int priority) {
        this.priority = priority;
    }

    //Changes the fragment back to TaskFragment
    public void cancel() {
        changeActivity();
    }

    //Change Activity while sending some data
    public void changeActivity(int priority, String title, String content) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_PRIORITY, priority);
        extras.putString(EXTRA_TITLE, title);
        extras.putString(EXTRA_CONTENT, content);
        intent.putExtras(extras);
        startActivity(intent);
    }

    //Change Activity without sending any data
    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
