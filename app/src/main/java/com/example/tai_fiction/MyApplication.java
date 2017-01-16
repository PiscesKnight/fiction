package com.example.tai_fiction;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Tai on 2016/4/11.
 */
public class MyApplication extends Application {

  //  public static RequestQueue queues;//请求队列

    @Override
    public void onCreate() {
        super.onCreate();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setWeixin("wxe14c1134c293ef0e","208e6ccd183fcb4b3a12a6d27302370a");
    }
}
