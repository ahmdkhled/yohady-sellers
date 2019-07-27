package com.example.ecommerceseller.repository;

import android.arch.lifecycle.MutableLiveData;
import com.example.ecommerceseller.model.Product;
import com.example.ecommerceseller.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRepository {

    private static ProductsRepository productsRepository;
    private MutableLiveData<Product> products;
    private MutableLiveData<Boolean> isProductUploading=new MutableLiveData<>();
    private MutableLiveData<String> productUploadingError=new MutableLiveData<>();


    public static ProductsRepository getInstance() {
        return productsRepository==null?new ProductsRepository():productsRepository;
    }

    public MutableLiveData<Product> uploadProduct(final Product product){
        products=new MutableLiveData<>();
        isProductUploading.setValue(true);

        RetrofitClient
                .getInstance()
                .getApiService()
                .uploadProduct(product)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        products.setValue(response.body());
                        isProductUploading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        isProductUploading.setValue(false);
                        productUploadingError.setValue(t.getMessage());

                    }
                });
        return products;
    }

    public MutableLiveData<Boolean> getIsProductUploading() {
        return isProductUploading;
    }

    public MutableLiveData<String> getProductUploadingError() {
        return productUploadingError;
    }
}
