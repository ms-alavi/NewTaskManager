package com.example.task2.controller.fragment;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.task2.R;


import com.example.task2.controller.State;
import com.example.task2.controller.activity.TaskPagerActivity;
import com.example.task2.model.Task;

import java.util.List;

import com.example.task2.repository.TaskDBRepository;

public class TaskListFragment extends Fragment {
    public static final String ARGS_TASK_LIST_FRAGMENT = "argsTaskListFragment";
    public static final String TLF = "TLF";
    public static final String ARGS_TASK_LIST_CREATOR_ID = "argsTaskListCreatorId";
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private TaskAdapter mTaskAdapter;
    private TaskDBRepository mTaskDBRepository;
    private State mState;
    private Long mCreatorId;
    private  SearchView mSearchView;
    private MenuItem mItem;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(State state, Long id) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putLong(ARGS_TASK_LIST_CREATOR_ID, id);
        args.putString(ARGS_TASK_LIST_FRAGMENT, state.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        String string = getArguments().getString(ARGS_TASK_LIST_FRAGMENT);
        mState = State.valueOf(string);
        mCreatorId = getArguments().getLong(ARGS_TASK_LIST_CREATOR_ID, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        setHasOptionsMenu(true);
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

        List<Task> tasks = mTaskDBRepository.getTasksForUserForState(mState, mCreatorId);
        mImageView.setVisibility(tasks.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
    }


    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.row_of_list, parent,
                    false);
            return new TaskHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
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
                    TaskDetailDialogFragment taskDetailDialogFragment =
                            TaskDetailDialogFragment.newInstance(mTask.getId());
                    taskDetailDialogFragment.setTargetFragment(TaskListFragment.this
                            , REQUEST_CODE_TASK_DETAIL);
                    taskDetailDialogFragment.show(getActivity().getSupportFragmentManager()
                            , TAG_DETAIL_DIALOG_FRAGMENT);
                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mDateText.setText(task.getDate());
            mTimeText.setText(task.getTime());
            mIcon.setText(String.valueOf(task.getTitle().charAt(0)));
            mTitleText.setText(task.getTitle());

        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cime_list, menu);
        mItem = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName=new ComponentName(getActivity(), TaskPagerActivity.class);
        mSearchView = (SearchView) mItem.getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(componentName  ));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.app_bar_search) {

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}