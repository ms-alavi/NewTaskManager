package com.example.task2.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.task2.R;
import com.example.task2.controller.RecyclerViewComponent.TaskAdapter;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;

import java.util.List;

public class ToDoListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskDBRepository mTaskDBRepository;
    private TaskAdapter mTaskAdapter;
    private ImageView mImageView;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance() {
        ToDoListFragment fragment = new ToDoListFragment();
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

        View view= inflater.inflate(R.layout.fragment_to_do_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initViews();
        return view;
    }
    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();


    }

    private void findViews(View view) {
        mImageView=view.findViewById(R.id.img_to_do_empty_list);
        mRecyclerView = view.findViewById(R.id.to_do_recyclerView);
    }
    private void updateUI() {

        List<TaskEntity> tasks = mTaskDBRepository.getTasksForState(State.Todo);
        mImageView.setVisibility(tasks.size()==0?View.VISIBLE:View.INVISIBLE);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
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

}