package com.example.markus.todoregister.GUI;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.markus.todoregister.R;

/**
 * Main activity with viewPager
 * Viewpager currently has 2 pages - active tasks & finished tasks
 * Handles the pages and communicating between fragments
 * and activities. Does not know about anything what
 * is going on! Just changes the activities.
 * <p>
 * FIXME:SHOULD HAVE SEPERATED THE VIEWPAGER/TABLAYOUT/TOOLBAR
 * FIXME:FROM THE MAIN ACTIVITY SO I COULD HAVE MADE CREATION ACTIVITY
 * FIXME:AND SHOWTASK ACTIVITY AS FRAGMENTS!
 * <p>
 */
public class MainActivity extends AppCompatActivity implements ActiveTaskFragment.OnClickedListener {

    public static final String TITLE_EXTRA = "com.example.markus.todoregister.EXTRA_TITLE";
    public static final String CONTENT_EXTRA = "com.example.markus.todoregister.EXTRA_CONTENT";
    public static final String ID_EXTRA = "com.example.markus.todoregister.EXTRA_ID";

    private ViewPager viewPager;
    private TabLayout tablayout;

    /**
     * On the activity launch
     * register all the views and adapter
     * @param savedInstanceState state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerComponents();
        registerPagerAdapter();
        setCustomActionBar();
    }


    /* Takes our context xml and inflates it
    * Shows the popup
    * @param menu  Our context menu
    * @param v  the view component e.g. we clicked on it
    * @param menuInfo gives some additional info about the component
    */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.contextual_menu, menu);
    }


    //Create help button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    //If user clicks on "help" - open help activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.helpButton) {
            openHelpActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set custom action bar where the title text
     * is on the middle
     */
    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(
                    getLayoutInflater().inflate(R.layout.actionbar_layout, null),
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                    )
            );
        }
    }

    /**
     * Register all the views controlled by this activity
     */
    private void registerComponents() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tablayout = (TabLayout) findViewById(R.id.tabLayout);
        setSupportActionBar((Toolbar)findViewById(R.id.toolBar));
    }

    /**
     * Initialize the page adapter to show the (page)fragments
     */
    private void registerPagerAdapter() {
        final String activeTasksFragment = "Active Tasks";
        final String finishedTasksFragment = "Finished Tasks";
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments( new ActiveTaskFragment(), activeTasksFragment);
        pagerAdapter.addFragments(new FinishedTasksFragment(), finishedTasksFragment);
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    /**
     * Open the creation activity when
     * user clicks "+" Button
     */
    public void openCreationActivity() {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    /**
     * Open help activity which contains
     * some information about the app
     */
    public void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * Open the show task activity
     * when user clicks on task
     */
    public void openShowTaskActivity(String title, String content, int id) {
        Intent intent = new Intent(this, ShowTaskActivity.class);
        Bundle extras = new Bundle();
        extras.putString(TITLE_EXTRA, title);
        extras.putString(CONTENT_EXTRA, content);
        extras.putInt(ID_EXTRA, id);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * When this is called from the ActiveTaskFragment, change
     * the current activity to show the task data
     */
    @Override
    public void onTaskClick(String content, String title, int id) {
        openShowTaskActivity(content, title, id);
    }

    //On FinishedTasksFragment, when user clicks on create
    // make a callback and change activity
    @Override
    public void onCreateClick() {
        openCreationActivity();
    }
}





