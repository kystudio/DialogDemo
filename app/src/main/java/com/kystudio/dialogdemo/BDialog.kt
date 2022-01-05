package com.kystudio.dialogdemo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView

class BDialog(context: Context) : BaseDialog(context), View.OnClickListener {
    private var tvInfo: TextView? = null
    private var data: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_b)
        tvInfo = findViewById(R.id.tv_info)
        tvInfo?.text = data
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    //  注释1：这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    fun onDataCallback(data: String) {
        this.data = data
        tryToShow()
    }

    // 注释2 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    override fun intercept(chain: DialogChain) {
        super.intercept(chain)
        tryToShow()
    }

    private fun tryToShow() {
        // 只有同时满足这俩条件才能弹出来
        if (data != null && nextChain() != null) {
            this.show()
        }
    }
}