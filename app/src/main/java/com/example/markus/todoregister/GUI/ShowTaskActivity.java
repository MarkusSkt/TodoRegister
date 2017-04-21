package com.example.markus.todoregister.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.markus.todoregister.R;


public class ShowTaskActivity extends AppCompatActivity {

    public static final String EXTRA_ID_FINISH = "com.example.markus.todoregister.EXTRA_ID_FINISH";
    public static final String EXTRA_ID_DELETE = "com.example.markus.todoregister.EXTRA_ID_DELETE";
    public static boolean showed = false;

    private TextView title, content;
    private int ID; //ID OF THE TASK WE JUST OPENED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        registerComponents();
        getSupportActionBar().setTitle("");
        showTask();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.finishButton) {
            openMainActivityFinish(ID);
            return true;
        }
        else if(id == R.id.deleteButton) {
            openMainActivityDelete(ID);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //Register all the components of this activity
    private void registerComponents() {
        title = (TextView) findViewById(R.id.titleText);
        content = (TextView) findViewById(R.id.contentText);
    }

    /**
     * Open the main when
     * user clicks "delete or finish" Button
     * Send the id of the task we want to delete/finish
     */
    public void openMainActivityFinish(int id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_ID_FINISH, id);
        showed = true;
        startActivity(intent);
    }

    /**
     * Open the main when
     * user clicks "delete or finish" Button
     * Send the id of the task we want to delete/finish
     */
    public void openMainActivityDelete(int id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_ID_DELETE, id);
        showed = true;
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
            Log.e("ID OF THE TASK SHOWING", Integer.toString(ID));
        }
    }

}
