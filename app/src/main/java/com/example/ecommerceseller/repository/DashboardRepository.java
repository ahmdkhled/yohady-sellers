package com.example.ecommerceseller.repository;



public class DashboardRepository {

    private static DashboardRepository dashboardRepository;

    public static DashboardRepository getInstance(){
        if (dashboardRepository==null)
            dashboardRepository=new DashboardRepository();
        return dashboardRepository;
    }

}
