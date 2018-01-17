package com.allen.kotlinapp.adapter

import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.allen.kotlinapp.R
import com.allen.kotlinapp.data.DataServer
import com.allen.kotlinapp.entity.Status
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 文 件 名: AnimationAdapter
 * 创 建 人: Allen
 * 创建日期: 2017/6/13 14:01
 * 修改时间：
 * 修改备注：
 */
open class AnimationAdapter : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.layout_animation, DataServer.getSampleData(100)) {


    override fun convert(helper: BaseViewHolder, item: Status) {
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName)
        when (helper.layoutPosition % 3) {
            0 -> helper.setImageResource(R.id.img, R.mipmap.animation_img1)
            1 -> helper.setImageResource(R.id.img, R.mipmap.animation_img2)
            2 -> helper.setImageResource(R.id.img, R.mipmap.animation_img3)
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro")
        val msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\""
//        (helper.getView<TextView>(R.id.tweetText)).text = SpannableStringUtils.getBuilder(msg).append("landscapes and nedes").setClickSpan(clickableSpan).create()
        (helper.getView<TextView>(R.id.tweetText)).movementMethod = com.allen.kotlinapp.util.ClickableMovementMethod.getInstance()
        (helper.getView<TextView>(R.id.tweetText)).isFocusable = false
        (helper.getView<TextView>(R.id.tweetText)).isClickable = false
        (helper.getView<TextView>(R.id.tweetText)).isLongClickable = false
        (helper.getView<TextView>(R.id.tweetText)).movementMethod = LinkMovementMethod.getInstance()
    }

//    internal var clickableSpan: ClickableSpan = object : ClickableSpan() {
//        override fun onClick(widget: View) {
//            ToastUtils.showShortToast("事件触发了 landscapes and nedes")
//        }
//
//        override fun updateDrawState(ds: TextPaint) {
//            ds.color = Utils.getContext().getResources().getColor(R.color.clickspan_color)
//            ds.isUnderlineText = true
//        }
//    }
}

