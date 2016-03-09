package com.mastercooker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercooker.model.Cook;
import com.mastercooker.model.CookLog;

import java.util.ArrayList;

public class CookLogAdapter extends RecyclerView.Adapter<FoodStyleAdapter.ItemViewHolder> {
    private ArrayList<CookLog> cookLogs = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public CookLogAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void add(CookLog cookLog) {
        this.cookLogs.add(cookLog);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<CookLog> cookLogs) {
        this.cookLogs.clear();
        this.cookLogs.addAll(cookLogs);
        notifyDataSetChanged();
    }

    @Override
    public FoodStyleAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FoodStyleAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
