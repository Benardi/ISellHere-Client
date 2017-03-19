package com.example.grupoes.projetoes.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.presentation.ui.activities.PointOfSaleActivity;
import com.example.grupoes.projetoes.models.PointOfSale;

import java.util.List;

/**
 * Created by Wesley on 02/03/2017.
 */

public class PointOfSaleAdapter extends RecyclerView.Adapter<PointOfSaleAdapter.PointOfSaleViewHolder> {
    private Context context;
    private List<PointOfSale> pointsOfSale;
    private LayoutInflater layoutInflater;

    public PointOfSaleAdapter(Context context, List<PointOfSale> pointsOfSale) {
        this.context = context;
        this.pointsOfSale = pointsOfSale;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PointOfSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.search_item_card, parent, false);
        PointOfSaleViewHolder viewHolder = new PointOfSaleViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PointOfSaleViewHolder holder, int position) {
        holder.nameTextView.setText(pointsOfSale.get(position).getName());
        holder.descriptionTextView.setText(pointsOfSale.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return pointsOfSale.size();
    }


    public class PointOfSaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public PointOfSaleViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.search_item_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.search_item_description);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, PointOfSaleActivity.class);
            i.putExtra("POINT_NAME", nameTextView.getText());
            context.startActivity(i);
        }
    }
}
