package com.example.markus.todoregister;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by Markus on 11.4.2017.4
 * Fragment for the taskCreation
 */
public class TaskCreationFragment extends Fragment {


//    public interface OnGetTaskAttrListener {
//        public void dataChanged(int priority, String title, String content);
//    }
//
//    private EditText title;
//    private EditText content;
//    private RadioGroup priorityGroup;
//    private int priority = 0;
//
//    private OnGetTaskAttrListener tCallBack;
//    private Button createButton;
//    private Button cancelButton;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.task_creation, container, false);
//        registerComponents(view);
//        createRadioGroupListener();
//        createButtonListeners();
//        return view;
//    }
//
//    //This makes it possible for the activity to take the data
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try { //If interface is implemented
//            tCallBack = (OnGetTaskAttrListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString());
//        }
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            try { //If interface is implemented
//                tCallBack = (OnGetTaskAttrListener) activity;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(activity.toString());
//            }
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        tCallBack = null;
//        super.onDetach();
//    }
//
//    //Register all the used views in this fragment
//    public void registerComponents(View view) {
//        title = (EditText) view.findViewById(R.id.title);
//        content = (EditText) view.findViewById(R.id.content);
//        priorityGroup = (RadioGroup) view.findViewById(R.id.priorityChooser);
//        createButton = (Button) view.findViewById(R.id.createButton);
//        cancelButton = (Button) view.findViewById(R.id.backButton);
//    }
//
//    /**
//     * When user is selecting the priority
//     */
//    public void createRadioGroupListener() {
//        priorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.priority0:
//                        setPriority(0);
//                        break;
//                    case R.id.priority1:
//                        setPriority(1);
//                        break;
//                    case R.id.priority2:
//                        setPriority(2);
//                        break;
//                    default:
//                        setPriority(0);
//                        break;
//                }
//            }
//        });
//    }
//
//    //Create listeners for "Create" and "Return" buttons
//    public void createButtonListeners() {
//        createButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleCreateTask(v);
//            }
//        });
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleCancel(v);
//            }
//        });
//    }
//
//    /**
//     * When user clicks on "Return"
//     *
//     * @param view we are clicking on
//     */
//    public void handleCancel(View view) {
//        cancel();
//    }
//
//    /**
//     * When user clicks on "Create"
//     *
//     * @param view we are clicking on
//     */
//    public void handleCreateTask(View view) {
//        createTask();
//    }
//
//    //set the priority according to the radio button that is actives
//    public int setPriority(int p) {
//        return priority = p;
//    }
//
//    public int getPriority() {
//        return priority;
//    }
//
//    //Get the user title input
//    public String getTitle() {
//        return title.getText().toString();
//    }
//
//    //Get the user content input
//    public String getContent() {
//        return content.getText().toString();
//    }
//
//    //Creates a new Task and returns to TaskFragment
//    public void createTask() {
//        String priority = "" + getPriority();
//        Log.d("Title", getTitle());
//        Log.d("Content", getContent());
//        Log.d("Priority", priority);
//        if(tCallBack != null) {
//            tCallBack.dataChanged(getPriority(), getTitle(), getContent());
//        }
//        //openTaskFragment();
//    }
//
//    //Changes the fragment back to TaskFragment
//    public void cancel() {
//        openTaskFragment();
//    }
//
//    //Change the fragment to TaskFragment
//    public void openTaskFragment() {
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        TaskFragment taskFragment = new TaskFragment();
//        transaction.replace(R.id.fragmentContainer, taskFragment);
//        transaction.commit();
//    }
}
