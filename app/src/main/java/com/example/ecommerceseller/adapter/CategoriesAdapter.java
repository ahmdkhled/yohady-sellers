package com.example.ecommerceseller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ecommerceseller.R;
import com.example.ecommerceseller.model.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {


    private Context context;
    private ArrayList<Category> categories;
    private OnCategoryItemClicked onCategoryItemClicked;
    private boolean reset=false;


    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public CategoriesAdapter(Context context, ArrayList<Category> categories
            , OnCategoryItemClicked onCategoryItemClicked) {
        this.context = context;
        this.categories = categories;
        this.onCategoryItemClicked = onCategoryItemClicked;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.category_row,viewGroup,false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
        if (reset){
            categoryHolder.categoryCB.setChecked(false);
            if (i==categories.size()-1)
                reset=false;
        }

        Category category=categories.get(i);
        categoryHolder.categoryName.setText(category.getName());
    }

    public void add(ArrayList<Category> categories){
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }
    public void reset(){
        reset=true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories==null?0:categories.size();
    }


    class CategoryHolder extends RecyclerView.ViewHolder{
        CheckBox categoryCB;
        TextView categoryName;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            categoryCB=itemView.findViewById(R.id.category_CB);
            categoryName=itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryCB.setChecked(!categoryCB.isChecked());
                    onCategoryItemClicked.onCategoryItemClicked(
                            categories
                            .get(getAdapterPosition())
                            .getId(),categoryCB.isChecked());
                }
            });

            categoryCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryItemClicked.onCategoryItemClicked(
                            categories
                                    .get(getAdapterPosition())
                                    .getId(),categoryCB.isChecked());
                }
            });
        }
    }

    public interface OnCategoryItemClicked{
        void onCategoryItemClicked(int id,boolean checked);
    }
}
