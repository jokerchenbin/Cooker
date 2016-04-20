package com.mastercooker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.mastercooker.model.Cook;
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
import java.net.URI;
import java.net.URL;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PostFoodActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private EditText et_name, et_description, et_food, et_keyWord, et_way1, et_way2, et_way3, et_way4;
    private Button btn_publish;
    private Context context;
    private int type = 1;
    private ImageView iv_header;
    private String tempImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_food);
        context = this;
        initView();
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.activity_post_food_spinner);
        String[] mItems = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_name = (EditText) findViewById(R.id.activity_post_food_et1);
        et_description = (EditText) findViewById(R.id.activity_post_food_et2);
        et_food = (EditText) findViewById(R.id.activity_post_food_et3);
        et_keyWord = (EditText) findViewById(R.id.activity_post_food_et4);
        et_way1 = (EditText) findViewById(R.id.activity_post_food_et5);
        et_way2 = (EditText) findViewById(R.id.activity_post_food_et6);
        et_way3 = (EditText) findViewById(R.id.activity_post_food_et7);
        et_way4 = (EditText) findViewById(R.id.activity_post_food_et8);
        btn_publish = (Button) findViewById(R.id.activity_post_food_bt);
        btn_publish.setOnClickListener(this);
        iv_header = (ImageView) findViewById(R.id.activity_post_food_upload);
        iv_header.setOnClickListener(this);
    }

    public void onClickBack(View view) {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString().trim();
        String description = et_description.getText().toString().trim();
        String food = et_food.getText().toString().trim();
        String keyword = et_keyWord.getText().toString().trim();
        String way1 = et_way1.getText().toString().trim();
        String way2 = et_way2.getText().toString().trim();
        String way3 = et_way3.getText().toString().trim();
        String way4 = et_way4.getText().toString().trim();
        switch (v.getId()) {
            case R.id.activity_post_food_bt://发布按钮
                if (isContentOk(name, description, food, keyword, way1, way2, way3, way4)) {
                    saveImage(name, description, food, keyword, way1, way2, way3, way4);
                    //publishFood(name, description, food, keyword, way1, way2, way3, way4);
                } else {
                    ToastDiy.showShort(context, err);
                }
                break;
            case R.id.activity_post_food_upload:
                showPicAnim();
                break;
        }
    }

    private void saveImage(final String name, final String description, final String food, final String keyword, final String way1, final String way2, final String way3, final String way4) {
         final File file = new File(tempImgPath);
        //上传头像文件  开起线程上传头像
        new Thread() {
            @Override
            public void run() {
                uploadFile(file,name, description, food, keyword, way1, way2, way3, way4);
            }
        }.start();
    }

    private BmobFile mFile;
    /**
     * Created by 陈彬 on 2016/1/4  15:40
     * 方法描述: 上传头像 并且更新到云端服务器
     */
    private void uploadFile(File file, final String name, final String description, final String food, final String keyword, final String way1, final String way2, final String way3, final String way4) {
        BTPFileResponse response = BmobProFile.getInstance(context).upload(file.getAbsolutePath(), new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                    mFile = file;
                publishFood(name, description, food, keyword, way1, way2, way3, way4,file);
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
            }
        });
    }



    private Uri photoUri = null;


    /**
     * Created by 陈彬 on 2015/12/30  17:56
     * 方法描述:  图片点击的动画加载
     */
    private void showPicAnim() {
       /* //加载 点击动画
        Animation alpha = AnimationUtils.loadAnimation(context, R.anim.image_anim);
        iv_header.startAnimation(alpha);*/

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
        SelectHeadTools.openDialog(context,this, photoUri);
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
               iv_header.setImageBitmap(bit);
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

    private void publishFood(String name, String description, String food, String keyword, String way1, String way2, String way3, String way4,BmobFile file) {
        FunctionUtils.showLoadingDialog(this);
        Cook cook = new Cook();
        cook.setName(name);
        cook.setDescription(description);
        cook.setFood(food);
        cook.setKeywords(keyword);
        String temp = " <h2>做法 </h2><hr>  ";
        String mess = temp + "<p> 1)" + way1 + "</p> " + "<p> 2)" + way2 + "</p> " + "<p> 3)" + way3 + "</p> " + "<p> 4)" + way4 + "</p> ";
        cook.setMessage(mess);
        cook.setFile(file);
        cook.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context, "保存成功");
                FunctionUtils.dissmisLoadingDialog();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context, s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }


    private String err;//错误信息

    /**
     * Created by 陈彬 on 2016/4/20  16:18
     * 方法描述: 判断内容是否OK
     *
     * @param name
     * @param description
     * @param food
     * @param keyword
     * @param way1
     * @param way2
     * @param way3
     * @param way4
     */
    private boolean isContentOk(String name, String description, String food, String keyword, String way1, String way2, String way3, String way4) {
        if (tempImgPath.isEmpty()){
            err = "请上传菜谱图片";
            return false;
        }
        if (name.isEmpty()) {
            err = "请输入菜谱名称";
            return false;
        }
        if (description.isEmpty()) {
            err = "概括描述不能为空";
            return false;
        }
        if (food.isEmpty()) {
            err = "请输入所需材料";
            return false;
        }
        if (keyword.isEmpty()) {
            err = "请输入关键字";
            return false;
        }
        if (way1.isEmpty()) {
            err = "请输入步骤1";
            return false;
        }
        if (way2.isEmpty()) {
            err = "请输入步骤2";
            return false;
        }
        if (way3.isEmpty()) {
            err = "请输入步骤3";
            return false;
        }
        if (way4.isEmpty()) {
            err = "请输入步骤4";
            return false;
        }

        return true;
    }
}
