package com.example.ecommerceseller.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ecommerceseller.R;
import com.example.ecommerceseller.viewmodel.DashboardViewModel;

public class DashboardFrag  extends Fragment {

    TextView productsNum,ordersNum,successRate,revenue;
    ProgressBar progressBar;
    DashboardViewModel dashboardViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dashboard_frag,container,false);
        productsNum=v.findViewById(R.id.productsNum);
        ordersNum=v.findViewById(R.id.ordersNum);
        successRate=v.findViewById(R.id.successRate);
        revenue=v.findViewById(R.id.revenue);
        progressBar=v.findViewById(R.id.dashboard_PB);
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);


        return v;
    }





}
