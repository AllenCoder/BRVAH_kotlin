package com.allen.kotlinapp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.allen.kotlinapp.adapter.ItemClickAdapter
import com.allen.kotlinapp.base.BaseActivity
import com.allen.kotlinapp.entity.ClickEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.toast
import java.util.*

/**
 * 文 件 名: ItemClickActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/20 11:20
 * 修改时间：
 * 修改备注：
 */
class ItemClickActivity : BaseActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var adapter: ItemClickAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackBtn()
        setTitle("ItemClickActivity Activity")
        setContentView(R.layout.activity_item_click)
        mRecyclerView = findViewById(R.id.list)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        initAdapter()
        adapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            Log.d(TAG, "onItemClick: ")
            toast("onItemClick" + position)
        }
        adapter?.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { _, _, position ->
            Log.d(TAG, "onItemLongClick: ")
            toast( "onItemLongClick" + position)
            true
        }
        adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, position ->
            Log.d(TAG, "onItemChildClick: ")

            toast("onItemChildClick" + position)
        }
        adapter?.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { _, _, position ->
            Log.d(TAG, "onItemChildLongClick: ")
            toast( "onItemChildLongClick" + position)
            true
        }
//        mRecyclerView?.addOnItemTouchListener(object :com.chad.library.adapter.base.listener.OnItemClickListener(){
//            override fun onSimpleItemClick(p0: BaseQuickAdapter<*, *>?, p1: View?, p2: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })
        /**
         * you can also use this way to solve your click Event
         */

        /*  kotlin 注释
        * 有时候，我们需要创建⼀个对某个类做了轻微改动的类的对象，
        * ⽽不⽤为之显式声明新的⼦类。Java ⽤匿名内部类 处理这种情况。
        * Kotlin ⽤对象表达式和对象声明对这个概念稍微概括了下。
        * */
        /*mRecyclerView?.addOnItemTouchListener(object : OnItemClickListener() {
            *//**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.

             * @param view     The view within the AdapterView that was clicked (this
             * *                 will be a view provided by the adapter)
             * *
             * @param position The position of the view in the adapter.
             *//*
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                Log.d(SimpleClickListener.TAG, "SimpleOnItemClick: ")

            }

            *//**
             * callback method to be invoked when an chidview in this view has been
             * click and held

             * @param view     The view whihin the AbsListView that was clicked
             * *
             * @param position The position of the view int the adapter
             * *
             * @return true if the callback consumed the long click ,false otherwise
             *//*
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                Logger.d("onItemChildClick $position be click")
                toast(this@ItemClickActivity, "onItemChildClick" + position, Toast.LENGTH_SHORT).show()

            }

            *//**
             * Callback method to be invoked when an item in this view has been clicked and held.
             * @param adapter
             * *
             * @param view
             * *
             * @param position
             *//*
            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                toast(this@ItemClickActivity, "onItemLongClick" + position, Toast.LENGTH_SHORT).show()
            }

            *//**
             * Callback method to be invoked when an itemchild in this view has been clicked and held.
             * @param adapter
             * *
             * @param view
             * *
             * @param position
             *//*
            override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                toast(this@ItemClickActivity, "onItemChildLongClick" + position, Toast.LENGTH_SHORT).show()
            }
        })*/


    }

    private fun initAdapter() {
        val data = ArrayList<ClickEntity>()
        data.add(ClickEntity(ClickEntity.CLICK_ITEM_VIEW))
        data.add(ClickEntity(ClickEntity.CLICK_ITEM_CHILD_VIEW))
        data.add(ClickEntity(ClickEntity.LONG_CLICK_ITEM_VIEW))
        data.add(ClickEntity(ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW))
        data.add(ClickEntity(ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW))
        adapter = ItemClickAdapter(data)
        adapter?.openLoadAnimation()
        mRecyclerView?.adapter = adapter
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private val PAGE_SIZE = 10
        private val TAG = "ItemClickActivity"
    }

}
