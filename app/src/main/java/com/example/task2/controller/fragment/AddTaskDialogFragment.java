package com.example.task2.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.task2.R;
import com.example.task2.model.State;
import com.example.task2.model.Task;
import com.example.task2.repository.TaskDBRepository;

public class AddTaskDialogFragment extends DialogFragment {
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonSetTime,mButtonSetDate,mButtonSave,mButtonCancel;
    private TaskDBRepository mTaskDBRepository;
private AutoCompleteTextView mAutoCompleteTextView;
private boolean mFlag;
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
        mTaskDBRepository=TaskDBRepository.getInstance(getContext());

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        exposedDropdownMenus();
        AlertDialog alertDialog= new AlertDialog.Builder(getActivity())
                .setView(view).create();
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task=new Task();
                if (mEditTextTitle.getText().toString().isEmpty() ||
                        isValueTextInput(mEditTextTitle.getText().toString())||
                                mAutoCompleteTextView.getText().toString().isEmpty()) {
                    if (mEditTextTitle.getText().toString().isEmpty() ||
                            isValueTextInput(mEditTextTitle.getText().toString())) {
                        mEditTextTitle.setText(null);
                        mEditTextTitle.setHint(R.string.cuationTitle);
                        mEditTextTitle.setHintTextColor(Color.RED);
                    }
                    if (mAutoCompleteTextView.getText().toString().isEmpty()) {
                        mAutoCompleteTextView.setHint(R.string.cuationTitle);
                        mAutoCompleteTextView.setHintTextColor(Color.RED);
                    }
                }else {
                    task.setTitle(mEditTextTitle.getText().toString());
                    task.setState(State.valueOf(mAutoCompleteTextView.getText().toString()));
                    mTaskDBRepository.insertTask(task);
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
        return alertDialog;
    }

    private void exposedDropdownMenus() {
        String[] STATES=new String[]{State.Todo.name(),State.Doing.name(),State.Done.name()};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        STATES);
        mAutoCompleteTextView.setAdapter(adapter);
    }

    private void findViews(View view) {
        mAutoCompleteTextView=view.findViewById(R.id.filled_exposed_dropdown);
        mButtonSetDate=view.findViewById(R.id.btn_dialog_setDate);
        mButtonSetTime=view.findViewById(R.id.btn_dialog_setTime);
        mEditTextDescription=view.findViewById(R.id.edt_dialog_description);
        mEditTextTitle=view.findViewById(R.id.edt_dialog_title);
        mButtonSave=view.findViewById(R.id.btn_dialog_add_save);
        mButtonCancel=view.findViewById(R.id.btn_dialog_add_cancel);
    }
    public boolean isValueTextInput(String string){
        int length=string.length();
        for (int i = 0; i <length ; i++) {
            if (string.charAt(i)!=' ')return false;
        }
        return true;
    }


}