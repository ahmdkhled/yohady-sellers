package com.example.ecommerceseller.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.ecommerceseller.R;
import com.example.ecommerceseller.adapter.CategoriesAdapter;
import com.example.ecommerceseller.model.Attribute;
import com.example.ecommerceseller.model.Category;
import com.example.ecommerceseller.model.Product;
import com.example.ecommerceseller.utils.FileUtil;
import com.example.ecommerceseller.utils.SessionManager;
import com.example.ecommerceseller.viewmodel.AddProductViewModel;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddProductFrag extends Fragment implements CategoriesAdapter.OnCategoryItemClicked{

    Button uploadProduct,uploadImages;
    TextInputLayout nameIL,priceIL,salePriceIL,descIL;
    ProgressBar ProductUploadPB,categoriiesPB;
    RecyclerView categoriesRecycler;
    AddProductViewModel addProductViewModel;
    ArrayList<Category> categories;
    int marketId=-1;
    private int PICK_IMAGE=1;
    private int STORAGE_PERMISSION_CODE=12;
    private CategoriesAdapter categoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_product_frag,container,false);

        uploadProduct =v.findViewById(R.id.uploadProduct);
        uploadImages =v.findViewById(R.id.uploadMedia);
        nameIL =v.findViewById(R.id.productName_IL);
        priceIL =v.findViewById(R.id.productPrice_IL);
        salePriceIL =v.findViewById(R.id.productSalePrice_IL);
        descIL =v.findViewById(R.id.productDesc_IL);
        ProductUploadPB =v.findViewById(R.id.uploadProduct_PB);
        categoriiesPB =v.findViewById(R.id.categories_PB);
        categoriesRecycler=v.findViewById(R.id.categories_recycler);
        categories=new ArrayList<>();

        clearFields();
        addProductViewModel= ViewModelProviders.of(this).get(AddProductViewModel.class);
        marketId= SessionManager.getInstance(getContext()).getMarketId();
        marketId=1;

        getCategories(1);
        observeCategories();
        observeCategoriesLoadingError();
        observeIsCategoriesLoading();
        showCategories();

        uploadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marketId==-1){
                    Toast.makeText(getContext(), "register a market first before uploading products !",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (validateInput()){
                    uploadProduct.setEnabled(false);
                    String name=nameIL.getEditText().getText().toString();
                    String price=priceIL.getEditText().getText().toString();
                    String salePrice=salePriceIL.getEditText().getText().toString();
                    String desc=descIL.getEditText().getText().toString();

                    uploadProduct(name,price,desc,salePrice);

                    clearFields();
                }else {
                    Toast.makeText(getContext(), "missing params", Toast.LENGTH_SHORT).show();
                }



            }
        });

        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermession();
            }
        });

        return v;
    }

    private void uploadProduct(String name, String price, String desc, String salePrice){
        Product p =new Product();
        p.setName(name);
        p.setRegular_price(price);
        p.setSale_price(salePrice);
        p.setDescription(desc);
        p.setType("simple");
        p.setStatus("pending");


        ArrayList<String> options=new ArrayList<>();
        options.add(String.valueOf(marketId));
        Attribute attribute=new Attribute("sellerId",options,false);
        ArrayList<Attribute> attributes=new ArrayList<>();
        attributes.add(attribute);
        p.setAttributes(attributes);

        Log.d("CATTTTTTTT","size "+categories.size());
        for (Category c:categories) {
            Log.d("CATTTTTTTT", "category id: "+c.getId());
        }

        p.setCategories(categories);
        addProductViewModel.uploadProduct(p);
        observeUploading();
        observeProductUploadingError();
        observeIsProductUploading();

    }

    private void getCategories(int page){
        addProductViewModel
                .getCategories(String.valueOf(page),null,null,null,null
                ,null,null,null,null,null,null);
    }


    void observeUploading(){
        addProductViewModel.uploadProduct()
                .observe(getActivity(), new Observer<Product>() {
                    @Override
                    public void onChanged(@Nullable Product product) {
                        Toast.makeText(getContext(), "uploaded ", Toast.LENGTH_SHORT).show();
                        uploadProduct.setEnabled(true);

                    }
                });
    }

    void observeProductUploadingError(){
        addProductViewModel.getProductUploadingError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getContext(), s,
                                Toast.LENGTH_SHORT).show();
                        uploadProduct.setEnabled(true);

                    }
                });
    }

    void observeIsProductUploading(){
        addProductViewModel.getIsProductsUploading()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean)
                            ProductUploadPB.setVisibility(View.VISIBLE);
                        else
                            ProductUploadPB.setVisibility(View.GONE);
                    }
                });
    }

    void observeCategories(){
        addProductViewModel.getCategories()
                .observe(this, new Observer<ArrayList<Category>>() {
                    @Override
                    public void onChanged(@Nullable final ArrayList<Category> categories) {
                        if (categories!=null){
                            categoriesAdapter.add(categories);
                        }else{
                            Toast.makeText(getContext(), "error loading categories", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    void observeCategoriesLoadingError(){
        addProductViewModel.getCategoriesLoadingError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getContext(), s,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    void observeIsCategoriesLoading(){
        addProductViewModel.getIsCategoriesLoading()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean)
                            categoriiesPB.setVisibility(View.VISIBLE);
                        else
                            categoriiesPB.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE){
            if (data!=null){
                uploadImages(8,data.getData());
                Toast.makeText(getContext(), "picked "+data.getDataString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==STORAGE_PERMISSION_CODE){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        }
    }

    private void pickImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent,PICK_IMAGE);
    }

    private void uploadImages(int productId, Uri imageUri){
        RequestBody productIdPart=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productId));
        File file=new File(FileUtil.getPath(imageUri, getContext()));

            //Log.d("UPLOOOOD","file "+file.toString());
        RequestBody imagePart=RequestBody.create
                (MediaType.parse(getContext().getContentResolver()
                        .getType(imageUri)),file);
        MultipartBody.Part image =MultipartBody.Part.createFormData("image",file.getName(),imagePart);



    }

    private void requestPermession(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                }else{
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                }
            }else {
                pickImage();
            }
        }else {
            pickImage();
        }
    }

    private void showCategories(){
        categoriesAdapter=new CategoriesAdapter(getContext(),new ArrayList<Category>() ,this);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecycler.setAdapter(categoriesAdapter);
    }

    private boolean validateInput(){
        boolean pass=true;

        if (TextUtils.isEmpty(nameIL.getEditText().getText())){
            nameIL.setError("Required");
            pass=false;
        }else{
            nameIL.setError(null);

        }
        if (TextUtils.isEmpty(priceIL.getEditText().getText())){
            priceIL.setError("Required");
            pass=false;
        }else{
            priceIL.setError(null);

        }
        if (TextUtils.isEmpty(descIL.getEditText().getText())){
            descIL.setError("Required");
            pass=false;
        }else{
            descIL.setError(null);

        }

        return pass;
    }

    private void clearFields(){
        nameIL.getEditText().setText("");
        priceIL.getEditText().setText("");
        salePriceIL.getEditText().setText("");
        descIL.getEditText().setText("");
        nameIL.clearFocus();
        descIL.clearFocus();
        priceIL.clearFocus();
        salePriceIL.clearFocus();
    }


    @Override
    public void onCategoryItemClicked(int id,boolean checked) {
        if (checked){
            categories.add(new Category(id));
        }else{
            categories.remove(new Category(id));
        }
    }
}
