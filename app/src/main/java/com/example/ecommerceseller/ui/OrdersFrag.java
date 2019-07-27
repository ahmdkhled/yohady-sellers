package com.example.ecommerceseller.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ecommerceseller.R;
import com.example.ecommerceseller.adapter.OrdersAdapter;
import com.example.ecommerceseller.model.Order;
import com.example.ecommerceseller.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class OrdersFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.orders_frag,container,false);
        final RecyclerView ordersRecycler=v.findViewById(R.id.ordersRecycler);
        final ProgressBar ordersPB=v.findViewById(R.id.orders_PB);

        OrdersViewModel ordersViewModel= ViewModelProviders.of(this).get(OrdersViewModel.class);
        MutableLiveData<ArrayList<Order>> orders=ordersViewModel.getOrders("1");
        orders.observe(this, new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Order> orders) {
                Log.d("VIEWMODELL","on changed "+orders.size());
                OrdersAdapter ordersAdapter=new OrdersAdapter(getContext(),orders);
                ordersRecycler.setAdapter(ordersAdapter);
                ordersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        ordersViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean!=null&&aBoolean){
                    ordersPB.setVisibility(View.VISIBLE);
                }else{
                    ordersPB.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }
}
