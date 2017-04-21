package com.example.markus.todoregister.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.markus.todoregister.R;

/**
 * Created by Markus on 13.4.2017.
 * Activity for creating a new task
 */

public class CreationActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.markus.todoregister.EXTRA_T";
    public static final String EXTRA_CONTENT = "com.example.markus.todoregister.EXTRA_C";
    public static final String EXTRA_PRIORITY = "com.example.markus.todoregister.EXTRA_P";
    public static boolean create = false; //BAD?

    private EditText title, content;
    private int priority = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creation);
        registerComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creation_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPopupBackgroundResource(R.color.actionBarColor);
        spinner.setAdapter(adapter);
        getSupportActionBar().setTitle("");
        setSpinnerListener(spinner);

     return true;
    }

    /**
     * Set listener for the priority spinner
     * @param spinner spinner on the actionbar
     */
    public void setSpinnerListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPriority(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setPriority(0);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.finishCreation) {
            createTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Find all the views used in this activity
    private void registerComponents() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        content.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(content, InputMethodManager.SHOW_IMPLICIT);
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

}
