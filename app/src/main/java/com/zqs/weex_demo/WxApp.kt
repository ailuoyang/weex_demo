package com.zqs.weex_demo

import android.app.Application
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.apache.weex.InitConfig
import org.apache.weex.WXEnvironment
import org.apache.weex.WXSDKEngine
import org.apache.weex.adapter.DefaultWXHttpAdapter
import org.apache.weex.adapter.IWXHttpAdapter
import org.apache.weex.adapter.IWXImgLoaderAdapter
import org.apache.weex.common.WXImageStrategy
import org.apache.weex.common.WXRequest
import org.apache.weex.dom.WXImageQuality

/**
@author zhangqisheng
@date 2020-06-2020/6/5 11:37
@description
 **/
class WxApp: Application() {
    override fun onCreate() {
        super.onCreate()
        WXEnvironment.setApkDebugable(true)
        val config = InitConfig.Builder() //图片库接口
            .setImgAdapter(object : IWXImgLoaderAdapter {
                override fun setImage(url: String?, view: ImageView?, quality: WXImageQuality?, strategy: WXImageStrategy?) {
                    Glide.with(view!!).load(url).into(view)
                }

            }) //网络库接口
            .setHttpAdapter(DefaultWXHttpAdapter())
            .build()
        WXSDKEngine.initialize(this, config)
    }
}