package com.mastercooker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.mastercooker.R;
import com.mastercooker.model.Cook;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/18 16:07
 */
public class CookInfoAdapter extends BaseAdapter {
    private Context context;
    private List<Cook> list;

    public CookInfoAdapter(Context context,List<Cook> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Cook cook = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cook_info_item, parent,false);
            holder = new ViewHolder();
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.cook_info_item_photo);
            holder.tv_food_name=(TextView)convertView.findViewById(R.id.cook_info_item_food_name);
            holder.tv_keyword= (TextView) convertView.findViewById(R.id.cook_info_item_keyword);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        holder.tv_food_name.setText(cook.getName());
        ImageLoader.getInstance().displayImage(cook.getFile().getFileUrl(context), holder.iv_photo);
        holder.tv_keyword.setText(cook.getKeywords());
        return convertView;
    }






    private class ViewHolder {
        TextView tv_food_name,tv_keyword;
        ImageView iv_photo;
    }
}
