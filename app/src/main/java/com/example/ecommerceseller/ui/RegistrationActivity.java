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
import com.example.ecommerceseller.model.Seller;
import com.example.ecommerceseller.viewmodel.LoginViewModel;

public class RegistrationActivity extends AppCompatActivity {

    // views
    TextInputLayout mEmailInputLayout,mPasswordInputLayout,mNameInputLayout,mMarketNameInputLayout,mStateInputLayout
            ,mCityInputLayout,mAddressInputLayout,mMobileInputLayout;
    AppCompatEditText mEmailTxt,mPasswordTxt,mNameTxt,mMarketNameTxt,mStateTxt,mCityTxt,mAddressTxt,mMobileTxt;
    AppCompatButton mRegisterBtn;
    ProgressBar progressBar;

    Toolbar mToolbar;

    // variables
    private static final String TAG = "RegistrationActivityLogs";
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailInputLayout = findViewById(R.id.email_register_txt_input_layout);
        mPasswordInputLayout = findViewById(R.id.password_register_txt_input_layout);
        mNameInputLayout = findViewById(R.id.name_register_txt_input_layout);
        mMarketNameInputLayout = findViewById(R.id.market_name_register_txt_input_layout);
        mStateInputLayout = findViewById(R.id.state_register_txt_input_layout);
        mCityInputLayout = findViewById(R.id.city_register_txt_input_layout);
        mAddressInputLayout = findViewById(R.id.address_register_txt_input_layout);
        mMobileInputLayout = findViewById(R.id.mobile_register_txt_input_layout);
        mEmailTxt = findViewById(R.id.email_reg_edit_txt);
        mPasswordTxt = findViewById(R.id.password_reg_edit_txt);
        mNameTxt = findViewById(R.id.name_reg_edit_txt);
        mMarketNameTxt = findViewById(R.id.market_name_reg_edit_txt);
        mStateTxt = findViewById(R.id.state_reg_edit_txt);
        mCityTxt = findViewById(R.id.city_reg_edit_txt);
        mAddressTxt = findViewById(R.id.address_reg_edit_txt);
        mMobileTxt = findViewById(R.id.mobile_reg_edit_txt);
        mRegisterBtn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progressBar);
        mToolbar = findViewById(R.id.toolbar);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.register_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDateCompleted()){
                    loginViewModel.signUp(getSellerInfo());
                    observeRegistrationResponse();
                    observeRegistrationError();
                    observeRegistrationStatus();
                }else{
                    Toast.makeText(RegistrationActivity.this, getString(R.string.uncompeted_data_msg), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void observeRegistrationStatus() {
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

    private void observeRegistrationError() {
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: failed to reister new seller "+s);
            }
        });
    }

    private void observeRegistrationResponse() {
        loginViewModel.getRegistrationResponse().observe(this, new Observer<Seller>() {
            @Override
            public void onChanged(@Nullable Seller seller) {
                if(seller != null){
                    Log.d(TAG, "onChanged:seller not null");
                    Log.d(TAG, "onChanged:seller id is "+seller.getId());
                }else Log.d(TAG, "onChanged: seller is null");
            }
        });
    }

    private Seller getSellerInfo() {
        return new Seller(mNameTxt.getText().toString(),
                mEmailTxt.getText().toString(),mPasswordTxt.getText().toString(),
                mStateTxt.getText().toString(),mCityTxt.getText().toString(),
                mAddressTxt.getText().toString(),mMobileTxt.getText().toString(),
                mMarketNameTxt.getText().toString());
    }

    private boolean isDateCompleted(){
        boolean pass = true;
        TextInputLayout[] textInputLayouts = {mEmailInputLayout,mPasswordInputLayout,mNameInputLayout,mMarketNameInputLayout,mStateInputLayout,mCityInputLayout,mAddressInputLayout,mMobileInputLayout};
        for (TextInputLayout textInputLayout :textInputLayouts){
            if(TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())){
                textInputLayout.setError(getString(R.string.required_field));
                pass = false;
            }else textInputLayout.setError(null);
        }

        return pass;
    }

}
