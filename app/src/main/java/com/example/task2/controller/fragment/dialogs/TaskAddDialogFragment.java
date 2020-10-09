package com.example.task2.controller.fragment.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.task2.R;
import com.example.task2.controller.State;
import com.example.task2.model.TaskEntity;
import com.example.task2.repository.TaskDBRepository;


public class TaskAddDialogFragment extends DialogFragment {
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonSetTime, mButtonSetDate, mButtonSave, mButtonCancel;
    private TaskDBRepository mTaskDBRepository;
    private AutoCompleteTextView mAutoCompleteTextView;
    private TaskEntity mTask;
/*    private int mRequestCod;
    private ToDoListFragment mToDoListFragment;
    private DoingListFragment mDoingListFragment;
    private DoneListFragment mDoneListFragment;
    private List<TaskEntity> mTasks;*/


    public TaskAddDialogFragment() {
        // Required empty public constructor
    }


    public static TaskAddDialogFragment newInstance() {
        TaskAddDialogFragment fragment = new TaskAddDialogFragment();
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
        /*Fragment fragment=getTargetFragment();
        mRequestCod=getTargetRequestCode();
        switch (mRequestCod){
            case 0:
                mToDoListFragment= (ToDoListFragment) fragment;
                break;
            case 1:
                mDoingListFragment= (DoingListFragment) fragment;
                break;
            case 2:
                mDoneListFragment= (DoneListFragment) fragment;
            default:
                break;
        }*/

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
                    if ( mAutoCompleteTextView.getText().toString().isEmpty()) {
                        mAutoCompleteTextView.setHint(R.string.cuationTitle);
                        mAutoCompleteTextView.setHintTextColor(Color.RED);
                    }
                } else {
                    mTaskDBRepository.insertTask(mTask);

                   /* switch (mRequestCod){
                        case 0:
                            mTasks=mTaskDBRepository.getTasksForState(State.Todo);
                            mToDoListFragment.getTaskAdapter().setTasks( mTasks);
                            mToDoListFragment.getTaskAdapter().notifyDataSetChanged();
                            break;
                        case 1:
                            mTasks=mTaskDBRepository.getTasksForState(State.Doing);
                            mDoingListFragment.getTaskAdapter().setTasks(mTasks);
                            mDoingListFragment.getTaskAdapter().notifyDataSetChanged();
                            break;
                        case 2:
                            mTasks=mTaskDBRepository.getTasksForState(State.Done);
                            mDoneListFragment.getTaskAdapter().setTasks( mTasks);
                            mDoneListFragment.getTaskAdapter().notifyDataSetChanged();
                        default:
                            break;
                    }*/

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
                mTask.setState(mAutoCompleteTextView.getText().toString()
                );
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
        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d", hourOfDay) + " : "
                                + String.format("%02d", minute);
                        mTask.setTime(time);
                        mButtonSetTime.setText(time);
                    }
                },24,00,false);

                dialog.show();

            }
        });
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(getActivity(),
                       new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       mTask.setDate(year+" / "+month+" / "+dayOfMonth);
                       mButtonSetDate.setText(year+" / "+month+" / "+dayOfMonth);
                    }

                },2000,1,1);
                dialog.show();
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