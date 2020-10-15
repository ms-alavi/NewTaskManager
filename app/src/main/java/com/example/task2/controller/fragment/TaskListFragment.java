package com.example.task2.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.task2.R;


import com.example.task2.controller.State;
import com.example.task2.model.Task;

import java.util.List;

import com.example.task2.repository.TaskDBRepository;

public class TaskListFragment extends Fragment {
    public static final String ARGS_TASK_LIST_FRAGMENT = "argsTaskListFragment";
    public static final String TLF = "TLF";
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private TaskAdapter mTaskAdapter;
    private TaskDBRepository mTaskDBRepository;
    private State mState;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(State state) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TASK_LIST_FRAGMENT,state.name() );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        String string=getArguments().getString(ARGS_TASK_LIST_FRAGMENT);
       mState = State.valueOf(string);

        Log.d(TLF,string+"____________");
        Log.d(TLF,"____________");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TLF,"____________");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
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
        mImageView = view.findViewById(R.id.img_doing_empty_list);
        mRecyclerView = view.findViewById(R.id.task_recyclerView);
    }

    public void updateUI() {

        List<Task> tasks = mTaskDBRepository.getTasksForState(mState);
        mImageView.setVisibility(tasks.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
    }


    public  class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
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
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_of_list,parent,
                    false);
            return new TaskHolder(itemView) ;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task=mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
    public class TaskHolder extends RecyclerView.ViewHolder {
        public static final String TAG_DETAIL_DIALOG_FRAGMENT = "tagDetailDialogFragment";
        public static final int REQUEST_CODE_TASK_DETAIL = 0;
        private TextView mIcon, mTitleText, mDateText, mTimeText;
        private Task mTask;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mDateText = itemView.findViewById(R.id.txt_date);
            mTimeText = itemView.findViewById(R.id.txt_time);
            mTitleText = itemView.findViewById(R.id.txt_task_title);
            mIcon = itemView.findViewById(R.id.txt_circle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailDialogFragment taskDetailDialogFragment=
                            TaskDetailDialogFragment.newInstance(mTask.getId());
                    taskDetailDialogFragment.setTargetFragment(TaskListFragment.this
                            , REQUEST_CODE_TASK_DETAIL);
                    taskDetailDialogFragment.show(getActivity().getSupportFragmentManager()
                            , TAG_DETAIL_DIALOG_FRAGMENT);
                }
            });
        }

        public void bindTask(Task task) {
            mTask=task;
            mDateText.setText(task.getDate());
            mTimeText.setText(task.getTime());
            mIcon.setText(String.valueOf(task.getTitle().charAt(0)));
            mTitleText.setText(task.getTitle());

        }
    }

}