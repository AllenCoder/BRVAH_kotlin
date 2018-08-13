package com.allen.kotlinapp

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.allen.kotlinapp.adapter.AnimationAdapter
import com.allen.kotlinapp.animation.CustomAnimation
import com.allen.kotlinapp.entity.Status
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jaredrummler.materialspinner.MaterialSpinner
import com.kyleduo.switchbutton.SwitchButton
import org.jetbrains.anko.toast

/**
 * 文 件 名: AnimationUseActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/13 14:00
 * 修改时间：
 * 修改备注：
 */


class AnimationUseActivity : Activity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAnimationAdapter: AnimationAdapter
    private lateinit var mImgBtn: ImageView
    private val mFirstPageItemCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_use)
        mRecyclerView = findViewById(R.id.rv_list)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        initAdapter()
        initMenu()
        initView()
    }

    private fun initView() {

        mImgBtn = findViewById(R.id.img_back)
        mImgBtn.setOnClickListener { finish() }
    }

    private fun initAdapter() {
        mAnimationAdapter = AnimationAdapter()
        mAnimationAdapter.openLoadAnimation()
        mAnimationAdapter.setNotDoAnimationCount(mFirstPageItemCount)
        mAnimationAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            toast("第 " + position + "Item 被点击")
        }
//        mAnimationAdapter.openLoadAnimation(BaseAnimation
//                                    { view -> arrayOf(ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f, 1.0f), ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f, 1.0f))
//                                    })
        mAnimationAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            var content: String
            val status = adapter.getItem(position) as Status
            content = when (view.id) {
                R.id.img -> {
                    "img:" + status.userAvatar
                }
                R.id.tweetName -> {
                    "name:" + status.userName
                }
                R.id.tweetText -> {
                    "tweetText:" + status.userName
                }
                else -> {
                    "触发其他控件"
                }

            }// you have set clickspan .so there should not solve any click event ,just empty
            toast(content)
        }
        mRecyclerView.adapter = mAnimationAdapter
    }

    private fun initMenu() {
        val spinner = findViewById<MaterialSpinner>(R.id.spinner)
        spinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom")
        spinner.setOnItemSelectedListener { _, position, _, _ ->
            when (position) {
                0 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                1 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                2 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
                3 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
                4 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
                5 -> mAnimationAdapter.openLoadAnimation(CustomAnimation())
                else -> {
                }
            }
            mRecyclerView.adapter = mAnimationAdapter
        }
        mAnimationAdapter.isFirstOnly(false)//init firstOnly state
        val switchButton = findViewById<SwitchButton>(R.id.switch_button)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mAnimationAdapter.isFirstOnly(true)
            } else {
                mAnimationAdapter.isFirstOnly(false)
            }
            mAnimationAdapter.notifyDataSetChanged()
        }

    }

}
