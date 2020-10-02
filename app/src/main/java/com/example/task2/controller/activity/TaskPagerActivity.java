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

import com.example.task2.R;
import com.example.task2.controller.fragment.DoingListFragment;
import com.example.task2.controller.fragment.DoneListFragment;
import com.example.task2.controller.fragment.ToDoListFragment;
import com.example.task2.model.State;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;

    //don't use of single fragment activity cuz use of activity not fragment for building view pager
    //***************************************************************

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,TaskPagerActivity.class);
        return intent;
    }
    //***************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_pager);
        findViews();
        initViews();
    }
    //***************************************************************
    private void initViews() {
        FixedTabsPagerAdapter adapter=new FixedTabsPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
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
    }
    //***************************************************************

    private void findViews() {
        mTabLayout=findViewById(R.id.tab_layout);
        mViewPager2=findViewById(R.id.view_pager);
    }

    //**************************pagerAdapter*************************
    private class FixedTabsPagerAdapter extends FragmentStateAdapter{

        public FixedTabsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:

                return new ToDoListFragment();

                case 1:

                return new DoingListFragment();
                case 2:
                    return new DoneListFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }


    }
}