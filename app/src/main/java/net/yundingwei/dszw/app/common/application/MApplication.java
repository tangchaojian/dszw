package net.yundingwei.dszw.app.common.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import net.yundingwei.dszw.app.net.HttpService;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化okhttp
        HttpService.init(this);

        //初始化Fresco
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);
    }
}
