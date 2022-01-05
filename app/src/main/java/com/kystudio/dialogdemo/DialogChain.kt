package com.kystudio.dialogdemo

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class DialogChain private constructor(
    // 弹窗的时候可能需要Activity/Fragment环境
    val activity: FragmentActivity? = null,
    val fragment: Fragment? = null,
    private var interceptors: MutableList<DialogInterceptor>?
) {
    companion object {
        private const val TAG = "DialogChain"

        @JvmStatic
        fun create(): Builder {
            return Builder()
        }
    }

    private var index: Int = 0

    // 执行拦截器
    fun process() {
        interceptors ?: return
        when (index) {
            in interceptors!!.indices -> {
                val interceptor = interceptors!![index]
                index++
                interceptor.intercept(this)
            }
            // 最后一个弹窗关闭的时候，我们希望释放所有弹窗引用
            interceptors!!.size -> {
                Log.i(TAG, "===> clearAllInterceptors")
                clearAllInterceptors()
            }
        }
    }

    private fun clearAllInterceptors() {
        interceptors?.clear()
        interceptors = null
    }

    // 构建者模式
    open class Builder {
        private var activity: FragmentActivity? = null
        private var fragment: Fragment? = null
        private val interceptors by lazy(LazyThreadSafetyMode.NONE) {
            mutableListOf<DialogInterceptor>()
        }

        // 关联Activity
        fun attach(activity: FragmentActivity): Builder {
            this.activity = activity
            return this
        }

        // 关联Fragment
        fun attach(fragment: Fragment): Builder {
            this.fragment = fragment
            return this
        }

        // 添加一个拦截器
        fun addInterceptor(interceptor: DialogInterceptor): Builder {
            if (!interceptors.contains(interceptor)) {
                interceptors.add(interceptor)
            }
            return this
        }

        fun build(): DialogChain {
            return DialogChain(activity, fragment, interceptors)
        }
    }
}

interface DialogInterceptor {
    fun intercept(chain: DialogChain)
}