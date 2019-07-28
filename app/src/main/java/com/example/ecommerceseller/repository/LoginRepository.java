package com.example.ecommerceseller.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.ecommerceseller.model.LoginResponse;
import com.example.ecommerceseller.model.Seller;
import com.example.ecommerceseller.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    // variables
    private static final String TAG = "LoginRepositoryLogs";

    private static LoginRepository loginRepository;
    private MutableLiveData<Seller> loginResponse=new MutableLiveData<>();
    private MutableLiveData<Seller> registerResponse=new MutableLiveData<>();
    private MutableLiveData<String> mError=new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();

    public static LoginRepository getInstance(){
        if (loginRepository==null)
            loginRepository=new LoginRepository();
        return  loginRepository;
    }

    public MutableLiveData<Seller> login(String email,String password){
        Log.d(TAG, "login: inside login");
        isLoading.setValue(true);
        RetrofitClient.getInstance().getApiService()
                .login(email,password)
                .enqueue(new Callback<Seller>() {
                    @Override
                    public void onResponse(Call<Seller> call, Response<Seller> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful()){
                            Log.d(TAG, "onResponse: successful response");
                            loginResponse.setValue(response.body());
                        }else {
                            try {
                                Log.d(TAG, "onResponse: error code "+response.code());
                                String errorBody = response.errorBody().string();
                                errorBody = errorBody.substring(7);
                                Log.d(TAG, "onResponse: error"+errorBody);
                                mError.setValue("email or password is incorrect or you haven't verified your email ");
                                Gson gson = new Gson();
                                Error error = gson.fromJson(errorBody,Error.class);
                                Log.d(TAG, "onResponse: error msg "+error.getMessage());

                            } catch (Exception e) {
                                Log.d(TAG, "onResponse: catch "+e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Seller> call, Throwable t) {
                        Log.d(TAG, "onFailure: failed to login");
                        isLoading.setValue(false);
                        mError.setValue(t.getMessage());
                    }
                });

        return loginResponse;
    }

    public MutableLiveData<Seller> signUp(Seller seller){
        Log.d(TAG, "signUp: inside sign up");
        Log.d(TAG, "signUp: trying to register a new seller with name "+seller.getName());
        isLoading.setValue(true);
        RetrofitClient.getInstance().getApiService()
                .registerSeller(seller.getName(),
                        seller.getEmail(),
                        seller.getPassword(),
                        seller.getState(),
                        seller.getCity(),
                        seller.getAddress(),
                        seller.getMobile(),
                        seller.getMarketName())
                .enqueue(new Callback<Seller>() {
                    @Override
                    public void onResponse(Call<Seller> call, Response<Seller> response) {
                        if (response.isSuccessful()){
                            Log.d(TAG, "onResponse: successful response");
                            isLoading.setValue(false);
                            if(response.body() != null) {
                                registerResponse.setValue(response.body());
                            }else Log.d(TAG, "onResponse: seller is null");
                        }else{
                            try {
                                Log.d(TAG, "onResponse: error code "+response.code());
                                JSONObject error = new JSONObject(response.errorBody().string());
                                Log.d(TAG, "kkk: ibra " + error.getString("message"));
                            } catch (Exception e) {
                                Log.d(TAG, "onResponse: catch");
                                e.printStackTrace();
                            }

                            isLoading.setValue(false);
                            mError.setValue(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Seller> call, Throwable t) {
                        Log.d(TAG, "onFailure: register a new seller is failed");
                        isLoading.setValue(false);
                        mError.setValue(t.getMessage());
                    }
                });

        return registerResponse;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }


}
