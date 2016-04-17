package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mastercooker.R;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPswActivity extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private EditText old_psw,new_psw,new_psw2;
    private Button changepsw_btn;
    String old,new1,new2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        activity=this;
        context=this;
        old_psw=(EditText)findViewById(R.id.old_psw);
        new_psw=(EditText)findViewById(R.id.new_psw);
        new_psw2=(EditText)findViewById(R.id.new_psw2);
        changepsw_btn=(Button)findViewById(R.id.changepsw_btn);
        changepsw();
    }


    private void changepsw(){
        changepsw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old=old_psw.getText().toString().trim();
                new1=new_psw.getText().toString().trim();
                new2=new_psw2.getText().toString().trim();
               BmobUser.updateCurrentUserPassword(context, old, new1, new UpdateListener() {
                   @Override
                   public void onSuccess() {
                       ToastDiy.showShort(context, "密码修改成功");
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
        });
    }

}
