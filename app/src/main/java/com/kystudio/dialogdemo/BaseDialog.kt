package com.kystudio.dialogdemo

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog

abstract class BaseDialog(context: Context) : AlertDialog(context), DialogInterceptor {

    private var _chain: DialogChain? = null

    // 下一个拦截器
    fun nextChain(): DialogChain? = _chain

    @CallSuper
    override fun intercept(chain: DialogChain) {
        _chain = chain
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.attributes?.width = 600
        window?.attributes?.height = 800

        // 弹窗消失时把请求移交给下一个拦截器
        setOnDismissListener {
            _chain?.process()
        }
    }

}