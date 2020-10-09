package com.example.task2.controller.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.task2.R;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;

import java.util.UUID;


public class TaskDetailDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_ID = "argsTaskId";
    private EditText mEditTextTitle,mEditTextDescription;
private AutoCompleteTextView mAutoCompleteTextView;
private Button mButtonTime,mButtonDate,mButtonSave,mButtonEdit,mButtonDelete;
private TaskDBRepository mTaskDBRepository;
private TaskEntity mTask;

    public TaskDetailDialogFragment() {
        // Required empty public constructor
    }

    public static TaskDetailDialogFragment newInstance(UUID taskId) {
        TaskDetailDialogFragment fragment = new TaskDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository=TaskDBRepository.getInstance(getActivity());
        mTask=mTaskDBRepository.getTask((UUID) getArguments().getSerializable(ARGS_TASK_ID));

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_task_detail_dialog,null);
        findViews(view);
        initViews();
        AlertDialog dialog=new AlertDialog.Builder(getActivity()).setView(view).create();
        return dialog;

    }

    private void initViews() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDescription());
        mButtonTime.setText(mTask.getTime());
        mButtonDate.setText(mTask.getDate());
        mAutoCompleteTextView.setText(mTask.getState().name());
    }

    private void findViews(View view) {
        mEditTextTitle=view.findViewById(R.id.edt_dialog_title_detail);
        mEditTextDescription=view.findViewById(R.id.edt_dialog_description_detail);
        mAutoCompleteTextView=view.findViewById(R.id.filled_exposed_dropdown_detail);
        mButtonTime=view.findViewById(R.id.btn_dialog_setTime_detail);
        mButtonSave=view.findViewById(R.id.btn_dialog_detail_save);
        mButtonDate=view.findViewById(R.id.btn_dialog_setDate_detail);
        mButtonEdit=view.findViewById(R.id.btn_dialog_detail_edit);
        mButtonDelete=view.findViewById(R.id.btn_dialog_detail_delete);
    }
}