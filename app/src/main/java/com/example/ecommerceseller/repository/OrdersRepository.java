package com.example.ecommerceseller.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.ecommerceseller.model.Order;
import com.example.ecommerceseller.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepository {

    private static OrdersRepository ordersRepository;
    private MutableLiveData<ArrayList<Order>> orders=new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();

    public static OrdersRepository getInstance(){
        if (ordersRepository==null)
            ordersRepository=new OrdersRepository();
        return ordersRepository;
    }

    public MutableLiveData<ArrayList<Order>> getOrders(String marketId){
        isLoading.setValue(true);
        RetrofitClient
                .getInstance()
                .getApiService()
                .getOrders(marketId)
                .enqueue(new Callback<ArrayList<Order>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                        if (response.isSuccessful()){
                            ArrayList<Order> ordersList=response.body();
                            Log.d("VIEWMODELL",ordersList.size()+"order");
                            orders.setValue(ordersList);
                            isLoading.setValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                        isLoading.setValue(false);
                        Log.d("BOTTOMVIEWW",t.getMessage());
                    }
                });
        return orders;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
