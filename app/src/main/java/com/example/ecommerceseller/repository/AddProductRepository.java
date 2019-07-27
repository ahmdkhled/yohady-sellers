package com.example.ecommerceseller.repository;

import android.arch.lifecycle.MutableLiveData;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.network.RetrofitClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductRepository {

    private static AddProductRepository addProductRepository;

    private MutableLiveData<ArrayList<Category>> categoriesList=new MutableLiveData<>();

    private MutableLiveData<Boolean> isCategoriesLoading =new MutableLiveData<>();

    private MutableLiveData<String> categoriesLoadingError =new MutableLiveData<>();


    public static AddProductRepository getInstance(){
        if (addProductRepository==null)
            addProductRepository=new AddProductRepository();
        return addProductRepository;
    }



    public MutableLiveData<ArrayList<Category>> getCategories(){
        RetrofitClient.getApiService()
                .getCategories(null,null,null,null,null,null,
        null,null,null,null,null)
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        if (response.isSuccessful()){
                            ArrayList<Category> list=response.body();
                            categoriesList.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

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
