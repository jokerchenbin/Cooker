package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mastercooker.R;
import com.mastercooker.model.MyUser;
import com.mastercooker.tools.FunctionUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_phono,et_code,et_username,et_password,et_conform;
    private Context context;
    private Activity activity;
    private TextView tv_click;
    private String errMessage;
    private static final int RESULT_ERROR = 0;
    private static final int RESULT_OK = 1;
    private MyUser myUser;
    private Button btn_register;
    private String telephone, username, password, nextpassword,messageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        activity = this;
        initView();
    }

    private void initView() {
        et_phono = (EditText) findViewById(R.id.phone_number);
        et_code = (EditText) findViewById(R.id.message_number);
        et_username = (EditText) findViewById(R.id.register_username);
        et_password = (EditText) findViewById(R.id.register_password);
        et_conform = (EditText) findViewById(R.id.register_check_password);
        tv_click = (TextView) findViewById(R.id.register_check_number);
        tv_click.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.register_btn);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /* 得到相应edittext中的值 */
        telephone = et_phono.getText().toString().trim();
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        nextpassword = et_conform.getText().toString().trim();
        switch (v.getId()){
            case R.id.register_check_number://获取短信验证码
                getVerification();
                break;
            case R.id.register_btn://注册
                registerUser();
                break;
        }
    }


    private void getVerification() {
        //检查手机号码是否合理
        if (!isMobile(telephone)) {
            Toast.makeText(activity, "手机号码有误，请检查！", Toast.LENGTH_SHORT).show();
            return;
        }
        timeHandler.sendEmptyMessageDelayed(1, 100);
        BmobSMS.requestSMSCode(context, telephone, "注册短信验证", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                Toast.makeText(context, "短信已发送，请注意查收.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private int time = 60;

    private Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (time == 0){
                tv_click.setText("获取验证码");
                tv_click.setFocusable(true);
                tv_click.setFocusableInTouchMode(true);
                tv_click.setClickable(true);
                time = 60;
            }else {
                tv_click.setClickable(false);
                tv_click.setText(time-- + "秒重发");
                timeHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    /**
     * 校验电话号码
     *
     * @param str
     * @return 是否合法的电话号码
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 判断输入信息是否完整
     *
     * @param username     用户名
     * @param password     密码
     * @param nextpassword 确认密码
     * @return
     */
    private boolean isFillCompletion(String username, String password, String nextpassword) {
        if ("".equals(username)) {
            errMessage = "用户名不能为空。";
            return false;
        }
        if (!password.equals(nextpassword)) {
            errMessage = "两次密码不一样，请重新输入！";
            return false;
        }
        if ("".equals(password) || "".equals(nextpassword)) {
            errMessage = "密码不能为空";
            return false;
        }
        return true;
    }


    private void registerUser() {
        messageNumber = et_code.getText().toString().trim();
        FunctionUtils.showLoadingDialog(activity);
        //检查手机号码是否合理
        if (!isMobile(telephone)) {
            Toast.makeText(activity, "手机号码有误，请检查！", Toast.LENGTH_SHORT).show();
            FunctionUtils.dissmisLoadingDialog();
            return;
        }
        //检查必须填的信息是否填完整
        if (!isFillCompletion(username, password, nextpassword)) {
            Toast.makeText(activity, errMessage, Toast.LENGTH_SHORT).show();
            FunctionUtils.dissmisLoadingDialog();
            return;
        }
        //验证短信号码 调试关闭验证
        checkMessageNumber(messageNumber);
    }

    private void checkMessageNumber(String number) {
        BmobSMS.verifySmsCode(context, telephone, number, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    registeHandler.sendEmptyMessage(RESULT_OK);
                }else {
                    registeHandler.sendEmptyMessage(RESULT_ERROR);
                    FunctionUtils.dissmisLoadingDialog();
                }

            }
        });
        //调试期间   不用验证短信验证码
        //registeHandler.sendEmptyMessage(RESULT_OK);
    }


    private Handler registeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RESULT_OK:
                    registerToBmob();
                    break;
                case  RESULT_ERROR:
                    Toast.makeText(context,"验证码输入错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void registerToBmob(){
        Toast.makeText(activity, "正在注册，请稍后...", Toast.LENGTH_SHORT).show();
        //使用Bmob自带的用户管理系统
        myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setMobilePhoneNumber(telephone);
        myUser.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                FunctionUtils.dissmisLoadingDialog();
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                if (i == 202) {
                    Toast.makeText(context, "此电话号码已注册过", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
                }
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }
}
