package com.example.markus.todoregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowTaskActivity extends AppCompatActivity {

    public static final String EXTRA_ID_FINISH = "com.example.markus.todoregister.EXTRA_ID_FINISH";
    public static final String EXTRA_ID_DELETE = "com.example.markus.todoregister.EXTRA_ID_DELETE";
    public static boolean showed = false;

    private TextView title, content;
    private Button returnB, finishB, deleteB;
    private int ID; //ID OF THE TASK WE JUST OPENED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        registerComponents();
        setButtonListeners();
        showTask();
    }

    //Register all the components of this activity
    private void registerComponents() {
        title = (TextView) findViewById(R.id.titleText);
        content = (TextView) findViewById(R.id.contentText);
        returnB = (Button) findViewById(R.id.returnButton);
        finishB = (Button) findViewById(R.id.finishButton);
        deleteB = (Button) findViewById(R.id.deleteButton);
    }

    private void setButtonListeners() {
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        finishB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityFinish(ID);
            }
        });
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityDelete(ID);
            }
        });
    }

    /**
     * Open the main when
     * user clicks "return" Button
     */
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            title.setText( extras.getString(MainActivity.TITLE_EXTRA));
            content.setText(extras.getString(MainActivity.CONTENT_EXTRA));
            ID = extras.getInt(MainActivity.ID_EXTRA);
            Log.e("ID OF THE TASK SHOWING", Integer.toString(ID));
        }
    }

}
