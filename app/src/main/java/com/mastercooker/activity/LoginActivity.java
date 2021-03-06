package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mastercooker.R;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_username,et_password;
    private Button bt_login,bt_register;
    private String tel,password;
    private Activity activity;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=this;
        context=this;
        et_username=(EditText)findViewById(R.id.login_username);
        et_password=(EditText)findViewById(R.id.login_password);
        bt_login=(Button)findViewById(R.id.login_btn);
        bt_login.setOnClickListener(this);
        bt_register=(Button)findViewById(R.id.register_btn);
        bt_register.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                //登录按钮
                FunctionUtils.showLoadingDialog(activity);
                Login();
                break;
            case R.id.register_btn:
                //注册按钮
                Intent registerIntent=new Intent(activity,RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }
    private void Login(){
        tel=et_username.getText().toString().trim();
        password=et_password.getText().toString().trim();
        BmobUser user=new BmobUser();
        user.setUsername(tel);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"登录成功");
                Intent intent=new Intent(activity,MainActivity.class);
                startActivity(intent);
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context,"登录失败");
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }
}
