package com.example.task2.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;

import kotlinx.coroutines.scheduling.Task;

public class AddTaskDialogFragment extends DialogFragment {
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonSetTime, mButtonSetDate, mButtonSave, mButtonCancel;
    private TaskDBRepository mTaskDBRepository;
    private AutoCompleteTextView mAutoCompleteTextView;
    private TaskEntity mTask;
private String mString;
    public AddTaskDialogFragment() {
        // Required empty public constructor
    }


    public static AddTaskDialogFragment newInstance() {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check this context is true or not
        mTaskDBRepository = TaskDBRepository.getInstance(getContext());
        mTask = new TaskEntity();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        exposedDropdownMenus();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view).create();
        setListeners();
        return alertDialog;
    }

    private void setListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextTitle.getText().toString().equals(null) ||
                        isValueTextInput(mEditTextTitle.getText().toString()) ||
                       mAutoCompleteTextView.getText().toString().equals(null)) {
                    if (mEditTextTitle.getText().toString().equals(null) ||
                            isValueTextInput(mEditTextTitle.getText().toString())) {
                        mEditTextTitle.setText(null);
                        mEditTextTitle.setHint(R.string.cuationTitle);
                        mEditTextTitle.setHintTextColor(Color.RED);
                    }
                    if ( mAutoCompleteTextView.getText().toString().equals(null)) {
                        mAutoCompleteTextView.setHint(R.string.cuationTitle);
                        mAutoCompleteTextView.setHintTextColor(Color.RED);
                    }
                } else {
                    mTaskDBRepository.insertTask(mTask);
                    dismiss();
                }


            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTask.setState(State.valueOf(mAutoCompleteTextView.getText().toString()
                ));
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

    private void findViews(View view) {
        mAutoCompleteTextView = view.findViewById(R.id.filled_exposed_dropdown);
        mButtonSetDate = view.findViewById(R.id.btn_dialog_setDate);
        mButtonSetTime = view.findViewById(R.id.btn_dialog_setTime);
        mEditTextDescription = view.findViewById(R.id.edt_dialog_description);
        mEditTextTitle = view.findViewById(R.id.edt_dialog_title);
        mButtonSave = view.findViewById(R.id.btn_dialog_add_save);
        mButtonCancel = view.findViewById(R.id.btn_dialog_add_cancel);
    }

    public boolean isValueTextInput(String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) != ' ') return false;
        }
        return true;
    }


}