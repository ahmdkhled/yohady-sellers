package com.example.ecommerceseller.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ecommerceseller.model.LoginResponse;
import com.example.ecommerceseller.model.Seller;
import com.example.ecommerceseller.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Seller> loginResponse;
    private MutableLiveData<Seller> mSeller;


    public MutableLiveData<Seller> login(String email,String password){
        loginResponse=LoginRepository.getInstance().login(email,password);
        return loginResponse;
    }

    public MutableLiveData<Seller> signUp(Seller seller){
        mSeller=LoginRepository.getInstance().signUp(seller);
        return mSeller;
    }

    public MutableLiveData<String> getError() {
        return LoginRepository.getInstance().getError();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return LoginRepository.getInstance().getIsLoading();
    }

    public MutableLiveData<Seller> getRegistrationResponse() {
        return mSeller;
    }
    public MutableLiveData<Seller> getLoginResponse() {
        return loginResponse;
    }


}
