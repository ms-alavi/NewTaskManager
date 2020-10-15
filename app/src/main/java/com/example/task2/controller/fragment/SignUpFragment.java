package com.example.task2.controller.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.task2.R;
import com.example.task2.controller.activity.LoginActivity;
import com.example.task2.model.User;
import com.example.task2.repository.TaskDBRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.task2.controller.activity.SignUpActivity.EXTRA_PASSWORD;
import static com.example.task2.controller.activity.SignUpActivity.EXTRA_USER_NAME;

public class SignUpFragment extends Fragment {
    public static final String ARGS_SIGN_UP_PASS = "argsSignUpPass";
    public static final String ARGS_SIGN_UP_USER = "argsSignUpUser";
    public static final String SUF = "SUF";

    private TextInputEditText mEditTextUserName, mEditTextPassword;
    private Button mButtonRegister;
    private TaskDBRepository mTaskDBRepository;
    private List<User> mUser;
    private String mUserName;
    private String mPassword;
    private View mSbView;
    private LinearLayout mLinearLayout;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String pass, String userName) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_SIGN_UP_PASS, pass);
        args.putString(ARGS_SIGN_UP_USER, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getContext());
        mUserName = getArguments().getString(ARGS_SIGN_UP_USER);
        mPassword = getArguments().getString(ARGS_SIGN_UP_PASS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        setListeners();
        initViews();
        return view;
    }

    private void initViews() {
        mEditTextUserName.setText(mUserName);
        mEditTextPassword.setText(mPassword);
        mEditTextUserName.setHint(R.string.enter_your_user_name);
        mEditTextPassword.setHint(R.string.enter_your_password);

    }

    private void setListeners() {
        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = mTaskDBRepository.getUserWithPassAndUser(mPassword, mUserName);
                Log.d(SUF,"Size Of mUser" + mUser.size());
                if (mUser.size() == 0) {
                    if (!mPassword.equals("") && !mUserName.equals("") && isValueTextInput(mUserName)) {
                        User user = new User();
                        user.setPassword(mPassword);
                        user.setUserName(mUserName);
                        mTaskDBRepository.insertUser(user);
                        sendIntent();
                        getActivity().finish();
                    } else {
                        if (mPassword.equals("")) {
                            mEditTextPassword.setHint(R.string.cuationTitle);
                            mEditTextPassword.setHintTextColor(Color.RED);
                        }
                        if (mUserName.equals("") || isValueTextInput(mUserName)) {
                            mEditTextUserName.setText(null);
                            mEditTextUserName.setHint(R.string.cuationTitle);
                            mEditTextUserName.setHintTextColor(Color.RED);
                        }
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(mLinearLayout, R.string.Registered
                            , Snackbar.LENGTH_LONG);
                    mSbView = snackbar.getView();
                    mSbView.setBackgroundColor(ContextCompat.getColor
                            (getActivity(), R.color.colorPrimaryDark));
                    snackbar.show();

                }
            }
        });
    }

    public boolean isValueTextInput(String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) != ' ') return true;
        }
        return false;
    }

    private void sendIntent(){
        Intent intent=new Intent();
        intent.putExtra(EXTRA_USER_NAME,mPassword);
        intent.putExtra(EXTRA_PASSWORD,mUserName);
        getActivity().setResult(RESULT_OK,intent);
    }

    private void findViews(View view) {
        mLinearLayout = view.findViewById(R.id.lnr_sign_up);
        mEditTextPassword = view.findViewById(R.id.edt_sign_up_password);
        mEditTextUserName = view.findViewById(R.id.edt_sign_up_user_name);
        mButtonRegister = view.findViewById(R.id.btn_sign_up_register);
    }
}