package com.example.grupoes.projetoes.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.activities.PointOfSaleActivity;
import com.example.grupoes.projetoes.activities.ProductActivity;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;

import java.util.List;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    private LayoutInflater layoutInflater;

    public ProductSearchAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.search_item_card, parent, false);
        ProductSearchAdapter.ProductViewHolder viewHolder = new ProductSearchAdapter.ProductViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.nameTextView.setText(products.get(position).getProductName());
        holder.descriptionTextView.setText(products.get(position).getProductComment());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.search_item_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.search_item_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, PointOfSaleActivity.class);
            i.putExtra("PRODUCT_NAME", nameTextView.getText());
            context.startActivity(i);
        }
    }
}

