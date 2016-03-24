package com.mastercooker;

import android.app.Activity;
import android.app.Application;

import com.mastercooker.tools.FunctionUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;


public class AppApplication extends Application {

    private String updateConfName = "android_update_conf.json";
    /**
     * 所以的Activity
     */
    public List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initUpdateJsonData();
        initCommonData();
        AppConfig.context = this;

        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "9d9709da41c3a86dcc5c6bd9c3498514");
        BmobInstallation.getCurrentInstallation(this).save();
        initImageLoader();
    }

    /**
     * Created by 陈彬 on 2016/1/5  16:43
     * 方法描述: 初始化ImageLoader的信息
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSizePercentage(30)
                .build();
        ImageLoader.getInstance().init(config);

        AppConfig.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageForEmptyUri(R.mipmap.ic_cooker)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_cooker)
                .build();
    }

    /**
     * 初始化asset的配置文件
     */
    public void initUpdateJsonData() {

        if (AppConfig.prefs == null) {
            AppConfig.prfsName = "parttime";
            AppConfig.prefs = getSharedPreferences(AppConfig.prfsName, 0);
        }
        if (AppConfig.jPushfs == null) {
            AppConfig.jPushfsName = "parttime_jpush";
            AppConfig.jPushfs = getSharedPreferences(AppConfig.jPushfsName, 0);
        }
    }

    /**
     * get all the activity
     *
     * @return List<Activity>
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activity add single activity in the activities
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 获取设备信息
     */
    public void initCommonData() {
        AppConfig.udId = FunctionUtils.getUdId(this);
        AppConfig.version = FunctionUtils.getAppVersion(this);
        AppConfig.apiVersion = FunctionUtils.getapiVersion(this);
        AppConfig.device = FunctionUtils.getDevice();
        AppConfig.os = FunctionUtils.getOs();
    }


}
