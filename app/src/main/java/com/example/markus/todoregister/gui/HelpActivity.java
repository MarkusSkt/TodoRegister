package com.example.markus.todoregister.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.markus.todoregister.R;
import com.example.markus.todoregister.data.Help;

/**
 * Created by Markus on 20.4.2017.
 * Activity that shows the Help menu to the user
 * Help menu shows some information about the application
 */

public class HelpActivity extends AppCompatActivity {


    /**
     * On the activity launch
     * register all the views and adapter
     * @param savedInstanceState state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_menu_layout);
        setActionBar();
        setHelp();
    }


    /**
     * Set the actionbar for the activity
     */
    public void setActionBar() {
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    /**
     * Set the help texts to show in the activity
     */
    private void setHelp() {
        String title = "TODO Version 0.1";
        String content = "Simple application for remembering tasks you are supposed to do! \n" +
                "1) Create a task and give it a priority. \n" +
                "2) Finish a task when it is done.";
        Help help = new Help(title, content);
        TextView titleText = (TextView) findViewById(R.id.helpTitle);
        titleText.setText(help.getTitle());
        TextView contentText = (TextView) findViewById(R.id.helpContent);
        contentText.setText(help.getContent());
    }
}
