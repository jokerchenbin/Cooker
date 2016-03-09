package com.mastercooker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.model.FoodStyle;

import java.util.ArrayList;

public class FoodStyleAdapter extends RecyclerView.Adapter<FoodStyleAdapter.ItemViewHolder> {
    private ArrayList<FoodStyle> foodStyles = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    public FoodStyleAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_food_style_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        FoodStyle foodStyle = foodStyles.get(position);
        int id = foodStyle.getImage_name_id();
        final int startId = foodStyle.getId();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
        holder.mImageView.setImageBitmap(bitmap);
        holder.mTextView.setText(foodStyle.getDescribe());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemClick(startId);
            }
        });
    }

    public void add(FoodStyle foodStyle) {
        this.foodStyles.add(foodStyle);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<FoodStyle> foodStyles) {
        /*if (null != this.foodStyles && this.foodStyles.size() > 0) {
            this.foodStyles.clear();
            notifyDataSetChanged();
        }*/
        this.foodStyles.addAll(foodStyles);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return foodStyles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int imgId);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_food_style_list_iv);
            mTextView = (TextView) itemView.findViewById(R.id.item_food_style_list_tv);
        }
    }

}
