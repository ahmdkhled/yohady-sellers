package com.example.ecommerceseller.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.repository.CategoriesRepository;
import java.util.ArrayList;


public class AddProductViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categoriesList;



    public MutableLiveData<ArrayList<Category>> getCategories(){
        if (categoriesList==null)
            categoriesList= CategoriesRepository.getInstance()
                    .getCategories();
        return categoriesList;
    }





    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return CategoriesRepository.getInstance().getIsCategoriesLoading();
    }


    public MutableLiveData<String> getCategoriesLoadingError() {
        return CategoriesRepository.getInstance().getCategoriesLoadingError();
    }



}
