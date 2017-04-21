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
 * Viewpager has 2 pages - tasks & finished tasks
 * Handles changing to CreationActivity
 * Handles changing to ShowTaskActivity
 * Handles the Pager/TabLayout/toolbar views
 * <p>
 * FIXME:SHOULD HAVE SEPERATED THE VIEWPAGER/TABLAYOUT/TOOLBAR
 * FIXME:FROM THE MAIN ACTIVITY SO I COULD HAVE MADE CREATIONACTIVITY
 * FIXME:AND SHOWTASKACTIVITY AS FRAGMENTS!
 * <p>
 * FIXME:Also it would be better to add all the "finish", "create", "delete"
 * FIXME:buttons to the action bar on this kind of application because now the user has to deactivate
 * FIXME:the keyboard before he can press those("create...") buttons!
 */
public class MainActivity extends AppCompatActivity implements ActiveTaskFragment.OnTaskClickedListener {


    public static final String TITLE_EXTRA = "com.example.markus.todoregister.EXTRA_TITLE";
    public static final String CONTENT_EXTRA = "com.example.markus.todoregister.EXTRA_CONTENT";
    public static final String ID_EXTRA = "com.example.markus.todoregister.EXTRA_ID";

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    private ViewPagerAdapter pagerAdapter;
    ActiveTaskFragment activeTaskFragment;

    /**
     * On the activity launch
     * register all the views and adapter
     *
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

    /* Takes our context xml and fills it with inflater
* Shows the popup
* @param menu     Our context menu
* @param v        the view component e.g. we clicked on it
* @param menuInfo gives some additional info about the component
*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
         MenuInflater mInflater = getMenuInflater();
        switch (v.getId()) {
            case R.id.lvTasks:
                 mInflater.inflate(R.menu.contextual_menu, menu);
                break;
            case R.id.finishedTasksList:
                 mInflater.inflate(R.menu.contextual_menu_finished, menu);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.helpButton) {
            openHelpActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set custom action bar where the text
     * is on the middle
     */
    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_layout, null),
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
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    /**
     * Initialize the page adapter to show the fragments
     */
    private void registerPagerAdapter() {
        final String activeTasksFragment = "Active Tasks";
        final String finishedTasksFragment = "Finished Tasks";
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        activeTaskFragment = new ActiveTaskFragment();
        pagerAdapter.addFragments(activeTaskFragment, activeTasksFragment);
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
}





