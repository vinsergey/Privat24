package com.example.vinsergey.privat24.rest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vinsergey.privat24.R;
import com.example.vinsergey.privat24.db.Currency;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Currency> data = Collections.emptyList();
    private View.OnClickListener clickListener;

    public RecyclerViewAdapter(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecyclerViewAdapter() {
    }

    public void setData(List<Currency> data) {
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
        switch (data.get(position).ccy) {
            case "USD":
                holder.leftFlag.setImageResource(R.drawable.american_flag);
                break;
            case "EUR":
                holder.leftFlag.setImageResource(R.drawable.euro_flag);
                break;
            case "RUR":
                holder.leftFlag.setImageResource(R.drawable.russia_flag);
                break;
            case "UAH":
                holder.leftFlag.setImageResource(R.drawable.ukraine_flag);
                break;
            case "BTC":
                holder.leftFlag.setImageResource(R.drawable.bitcoin_flag);
                break;
            default:
                holder.leftFlag.setImageResource(R.drawable.default_flag);
                break;
        }
        switch (data.get(position).base_ccy) {
            case "USD":
                holder.rightFlag.setImageResource(R.drawable.american_flag);
                break;
            case "EUR":
                holder.rightFlag.setImageResource(R.drawable.euro_flag);
                break;
            case "RUR":
                holder.rightFlag.setImageResource(R.drawable.russia_flag);
                break;
            case "UAH":
                holder.rightFlag.setImageResource(R.drawable.ukraine_flag);
                break;
            case "BTC":
                holder.rightFlag.setImageResource(R.drawable.bitcoin_flag);
                break;
            default:
                holder.rightFlag.setImageResource(R.drawable.default_flag);
                break;
        }

        holder.itemView.setTag(data.get(position));
        holder.dateTime.setText(data.get(position).dateTime);
        holder.bye.setText(data.get(position).bye);
        holder.sale.setText(data.get(position).sale);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bye, sale, dateTime;
        private ImageView leftFlag, rightFlag;

        ViewHolder(View itemView) {
            super(itemView);
            leftFlag = itemView.findViewById(R.id.flag_left);
            rightFlag = itemView.findViewById(R.id.flag_right);
            dateTime = itemView.findViewById(R.id.date_time);
            bye = itemView.findViewById(R.id.text_bye);
            sale = itemView.findViewById(R.id.text_sale);
        }
    }
}
