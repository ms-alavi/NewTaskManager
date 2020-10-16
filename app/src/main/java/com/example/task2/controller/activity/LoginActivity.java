package com.example.task2.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.task2.R;
import com.example.task2.controller.fragment.LoginFragment;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends SingleFragmentActivity {

    public static final String EXTRA_LOGIN_USER_NAME = "extraLoginUserName";
    public static final String EXTRA_LOGIN_PASSWORD = "extraLoginPassword";
    public static final String LA = "LA";
    private String mPassword;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LA,"password : "+mPassword+"userName"+ mUserName);
    }

    public Intent newIntent(Context context, String pass, String userName) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_LOGIN_PASSWORD, pass);
        intent.putExtra(EXTRA_LOGIN_USER_NAME, userName);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || requestCode != RESULT_OK) return;
        if (requestCode == LoginFragment.REQUEST_CODE_SignUpActivity) {
            mPassword=data.getStringExtra(SignUpActivity.EXTRA_PASSWORD);
            mUserName=data.getStringExtra(SignUpActivity.EXTRA_USER_NAME);

        }
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance(mPassword, mUserName);
    }

}