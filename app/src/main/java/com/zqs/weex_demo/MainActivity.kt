package com.zqs.weex_demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.weex.IWXRenderListener
import org.apache.weex.WXSDKEngine
import org.apache.weex.WXSDKInstance
import org.apache.weex.common.WXRenderStrategy
import org.apache.weex.utils.WXFileUtils
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), IWXRenderListener {
    var mWXSDKInstance: WXSDKInstance? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * bundleUrl source http://dotwe.org/vue/38e202c16bdfefbdb88a8754f975454c
         */
        val pageName = "bundle.js"
        val bundleUrl = "http://192.168.2.195:8088/bundle.js"
        flush.setOnClickListener{
            finish()
            var intent:Intent= Intent(this,this.javaClass)
            startActivity(intent)
        }
        thread {
            var flag = true
            while (flag) {
                if (WXSDKEngine.isInitialized()) {
                    runOnUiThread {
                        mWXSDKInstance = WXSDKInstance(this@MainActivity)
                        mWXSDKInstance!!.registerRenderListener(this@MainActivity)
                        //mWXSDKInstance!!.renderByUrl(pageName, bundleUrl, null, null, WXRenderStrategy.APPEND_ASYNC)
                        mWXSDKInstance!!.renderByUrl(
                            pageName,
                            bundleUrl?:WXFileUtils.loadAsset("bundle.js", this),
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
        Toast.makeText(this,"渲染成功",Toast.LENGTH_SHORT).show()
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
        Toast.makeText(this,"渲染异常:${errCode}->${msg}",Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        if (mWXSDKInstance != null&&this!=null) {
            mWXSDKInstance!!.onActivityResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mWXSDKInstance != null&&this!=null) {
            mWXSDKInstance!!.onActivityPause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mWXSDKInstance != null&&this!=null) {
            mWXSDKInstance!!.onActivityStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWXSDKInstance != null&&this!=null) {
            mWXSDKInstance!!.onActivityDestroy()
        }
    }
}