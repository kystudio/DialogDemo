package com.kystudio.dialogdemo

import android.content.Context
import android.os.Bundle
import android.view.View

class ADialog(context: Context) : BaseDialog(context), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_a)
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    override fun intercept(chain: DialogChain) {
        super.intercept(chain)
        val isShow = true // 注释2：这里可根据实际业务场景来定制dialog 显示条件
        if (isShow) {
            this.show()
        } else { // 注释3：当自己不具备弹出条件的时候，可以立刻把请求转交给下一个拦截器
            chain.process()
        }
    }
}