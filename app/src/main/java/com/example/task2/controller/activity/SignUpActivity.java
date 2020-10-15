package com.example.task2.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.task2.R;
import com.example.task2.controller.fragment.LoginFragment;
import com.example.task2.controller.fragment.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {
    public static final String EXTRA_USER_NAME = "extraUserName";
    public static final String EXTRA_SIGN_UP_USER_NAME = "extraSignUpUserName";
    public static final String EXTRA_SIGN_UP_PASSWORD = "extraSignUpPassword";
    public static final String EXTRA_PASSWORD = "extraPassword";
    public static final String SUA = "SUA";
    private String mPassword;
    private String mUserName;

    public static Intent newIntent(Context context, String pass, String userName){
        Intent intent=new Intent(context,SignUpActivity.class);
        intent.putExtra(EXTRA_SIGN_UP_PASSWORD,pass);
        intent.putExtra(EXTRA_SIGN_UP_USER_NAME,userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
//question************************************************************************************
        mPassword=getIntent().getStringExtra(EXTRA_SIGN_UP_PASSWORD);
        mUserName= getIntent().getStringExtra(EXTRA_SIGN_UP_USER_NAME);
        Log.d(SUA,"password For SignUpActivity : "+mPassword+ "  userName :"+mUserName);
*/

    }

    private void sendIntent(){
        Intent intent=new Intent();
        intent.putExtra(EXTRA_USER_NAME,mPassword);
        intent.putExtra(EXTRA_PASSWORD,mUserName);
        setResult(RESULT_OK,intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendIntent();



    }

    @Override
    public Fragment getFragment() {
        return SignUpFragment.newInstance(getIntent().getStringExtra(EXTRA_SIGN_UP_PASSWORD)
                ,getIntent().getStringExtra(EXTRA_SIGN_UP_USER_NAME));
    }
    /*@Override
    public Fragment getFragment() {
        return SignUpFragment.newInstance(mPassword,mUserName);
    }*/
}