package com.example.task2.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.task2.R;
import com.example.task2.controller.State;
import com.example.task2.controller.fragment.TaskAddDialogFragment;
import com.example.task2.controller.fragment.TaskListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    public static final String TAG_ADD_TASK_DIALOG_FRAGMENT = "TagAddTaskDialogFragment";
    public static final int REQUEST_CODE_TASK_LIST = 0;
    public static final String EXTRA_USER_CREATOR_ID = "extraUserCreatorId";

    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;
    private TaskListFragment mTaskListFragment;
    public static final String TPA = "TPA";
    private State[] mTaskList;
    private Long mUserCreatorId;
    //don't use of single fragment activity cuz use of activity not fragment for building view pager
    //***************************************************************

    public static Intent newIntent(Context context, Long userCreatorId) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_CREATOR_ID,userCreatorId);
        return intent;
    }
    //***************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_pager_activity);
        findViews();
        mTaskList= new State[]{State.Todo, State.Doing, State.Done};
        init();
        Log.d(TPA, "************************");
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskAddDialogFragment addTaskDialogFragment = TaskAddDialogFragment.newInstance
                        (mUserCreatorId);
                addTaskDialogFragment.setTargetFragment(mTaskListFragment,REQUEST_CODE_TASK_LIST);
                addTaskDialogFragment.show
                        (getSupportFragmentManager(), TAG_ADD_TASK_DIALOG_FRAGMENT);
            }
        });
        Bundle bundle = new Bundle();


    }

    //***************************************************************
    private void init() {
        FixedTabsPagerAdapter adapter = new FixedTabsPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText(State.Todo.name());
                                break;
                            case 1:
                                tab.setText(State.Doing.name());
                                break;
                            case 2:
                                tab.setText(State.Done.name());
                                break;

                        }
                    }
                }).attach();
        mUserCreatorId=getIntent().getLongExtra(EXTRA_USER_CREATOR_ID,0);
    }
    //***************************************************************

    private void findViews() {
        mFloatingActionButton = findViewById(R.id.fab);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager);
    }

    //**************************pagerAdapter*************************
    private class FixedTabsPagerAdapter extends FragmentStateAdapter {



        public FixedTabsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            State state=mTaskList[position];
            Log.d(TPA,state.name() + "************************");
            TaskListFragment taskListFragment=TaskListFragment.newInstance(state,mUserCreatorId
            );
            return taskListFragment;

            }


        @Override
        public int getItemCount() {
            return mTaskList.length;
        }


    }
}