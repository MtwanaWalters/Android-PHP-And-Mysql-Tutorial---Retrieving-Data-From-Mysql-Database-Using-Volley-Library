package com.example.walter.projects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> products = new ArrayList<>();


    public RecyclerAdapter (Context context,List<Product> products){
        this.mContext = context;
        this.products = products;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mPrice;
        private ImageView mImageView;
        private RatingBar mRate;
        private LinearLayout mContainer;

        public MyViewHolder (View view){
            super(view);

            mTitle = view.findViewById(R.id.product_title);
            mImageView = view.findViewById(R.id.product_image);
            mRate = view.findViewById(R.id.product_rating);
            mPrice = view.findViewById(R.id.product_price);
            mContainer = view.findViewById(R.id.product_container);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.products_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Product product = products.get(position);

        holder.mPrice.setText("Ksh "+product.getPrice());
        holder.mRate.setRating(product.getRating());
        holder.mTitle.setText(product.getTitle());
        Glide.with(mContext).load(product.getImage()).into(holder.mImageView);

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailedProductsActivity.class);

                intent.putExtra("title",product.getTitle());
                intent.putExtra("image",product.getImage());
                intent.putExtra("rate",product.getRating());
                intent.putExtra("price",product.getPrice());

                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
