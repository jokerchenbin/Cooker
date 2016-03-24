package com.mastercooker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.model.Cook;
import com.mastercooker.model.Util;

import java.io.File;
import java.util.ArrayList;

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.ItemViewHolder> {
    private ArrayList<Cook> cooks = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public CookAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void add(Cook cook) {
        this.cooks.add(cook);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Cook> cooks) {
        this.cooks.clear();
        notifyDataSetChanged();
        this.cooks.addAll(cooks);
        notifyDataSetChanged();
    }

    public void clear() {
        this.cooks.clear();
        notifyDataSetChanged();
    }

    @Override
    public CookAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_food_list, parent, false));
    }

    @Override
    public void onBindViewHolder(CookAdapter.ItemViewHolder holder, int position) {
        final Cook cook = cooks.get(position);
        final int id = mContext.getResources().getIdentifier(Util.getZero(cook.getId()), "drawable", "com.mastercooker");
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);

        holder.mImageView.setImageBitmap(bitmap);
        holder.mTextView.setText(cook.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemClick(cook);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cooks.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(Cook cook);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_food_list_iv);
            mTextView = (TextView) itemView.findViewById(R.id.item_food_list_tv);
        }
    }
}
