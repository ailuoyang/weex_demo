package com.zqs.weex_demo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.weex.*
import org.apache.weex.adapter.DefaultWXHttpAdapter
import org.apache.weex.adapter.IWXImgLoaderAdapter
import org.apache.weex.common.WXImageStrategy
import org.apache.weex.common.WXRenderStrategy
import org.apache.weex.dom.WXImageQuality
import org.apache.weex.utils.WXFileUtils
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), IWXRenderListener {
    var mWXSDKInstance: WXSDKInstance? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (WXSDKEngine.isInitialized()) {
            Log.w("mTest", "weex初始化成功")
        } else {
            Log.w("mTest", "weex初始化失败")
        }

        thread {
            var flag = true
            while (flag) {
                if (WXSDKEngine.isInitialized()) {
                    runOnUiThread {
                        mWXSDKInstance = WXSDKInstance(this@MainActivity)
                        mWXSDKInstance!!.registerRenderListener(this@MainActivity)
                        /**
                         * bundleUrl source http://dotwe.org/vue/38e202c16bdfefbdb88a8754f975454c
                         */
                        val pageName = "bundle.js"
                        val bundleUrl = "http://192.168.2.195:8088/bundle.js"

                        //mWXSDKInstance!!.renderByUrl(pageName, bundleUrl, null, null, WXRenderStrategy.APPEND_ASYNC)
                        mWXSDKInstance!!.render(
                            pageName,
                            WXFileUtils.loadAsset("bundle.js", this),
                            null,
                            null,
                            WXRenderStrategy.APPEND_ASYNC
                        )
                    }
                    flag = false
                }

            }
        }

    }

    override fun onViewCreated(instance: WXSDKInstance?, view: View?) {
        container.addView(view)
    }

    override fun onRenderSuccess(
        instance: WXSDKInstance,
        width: Int,
        height: Int
    ) {
    }

    override fun onRefreshSuccess(
        instance: WXSDKInstance,
        width: Int,
        height: Int
    ) {
    }

    override fun onException(
        instance: WXSDKInstance,
        errCode: String,
        msg: String
    ) {
    }

    override fun onResume() {
        super.onResume()
        if (mWXSDKInstance != null) {
            mWXSDKInstance!!.onActivityResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mWXSDKInstance != null) {
            mWXSDKInstance!!.onActivityPause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mWXSDKInstance != null) {
            mWXSDKInstance!!.onActivityStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWXSDKInstance != null) {
            mWXSDKInstance!!.onActivityDestroy()
        }
    }
}