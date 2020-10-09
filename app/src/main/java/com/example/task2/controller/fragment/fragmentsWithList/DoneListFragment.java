package com.example.task2.controller.fragment.fragmentsWithList;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.controller.RecyclerViewComponent.TaskAdapter;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;

import java.util.List;

public class DoneListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskDBRepository mTaskDBRepository;
    private TaskAdapter mTaskAdapter;
    private ImageView mImageView;

    public TaskAdapter getTaskAdapter() {
        return mTaskAdapter;
    }

    public DoneListFragment() {
        // Required empty public constructor
    }


    public static DoneListFragment newInstance() {
        DoneListFragment fragment = new DoneListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository=TaskDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_done_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mImageView=view.findViewById(R.id.img_done_empty_list);
        mRecyclerView=view.findViewById(R.id.done_recyclerView);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       updateUI();
    }
    private void updateUI() {
        List<TaskEntity> tasks = mTaskDBRepository.getTasksForState(State.Done);
        mImageView.setVisibility(tasks.size()==0?View.VISIBLE:View.INVISIBLE);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks) {
                @Override
                public FragmentManager getFragmentManagerForAdapter() {
                    return getActivity().getSupportFragmentManager();
                }

                @Override
                public Fragment getFragmentForAdapter() {
                    return DoneListFragment.this;
                }
            };
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    public void notifyDataSetChangeDone(){
        mTaskAdapter.notifyDataSetChanged();
    }


}