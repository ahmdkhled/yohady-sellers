package com.example.ecommerceseller.repository;

import android.arch.lifecycle.MutableLiveData;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.network.RetrofitClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    private static CategoriesRepository categoriesRepository;

    private MutableLiveData<ArrayList<Category>> categoriesList;
    private MutableLiveData<Boolean> isCategoriesLoading =new MutableLiveData<>();
    private MutableLiveData<String> categoriesLoadingError =new MutableLiveData<>();



    public static CategoriesRepository getInstance(){
        if (categoriesRepository ==null)
            categoriesRepository =new CategoriesRepository();
        return categoriesRepository;
    }



    public MutableLiveData<ArrayList<Category>> getCategories(String page, String per_page,
                                                              String parent, String product,
                                                              String search,
                                                              String include,String exclude,
                                                              String slug, String hide_empty,
                                                              String order_by, String order){
        isCategoriesLoading.setValue(true);
        categoriesList=new MutableLiveData<>();
        RetrofitClient
                .getInstance()
                .getApiService()
                .getCategories(page,per_page,parent,product,search,include
                        ,exclude,slug,hide_empty,order_by,order)
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        isCategoriesLoading.setValue(false);
                            ArrayList<Category> list=response.body();
                            categoriesList.setValue(list);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        isCategoriesLoading.setValue(false);
                        categoriesLoadingError.setValue(t.getMessage());
                    }
                });
        return categoriesList;
    }




    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return isCategoriesLoading;
    }




    public MutableLiveData<String> getCategoriesLoadingError() {
        return categoriesLoadingError;
    }
}
