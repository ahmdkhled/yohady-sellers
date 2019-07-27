package com.example.ecommerceseller.repository;

public class ProductsRepository {

    private static ProductsRepository productsRepository;

    public static ProductsRepository getInstance() {
        return productsRepository==null?new ProductsRepository():productsRepository;
    }


}
