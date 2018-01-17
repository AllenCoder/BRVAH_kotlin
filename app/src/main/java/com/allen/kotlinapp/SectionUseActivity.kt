package com.allen.kotlinapp

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.allen.kotlinapp.adapter.SectionAdapter
import com.allen.kotlinapp.base.BaseActivity
import com.allen.kotlinapp.data.DataServer
import com.allen.kotlinapp.entity.MySection
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.toast

/**
 * 文 件 名: SectionUseActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/20 11:24
 * 修改时间：
 * 修改备注：
 */
class SectionUseActivity : BaseActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mData: List<MySection>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section_uer)
        setBackBtn()
        setTitle("Section Use")
        mRecyclerView = findViewById(R.id.rv_list)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mData = DataServer.getSampleData()
        val sectionAdapter = SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData)

        sectionAdapter.setOnItemClickListener({ _, _, position ->
            val mySection = mData[position]
            if (mySection.isHeader)
                toast(mySection.header)
            else
                toast(mySection.t.name!!)
        })
        sectionAdapter.setOnItemChildClickListener({ _, _, position -> toast("onItemChildClick" + position) })
        mRecyclerView.adapter = sectionAdapter
    }


}
