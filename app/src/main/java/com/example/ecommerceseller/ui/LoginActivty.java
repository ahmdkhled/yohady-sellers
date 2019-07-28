package com.example.ecommerceseller.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommerceseller.R;
import com.example.ecommerceseller.model.LoginResponse;
import com.example.ecommerceseller.model.Seller;
import com.example.ecommerceseller.viewmodel.LoginViewModel;

public class LoginActivty extends AppCompatActivity {

    // views
    TextInputLayout mEmailTextInputLayout,mPasswordTextInputLayout;
    AppCompatEditText mEmailTxt,mPasswordTxt;
    AppCompatButton mLoginBtn;
    Toolbar mToolbar;
    ProgressBar progressBar;

    // variables
    private static final String TAG = "LoginActivityLogs";
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        // bind views
        mEmailTextInputLayout = findViewById(R.id.email_register_txt_input_layout);
        mPasswordTextInputLayout = findViewById(R.id.password_register_txt_input_layout);
        mEmailTxt = findViewById(R.id.email_reg_edit_txt);
        mPasswordTxt = findViewById(R.id.password_reg_edit_txt);
        mLoginBtn = findViewById(R.id.login_btn);
        mToolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.register_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDateCompleted()){
                    loginViewModel.login(mEmailTxt.getText().toString(),mPasswordTxt.getText().toString());
                    observeLoginResponse();
                    observeLoginError();
                    observeLoginStatus();
                }else {
                    Toast.makeText(LoginActivty.this, getString(R.string.uncompeted_data_msg), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void observeLoginStatus() {
        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null) {
                    if (aBoolean) progressBar.setVisibility(View.VISIBLE);
                    else progressBar.setVisibility(View.GONE);
                }else Log.d(TAG, "onChanged: aboolean is null ");
            }
        });
    }

    private void observeLoginError() {
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: failed to login "+s);
            }
        });
    }

    private void observeLoginResponse() {
        loginViewModel.getLoginResponse().observe(this, new Observer<Seller>() {
            @Override
            public void onChanged(@Nullable Seller seller) {
                if(seller != null){
                    Log.d(TAG, "onChanged:seller not null");
                    Log.d(TAG, "onChanged:seller id is "+seller.getId());
                }else Log.d(TAG, "onChanged: seller is null");
            }
        });
    }

    private boolean isDateCompleted(){
        boolean pass = true;
        TextInputLayout[] textInputLayouts = {mEmailTextInputLayout,mPasswordTextInputLayout};
        for (TextInputLayout textInputLayout :textInputLayouts){
            if(TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())){
                textInputLayout.setError(getString(R.string.required_field));
                pass = false;
            }else textInputLayout.setError(null);
        }

        return pass;
    }
}
