package com.example.markus.todoregister;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;

import static android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM;

/**
 * Main activity with viewPager
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    private ViewPagerAdapter pagerAdapter;

    /**
     * When the activity is ran
     *
     * @param savedInstanceState don't know yet
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerComponents();
        registerPagerAdapter();
        setCustomActionBar();

    }

    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
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

    //Set the components used and register our ActionBar
    private void registerComponents() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tablayout = (TabLayout) findViewById(R.id.tabLayout);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    //Initialize our pagerAdapter
    private void registerPagerAdapter() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new TaskFragment(), "Tasks");
        pagerAdapter.addFragments(new FinishedTasksFragment(), "Finished Tasks");
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    /**
     * Open the creation activity when
     * user cliks "+" Button
     */
    public void openCreationActivity() {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    /**
     * If user created a new Task, get the data
     * from CreationActivity
     */
    //  public void getCreationData() {
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            ((TaskFragment)pagerAdapter.getItem(0)).createTask(extras.getInt(CreationActivity.EXTRA_PRIORITY),
//                    extras.getString(CreationActivity.EXTRA_TITLE),
//                    extras.getString(CreationActivity.EXTRA_CONTENT));
//        }
//    }
//    public void initializeTaskFragment() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        TaskFragment taskFragment = new TaskFragment();
//        transaction.add(R.id.fragmentContainer, taskFragment);
//        transaction.commit();
//    }

//    public void changeToTaskFragment(int priority, String title, String content) {
//
////        FragmentManager manager = getSupportFragmentManager();
////        FragmentTransaction transaction = manager.beginTransaction();
////        TaskFragment taskFragment = new TaskFragment();
////        taskFragment.createTask(priority, title, content);
////        transaction.replace(R.id.fragmentContainer, taskFragment);
////        transaction.commit();
//    }


//    @Override
//    public void dataChanged(int priority, String title, String content) {
//        changeToTaskFragment(priority, title, content);
//        TaskFragment tf = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment);
//        tf.createTask(priority, title, content);
//        initializeTaskFragment();
//
//    }
}





