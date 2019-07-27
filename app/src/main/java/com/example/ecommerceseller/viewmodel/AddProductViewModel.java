package com.example.ecommerceseller.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.repository.AddProductRepository;
import java.util.ArrayList;


public class AddProductViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categoriesList;



    public MutableLiveData<ArrayList<Category>> getCategories(){
        if (categoriesList==null)
            categoriesList=AddProductRepository.getInstance()
                    .getCategories();
        return categoriesList;
    }





    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return AddProductRepository.getInstance().getIsCategoriesLoading();
    }


    public MutableLiveData<String> getCategoriesLoadingError() {
        return AddProductRepository.getInstance().getCategoriesLoadingError();
    }



}
