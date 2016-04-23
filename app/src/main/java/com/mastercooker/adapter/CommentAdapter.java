package com.mastercooker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.model.Comment;
import com.mastercooker.model.Post;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by THB on 2016/4/22.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> list;
    public CommentAdapter(Context context,List<Comment> list) {
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
        Comment comment = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_info_item, parent,false);
            holder = new ViewHolder();
            holder.tv_number= (TextView) convertView.findViewById(R.id.comment_info_item_number);
            holder.tv_name= (TextView) convertView.findViewById(R.id.comment_info_item_name);
            holder.tv_content= (TextView) convertView.findViewById(R.id.comment_info_item_content);
            holder.tv_time= (TextView) convertView.findViewById(R.id.comment_info_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        holder.tv_number.setText((position+1)+""+"楼");
        holder.tv_name.setText(comment.getCommentUser());
        holder.tv_content.setText(comment.getContent());
        holder.tv_time.setText(comment.getCreatedAt().substring(0,10));
        return convertView;
    }
    private class ViewHolder {
        TextView tv_number,tv_name,tv_content,tv_time;
    }
}
