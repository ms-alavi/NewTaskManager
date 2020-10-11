package com.example.task2.controller.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.task2.R;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;

import java.util.UUID;


public class TaskDetailDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_ID = "argsTaskId";
    private EditText mEditTextTitle, mEditTextDescription;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button mButtonTime, mButtonDate, mButtonSave, mButtonEdit, mButtonDelete;
    private TaskDBRepository mTaskDBRepository;
    private TaskEntity mTask;

    public TaskDetailDialogFragment() {
        // Required empty public constructor
    }

    public static TaskDetailDialogFragment newInstance(UUID taskId) {
        TaskDetailDialogFragment fragment = new TaskDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        mTask = mTaskDBRepository.getTask((UUID) getArguments().getSerializable(ARGS_TASK_ID));

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_task_detail_dialog, null);
        findViews(view);
        initViews();
        exposedDropdownMenus();
        setListeners();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        return dialog;

    }

    private void setListeners() {
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskDBRepository.deleteTask(mTask);
                dismiss();
            }
        });
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextTitle.setEnabled(true);
                mEditTextDescription.setEnabled(true);
                mButtonDate.setEnabled(true);
                mButtonTime.setEnabled(true);
                mAutoCompleteTextView.setEnabled(true);

            }
        });
        mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mTask.setDate(year+" / "+month+" / "+dayOfMonth);
                                mButtonDate.setText(year+" / "+month+" / "+dayOfMonth);
                            }

                        },2000,1,1);
                dialog.show();
            }
        });
        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = String.format("%02d", hourOfDay) + " : "
                                        + String.format("%02d", minute);
                                mTask.setTime(time);
                                mButtonTime.setText(time);
                            }
                        },24,00,false);

                dialog.show();
            }
        });
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTask.setState(mAutoCompleteTextView.getText().toString()
                );
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextTitle.getText().toString().equals(null) ||
                        isValueTextInput(mEditTextTitle.getText().toString()) ||
                        mAutoCompleteTextView.getText().toString().equals(null)) {
                    if (mEditTextTitle.getText().toString().equals(null) ||
                            isValueTextInput(mEditTextTitle.getText().toString())) {
                        mEditTextTitle.setText(null);
                        mEditTextTitle.setHint(R.string.cuationTitle);
                        mEditTextTitle.setHintTextColor(Color.RED);
                    }
                    if ( mAutoCompleteTextView.getText().toString().isEmpty()) {
                        mAutoCompleteTextView.setHint(R.string.cuationTitle);
                        mAutoCompleteTextView.setHintTextColor(Color.RED);
                    }
                }else {
                    mTaskDBRepository.update(mTask);
                    dismiss();
                }
            }
        });
    }

    private void initViews() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDescription());
        mButtonTime.setText(mTask.getTime());
        mButtonDate.setText(mTask.getDate());
        mAutoCompleteTextView.setText(mTask.getState());
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edt_dialog_title_detail);
        mEditTextDescription = view.findViewById(R.id.edt_dialog_description_detail);
        mAutoCompleteTextView = view.findViewById(R.id.filled_exposed_dropdown_detail);
        mButtonTime = view.findViewById(R.id.btn_dialog_setTime_detail);
        mButtonSave = view.findViewById(R.id.btn_dialog_detail_save);
        mButtonDate = view.findViewById(R.id.btn_dialog_setDate_detail);
        mButtonEdit = view.findViewById(R.id.btn_dialog_detail_edit);
        mButtonDelete = view.findViewById(R.id.btn_dialog_detail_delete);
    }
    public boolean isValueTextInput(String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) != ' ') return false;
        }
        return true;
    }
    private void exposedDropdownMenus() {
        String[] STATES = new String[]{State.Todo.name(), State.Doing.name(), State.Done.name()};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        STATES);
        mAutoCompleteTextView.setAdapter(adapter);
    }
}