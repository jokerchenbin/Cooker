package com.mastercooker.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.model.ItemMenu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {

    private String TAG = "menu";
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<ItemMenu> itemMenus = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MenuAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void add(ItemMenu itemMenu) {
        itemMenus.add(itemMenu);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ItemMenu> itemMenus) {
        this.itemMenus.addAll(itemMenus);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final ItemMenu itemMenu = itemMenus.get(position);
        holder.mImageView.setImageResource(itemMenu.getBefore_click_src());
        holder.mTextView.setText(itemMenu.getName());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageView.setImageResource(itemMenu.getAfter_click_src());
                if (null != onItemClickListener)
                    onItemClickListener.itemClick(v);
                Log.i(TAG, "onClick ------miv----"+v.getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageView.setImageResource(itemMenu.getAfter_click_src());
                if (null != onItemClickListener)
                    onItemClickListener.itemClick(v);
                Log.i(TAG, "onClick ------iv----"+v.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemMenus.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(View view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_menu_iv);
            mTextView = (TextView) itemView.findViewById(R.id.item_menu_tv);
        }
    }
}
