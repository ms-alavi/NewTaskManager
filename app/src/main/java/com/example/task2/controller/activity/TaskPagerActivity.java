package com.example.task2.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.task2.R;
import com.example.task2.controller.fragment.DoingFragment;
import com.example.task2.controller.fragment.DoneFragment;
import com.example.task2.controller.fragment.ToDoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;

    //don't use of single fragment activity cuz use of activity not fragment for building view pager

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,TaskPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_pager);
        findViews();
        initViews();
    }

    private void initViews() {
        FixedTabsPagerAdapter adapter=new FixedTabsPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:
                                tab.setText(R.string.doing);
                                break;
                            case 1:
                                tab.setText(R.string.done);
                                break;
                            case 2:
                                tab.setText(R.string.todo);
                                break;

                        }
                    }
                }).attach();
    }

    private void findViews() {
        mTabLayout=findViewById(R.id.tab_layout);
        mViewPager2=findViewById(R.id.view_pager);
    }

    //pagerAdapter
    private class FixedTabsPagerAdapter extends FragmentStateAdapter{

        public FixedTabsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new DoingFragment();
                case 1:
                    return new DoneFragment();
                case 2:
                    return new ToDoFragment();
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