package com.example.ecommerceseller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerceseller.R;
import com.example.ecommerceseller.model.Product;
import com.example.ecommerceseller.utils.HtmlUtil;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    private Context context;
    private ArrayList<Product> products;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.product_row,viewGroup,false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder productHolder, int i) {
        Product product=products.get(i);
        productHolder.name.setText(product.getName());
        productHolder.price.setText(HtmlUtil.toString(product.getPrice_html()));

        if (product.getImages()!=null&&!product.getImages().isEmpty()){
            Glide.with(context)
                    .load(product.getImages().get(0).getSrc())
                    .into(productHolder.image);
        }else{
            productHolder.image.setImageResource(R.drawable.notfound);
        }
    }

    @Override
    public int getItemCount() {
        return products==null?0:products.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        TextView name,price;
        Button edit,delete;
        ImageView image;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            image=itemView.findViewById(R.id.product_image);
        }
    }
}
