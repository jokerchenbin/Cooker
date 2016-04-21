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
import com.mastercooker.model.Post;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by THB on 2016/4/21.
 */
public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Post> list;
    public PostAdapter(Context context,List<Post> list) {
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
        Post post = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.post_info_item, parent,false);
            holder = new ViewHolder();
            holder.tv_user= (TextView) convertView.findViewById(R.id.post_info_item_tv_name);
            holder.tv_name= (TextView) convertView.findViewById(R.id.post_info_item_name);
            holder.tv_comment= (TextView) convertView.findViewById(R.id.post_info_item_number);
            holder.tv_des= (TextView) convertView.findViewById(R.id.post_info_item_description);
            holder.tv_time= (TextView) convertView.findViewById(R.id.post_info_item_time);
            holder.tv_type= (TextView) convertView.findViewById(R.id.post_info_item_type);
            holder.iv_photo= (ImageView) convertView.findViewById(R.id.post_info_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        holder.tv_user.setText(post.getPostUser());
        holder.tv_name.setText(post.getPostName());
        holder.tv_type.setText(post.getPostType());
        holder.tv_time.setText(post.getUpdatedAt().substring(0,10));
        holder.tv_des.setText(post.getDescription());
        holder.tv_comment.setText(String.valueOf(post.getCommentNumber()));
        ImageLoader.getInstance().displayImage(post.getImage().getFileUrl(context), holder.iv_photo);
        return convertView;
    }
    private class ViewHolder {
        TextView tv_user,tv_type,tv_name,tv_des,tv_time,tv_comment;
        ImageView iv_photo;
    }
}
