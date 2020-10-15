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

import com.example.task2.R;
import com.example.task2.controller.activity.SignUpActivity;
import com.example.task2.controller.activity.TaskPagerActivity;
import com.example.task2.model.User;
import com.example.task2.repository.TaskDBRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    public static final int REQUEST_CODE_SignUpActivity = 0;
    public static final String LF = "LF";
    public static final String ARGS_LOGIN_PASS = "argsLoginPass";
    public static final String ARGS_LOGIN_USER_NAME = "argsLoginUserName";
    private TextInputEditText mEditTextUserName, mEditTextPassword;
    private Button mButtonLogin, mButtonSignUp;
    private View mSbView;
    private List<User> mUser;
    private String mPassword = "";
    private String mUserName = "";
    private TaskDBRepository mTaskDBRepository;
    private View mLinearLayout;
    private boolean mFlag;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String pass, String userName) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_LOGIN_PASS, pass);
        args.putString(ARGS_LOGIN_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getContext());

            mUserName = getArguments().getString(ARGS_LOGIN_USER_NAME);
            mPassword = getArguments().getString(ARGS_LOGIN_PASS);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        mEditTextUserName.setText(mUserName);
        mEditTextPassword.setText(mPassword);
        mEditTextPassword.setHint(R.string.enter_your_password);
        mEditTextUserName.setHint(R.string.enter_your_user_name);
    }

    private void setListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = mTaskDBRepository.getUserWithPassAndUser(mPassword, mUserName);
                if (mUser.size() == 1) {
                    startActivity(TaskPagerActivity.newIntent(getActivity(), mUser.get(0).getId()));
                } else {
                    Log.d(LF, "password   " + mPassword + "  userName : " + mUserName);
                    if (!mPassword.equals("") && !mUserName.equals("") && isValueTextInput(mUserName)) {
                        Snackbar snackbar = Snackbar.make(mLinearLayout, R.string.doRegister, Snackbar.LENGTH_LONG);
                        mSbView = snackbar.getView();
                        mSbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                        snackbar.show();
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
                }

            }
        });

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

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LF, "password :" + mPassword + " UserName :" + mUserName);
                Intent intent = SignUpActivity.newIntent(getActivity(), mPassword, mUserName);
                startActivityForResult(intent, REQUEST_CODE_SignUpActivity);
                mFlag = true;
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


    private void findViews(View view) {
        mLinearLayout = view.findViewById(R.id.lnr_login);
        mEditTextPassword = view.findViewById(R.id.edt_login_password);
        mEditTextUserName = view.findViewById(R.id.edt_login_user_name);
        mButtonLogin = view.findViewById(R.id.btn_login_login);
        mButtonSignUp = view.findViewById(R.id.btn_login_sign_up);
    }
}