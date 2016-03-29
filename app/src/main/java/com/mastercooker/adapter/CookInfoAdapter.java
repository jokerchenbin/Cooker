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
import com.mastercooker.tools.Logger;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.job_info_item, parent,false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.job_info_item_name);
            holder.tv_tag1 = (TextView) convertView.findViewById(R.id.job_info_item_tag1);
            holder.tv_tag2 = (TextView) convertView.findViewById(R.id.job_info_item_tag2);
            holder.tv_tag3 = (TextView) convertView.findViewById(R.id.job_info_item_tag3);
            holder.tv_tag4 = (TextView) convertView.findViewById(R.id.job_info_item_tag4);
            holder.tv_place = (TextView) convertView.findViewById(R.id.job_info_item_place);
            holder.tv_date = (TextView) convertView.findViewById(R.id.job_info_item_date);
            holder.tv_money = (TextView) convertView.findViewById(R.id.job_info_item_money);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.job_info_item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        holder.tv_name.setText(cook.getName());
        Logger.getInstance().v("chenbin",cook.getFile().getFileUrl(context));
        ImageLoader.getInstance().displayImage(cook.getFile().getFileUrl(context),holder.iv_photo);
        return convertView;
    }






    private class ViewHolder {
        TextView tv_name, tv_tag1, tv_tag2, tv_tag3, tv_tag4, tv_place, tv_date, tv_money;
        ImageView iv_photo;
    }
}