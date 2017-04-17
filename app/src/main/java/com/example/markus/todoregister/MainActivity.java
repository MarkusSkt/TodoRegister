package com.example.markus.todoregister;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import static android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM;

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
public class MainActivity extends AppCompatActivity implements TaskFragment.OnTaskClickedListener {

    private final String ACTIVE_TASKS_FRAGMENT = "Tasks"; //CHANGE NAME
    private final String FINISHED_TASKS_FRAGMENT = "Finished Tasks"; //CHANGE NAME
    public static final String TITLE_EXTRA = "com.example.markus.todoregister.EXTRA_TITLE";
    public static final String CONTENT_EXTRA = "com.example.markus.todoregister.EXTRA_CONTENT";
    public static final String ID_EXTRA = "com.example.markus.todoregister.EXTRA_ID";

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    private ViewPagerAdapter pagerAdapter;
    TaskFragment taskFragment;

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
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        taskFragment = new TaskFragment();
        pagerAdapter.addFragments(taskFragment, ACTIVE_TASKS_FRAGMENT);
        pagerAdapter.addFragments(new FinishedTasksFragment(), FINISHED_TASKS_FRAGMENT);
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
     * When this is called from the TaskFragment, change
     * the current activity to show the task data
     */
    @Override
    public void onTaskClick(String content, String title, int id) {
        openShowTaskActivity(content, title, id);
    }
}





