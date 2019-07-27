package com.example.ecommerceseller.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.model.Product;
import com.example.ecommerceseller.repository.CategoriesRepository;
import com.example.ecommerceseller.repository.ProductsRepository;

import java.util.ArrayList;


public class AddProductViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categoriesList;
    private MutableLiveData<Product> Products;



    public MutableLiveData<ArrayList<Category>> getCategories(){
        if (categoriesList==null)
            categoriesList= CategoriesRepository.getInstance()
                    .getCategories();
        return categoriesList;
    }


    public void uploadProduct(Product product){
        Products = ProductsRepository.getInstance()
                .uploadProduct(product);
    }

    public MutableLiveData<Product> uploadProduct() {
        return Products;
    }

    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return CategoriesRepository.getInstance().getIsCategoriesLoading();
    }


    public MutableLiveData<String> getCategoriesLoadingError() {
        return CategoriesRepository.getInstance().getCategoriesLoadingError();
    }

    public MutableLiveData<Boolean> getIsProductsUploading() {
        return ProductsRepository.getInstance().getIsProductUploading();
    }


    public MutableLiveData<String> getProductUploadingError() {
        return ProductsRepository.getInstance().getProductUploadingError();
    }


}
