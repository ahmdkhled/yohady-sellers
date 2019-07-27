package com.example.ecommerceseller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerceseller.R;
import com.example.ecommerceseller.model.LineItem;
import com.example.ecommerceseller.model.Product;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    private Context context;
    private ArrayList<LineItem> orderItemsList;
    private ArrayList<Product> products;

    public OrderItemsAdapter(Context context, ArrayList<LineItem> orderItemsList, ArrayList<Product> products) {
        this.context = context;
        this.products=products;
        this.orderItemsList = orderItemsList;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.order_item_row,parent,false);
        return new OrderItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        LineItem orderItem=orderItemsList.get(position);
        Product product=products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(orderItem.getPrice()));
        holder.quantity.setText(String.valueOf(orderItem.getQuantity()));
        if (product.getImages()!=null&&!product.getImages().isEmpty()){
            Glide.with(context).load(product.getImages().get(0).getSrc())
                    .into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.notfound);
        }

    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        TextView name,price,quantity;
        ImageView image;
        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.order_item_name);
            price=itemView.findViewById(R.id.order_item_price);
            quantity=itemView.findViewById(R.id.order_item_quantity);
            image=itemView.findViewById(R.id.order_item_image);
        }
    }
}
