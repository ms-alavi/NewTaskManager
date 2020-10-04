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
import com.example.task2.model.State;
import com.example.task2.model.Task;

import java.util.Date;
import java.util.List;

import com.example.task2.repository.TaskDBRepository;

public class DoingListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private TaskAdapter mTaskAdapter;
    private TaskDBRepository mTaskDBRepository;
    private List<Task> tasks;

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
        Task task=new Task();
        task.setTitle("test");
        task.setState(State.Doing);
        task.setDescription("Test list");
        task.setDate(new Date());
        mTaskDBRepository.insertTask(task);
        List<Task> tasks = mTaskDBRepository.getTasksForState(State.Doing);

        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
        if (tasks.size()==0)mImageView.setVisibility(View.VISIBLE);
        else mImageView.setVisibility(View.GONE);

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
    public class TaskAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter< TaskHolder>{
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
        public  TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(getActivity()).inflate(R.layout.row_of_list,parent,
                    false);
            return new  TaskHolder(itemView);
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