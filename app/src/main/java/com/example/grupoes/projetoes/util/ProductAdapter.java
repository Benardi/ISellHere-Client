package com.example.grupoes.projetoes.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.activities.ProductActivity;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.models.Product;

import java.util.List;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CustomViewHolder> {
    private List<Product> itens;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> itens) {
        this.itens = itens;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_card_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Product item = itens.get(i);

        customViewHolder.textView.setText(item.getProductName());
        customViewHolder.imageView.setImageBitmap(UtilOperations.StringToBitMap(item.getProductImage()));

        customViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ADAPTER", "CHAMOU ONCLICK");
                Intent i = new Intent(mContext, ProductActivity.class);
                i.putExtra("PRODUCT_NAME", item.getProductName());
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itens.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title_pos_product);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_product_adapter);
        }
    }
}
