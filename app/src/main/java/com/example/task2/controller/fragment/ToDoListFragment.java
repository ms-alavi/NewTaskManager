package com.example.task2.controller.fragment;

import android.content.Context;
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
import com.example.task2.model.Task;
import com.example.task2.repository.TaskDBRepository;

import java.util.List;

public class ToDoListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskDBRepository mTaskDBRepository;
    private List<Task> tasks;
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
        tasks=mTaskDBRepository.getTasks();
        TaskAdapter taskAdapter=new TaskAdapter(tasks);
        mRecyclerView.setAdapter(taskAdapter);
        if (tasks.size()==0)mImageView.setVisibility(View.VISIBLE);



    }

    private void findViews(View view) {
        mImageView=view.findViewById(R.id.img_to_do_empty_list);
        mRecyclerView = view.findViewById(R.id.to_do_recyclerView);
    }
    public class TaskHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private TextView mIcon, mTitleText, mDateText, mTimeText;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mDateText = itemView.findViewById(R.id.txt_date);
            mTimeText = itemView.findViewById(R.id.txt_time);
            mTitleText = itemView.findViewById(R.id.txt_task_title);
            mIcon = itemView.findViewById(R.id.txt_circle);
        }

        public void bindTask(Task task) {
            mTask=task;
            mDateText.setText(task.getDate().toString());
            mTimeText.setText(String.valueOf(task.getDate().getTime()));
            mIcon.setText(task.getTitle().charAt(0));
            mTitleText.setText(task.getTitle());

        }
    }
    public class TaskAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<TaskHolder>{
        private List<Task> mTasks;


        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks ) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(getActivity()).inflate(R.layout.row_of_list,parent,
                    false);
            return new TaskHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task=new Task();
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

}