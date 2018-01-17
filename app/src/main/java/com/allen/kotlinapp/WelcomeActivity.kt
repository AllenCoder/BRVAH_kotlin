package com.allen.kotlinapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity

/**
 * 文 件 名: WelcomeActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/20 11:28
 * 修改时间：
 * 修改备注：
 */
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        Handler().postDelayed({
            startActivity<HomeActivity>()
            finish()
            Logger.d(this@WelcomeActivity)
        }, 2000)
    }
}
