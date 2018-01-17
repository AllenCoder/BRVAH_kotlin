package com.allen.kotlinapp

import android.app.Application
import android.content.Context
import com.allen.kotlinapp.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.addLogAdapter

/**
 * 文 件 名: MyApplication
 * 创 建 人: Allen
 * 创建日期: 2017/6/13 11:54
 * 修改时间：
 * 修改备注：
 */
class MyApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any
        instance = this
        Utils.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("hello");
    }
}
