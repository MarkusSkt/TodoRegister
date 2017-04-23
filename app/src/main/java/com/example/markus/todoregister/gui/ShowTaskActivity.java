package com.example.markus.todoregister.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.markus.todoregister.R;
import com.example.markus.todoregister.data.MenuCommand;

import java.util.HashMap;
import java.util.Map;


/**
 * Activity for showing content of a specific task
 * Also handles calls for updating/deleting/finishing a task
 */
public class ShowTaskActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.markus.todoregister.EXTRA_ID";
    public static final String EXTRA_COMMAND = "com.example.markus.todoregister.EXTRA_COMMAND";
    public static final String EXTRA_TITLE = "com.example.markus.todoregister.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.markus.todoregister.EXTRA_CONTENT";
    private final String[] extraCommands = {"finish", "delete", "update"};

    private Map<Integer, MenuCommand> commandMap = new HashMap<>();
    public static boolean showed = false; //badbad
    private EditText title, content;
    private int ID;  //Id of the task we just opened


    //When activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        registerComponents();
        setActionBar();
        showTask();
        createCommands();
    }


    /**
     * Create menu commands for the activity
     */
    public void createCommands() {
        commandMap.put(R.id.finishButton, new MenuCommand() {
            @Override
            public boolean execute() {
                openMainActivity(ID, extraCommands[0]);
                return true;
            }
        });
        commandMap.put(android.R.id.home, new MenuCommand() {
            @Override
            public boolean execute() {
                if(getWindow().getCurrentFocus() != findViewById(R.id.focusedLayout)) {
                    createConfirmationDialog("Confirmation", "Do you want to save the changes?");
                    return true;
                }
                openMainActivity();
                return true;
            }

        });
        commandMap.put(R.id.deleteButton, new MenuCommand() {
            @Override
            public boolean execute() {
                openMainActivity(ID, extraCommands[1]);
                return true;
            }
        });
    }



    /**
     * Set the actionbar for the activity
     */
    private void setActionBar() {
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_task_menu, menu);
        return true;
    }


    /**
     * Check what the pressed menu item is and act accordingly
     * @param item menu item that was pressed on
     * @return if we clicked on something
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (commandMap.get(item.getItemId()).execute())
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //Register all the components(Views) of this activity
    private void registerComponents() {
        title = (EditText) findViewById(R.id.titleText);
        content = (EditText) findViewById(R.id.contentText);
    }


    /**
     * Check if user click's on back button
     * while being on ShowTaskActivity
     * @param keyCode back button
     * @param event   event
     * @return if key pressed
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Fire the wanted event when the button is released
     * @param keyCode back button
     * @param event   event
     * @return if key was released
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled() && getWindow().getCurrentFocus() != findViewById(R.id.focusedLayout)) {
            createConfirmationDialog("Confirmation", "Do you want to save the changes?");
            return true;
        }
        else {
            openMainActivity();
        }
        return super.onKeyUp(keyCode, event);
    }


    /**
     * Create a basic confirmation dialog and create the button listeners
     */
    public void createConfirmationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowTaskActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity(ID, extraCommands[2]);
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Open main activity and pass a specific command
     * @param id  id of the task we were watching
     * @param cmd command for main activity(ActiveTaskFragment)
     */
    public void openMainActivity(int id, String cmd) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        intent.putExtra(EXTRA_ID, id);
        extras.putString(EXTRA_CONTENT, content.getText().toString());
        extras.putString(EXTRA_TITLE, title.getText().toString());
        extras.putString(EXTRA_COMMAND, cmd);
        intent.putExtras(extras);
        showed = true;
        startActivity(intent);
    }


    /**
     * Open the main activity without passing any data through
     */
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * Get the data given from MainActivity and show
     * the task title and content we just clicked on
     */
    private void showTask() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString(MainActivity.TITLE_EXTRA));
            content.setText(extras.getString(MainActivity.CONTENT_EXTRA));
            ID = extras.getInt(MainActivity.ID_EXTRA);
        }
    }
}
