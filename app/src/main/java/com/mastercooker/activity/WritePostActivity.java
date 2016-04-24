package com.mastercooker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.mastercooker.Configs;
import com.mastercooker.R;
import com.mastercooker.model.Post;
import com.mastercooker.tools.FileTools;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.Logger;
import com.mastercooker.tools.SelectHeadTools;
import com.mastercooker.tools.ToastDiy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

public class WritePostActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private Context context;
    private EditText et_name,et_des,et_content;
    private ImageView iv_image;
    private String type = "秀厨艺";
    private TextView tv_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        context = this;
        initView();
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.activity_write_post_spinner);
        final String[] mItems = getResources().getStringArray(R.array.postType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = mItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_name = (EditText) findViewById(R.id.activity_write_post_et_title);
        et_des = (EditText) findViewById(R.id.activity_write_post_et_description);
        et_content = (EditText) findViewById(R.id.activity_write_post_et_content);
        iv_image = (ImageView) findViewById(R.id.activity_write_post_photo);
        iv_image.setOnClickListener(this);
        tv_post = (TextView) findViewById(R.id.activity_write_post_tv_submit);
        tv_post.setOnClickListener(this);
    }
    public void onClickBack(View view) {
        this.finish();
    }



    private String err;

    private boolean isContentOk(String tempImgPath, String name, String des, String content){
        if (tempImgPath.isEmpty()){
            err = "请添加图片";
            return false;
        }
        if (name.isEmpty()){
            err = "请输入帖子名称";
            return false;
        }
        if (des.isEmpty()){
            err = "请输入帖子描述";
            return false;
        }
        if (content.isEmpty()){
            err = "请输入帖子内容";
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_write_post_tv_submit://发布
                String name = et_name.getText().toString().trim();
                String des = et_des.getText().toString().trim();
                String content = et_content.getText().toString().trim();
                if (isContentOk(tempImgPath,name,des,content)){
                    saveImage(name, des, content);
                }else {
                    ToastDiy.showShort(context,err);
                }
                break;
            case R.id.activity_write_post_photo://相册
                showPicAnim();
                break;
        }
    }

    private BmobFile mFile;
    private void saveImage(final String name, final String des,final String content){
        FunctionUtils.showLoadingDialog(this);
        File file = new File(tempImgPath);
        BTPFileResponse response = BmobProFile.getInstance(context).upload(file.getAbsolutePath(), new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                mFile = file;
                postWrite(name, des, content, file);
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
            }
        });
    }

    private void postWrite(String name, String des, String content,BmobFile file) {
        //FunctionUtils.showLoadingDialog(this);
        Post post = new Post();
        post.setPostName(name);
        post.setPostUser(BmobUser.getCurrentUser(context).getUsername());
        post.setDescription(des);
        post.setContent(content);
        post.setImage(file);
        post.setCommentNumber(0);
        post.setPostType(type);
        post.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"保存成功");
                FunctionUtils.dissmisLoadingDialog();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
                ToastDiy.showShort(context,i+" "+s);
            }
        });
    }

    private Uri photoUri = null;
    private String tempImgPath="";

    /**
     * Created by 陈彬 on 2015/12/30  17:56
     * 方法描述:  图片点击的动画加载
     */
    private void showPicAnim() {
        //弹出头像选择
        if (!FileTools.hasSdcard()) {
            Toast.makeText(context, "没有找到SD卡，请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(context, "创建文件失败", Toast.LENGTH_SHORT).show();
            return;
        }
        SelectHeadTools.openDialog(context, this, photoUri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(this, photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data == null) {
                    return;
                }
                SelectHeadTools.startPhotoZoom(this, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data == null) {
                    return;
                }
                Bitmap bit = data.getExtras().getParcelable("data");
                iv_image.setImageBitmap(bit);
                saveBitmaptoLocal(bit);
                break;
        }
    }


    /**
     * Created by 陈彬 on 2016/1/4  11:38
     * 方法描述: 保存头像图片 Bitmap  到本地
     */
    private void saveBitmaptoLocal(Bitmap bitmap) {
        long l2 = System.currentTimeMillis();
        String fileName = l2 + ".jpg";
        tempImgPath = Environment.getExternalStorageDirectory() + Configs.SystemPicture.SAVE_DIRECTORY + fileName;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
