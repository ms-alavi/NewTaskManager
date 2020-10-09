package com.example.task2.controller.fragment.fragmentsWithList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.task2.R;
import com.example.task2.controller.RecyclerViewComponent.TaskAdapter;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;

import java.util.List;

import com.example.task2.repository.TaskDBRepository;

public class DoingListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private TaskAdapter mTaskAdapter;
    private TaskDBRepository mTaskDBRepository;

    public DoingListFragment() {
        // Required empty public constructor
    }

    public static DoingListFragment newInstance() {
        DoingListFragment fragment = new DoingListFragment();
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
        View view = inflater.inflate(R.layout.fragment_doing_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void findViews(View view) {
        mImageView=view.findViewById(R.id.img_doing_empty_list);
        mRecyclerView = view.findViewById(R.id.doing_recyclerView);
    }
    private void updateUI() {

        List<TaskEntity> tasks = mTaskDBRepository.getTasksForState(State.Doing);
        mImageView.setVisibility(tasks.size()==0?View.VISIBLE:View.INVISIBLE);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks) {
                @Override
                public FragmentManager getFragmentManagerForAdapter() {
                    return getActivity().getSupportFragmentManager();
                }

                @Override
                public Fragment getFragmentForAdapter() {
                    return DoingListFragment.this;
                }
            };
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
    }
    public void notifyDataSetChangeDoing(){
        mTaskAdapter.notifyDataSetChanged();
    }

    public TaskAdapter getTaskAdapter() {
        return mTaskAdapter;
    }
}