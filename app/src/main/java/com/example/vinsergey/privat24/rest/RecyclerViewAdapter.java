package com.example.vinsergey.privat24.rest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vinsergey.privat24.R;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ModelCurrency> data = Collections.emptyList();

    public void setData(List<ModelCurrency> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ccy.setText(data.get(position).ccy);
        holder.base_ccy.setText(data.get(position).baseCcy);
        holder.bye.setText(data.get(position).buy);
        holder.sale.setText(data.get(position).sale);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ccy, base_ccy, bye, sale;
        ViewHolder(View itemView) {
            super(itemView);
            ccy = itemView.findViewById(R.id.text_ccy);
            base_ccy = itemView.findViewById(R.id.text_base_ccy);
            bye = itemView.findViewById(R.id.text_bye);
            sale = itemView.findViewById(R.id.text_sale);
        }
    }
}
