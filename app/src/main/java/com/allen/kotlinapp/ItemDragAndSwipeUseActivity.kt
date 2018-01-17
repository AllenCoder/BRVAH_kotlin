package com.allen.kotlinapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.allen.kotlinapp.adapter.ItemDragAdapter
import com.allen.kotlinapp.base.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.orhanobut.logger.Logger
import org.jetbrains.anko.toast

/**
 * 文 件 名: ItemDragAndSwipeUseActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/20 11:21
 * 修改时间：
 * 修改备注：
 */
class ItemDragAndSwipeUseActivity : BaseActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mData: List<String>
    private lateinit var mAdapter: ItemDragAdapter
    private lateinit var mItemTouchHelper: ItemTouchHelper
    private lateinit var mItemDragAndSwipeCallback: ItemDragAndSwipeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_touch_use)
        setBackBtn()
        setTitle("ItemDrag  And Swipe")
        mRecyclerView = findViewById(R.id.rv_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mData = generateData(50)
        val listener = object : OnItemDragListener {
            override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Logger.d(TAG, "drag start")
            }

            override fun onItemDragMoving(source: RecyclerView.ViewHolder, from: Int, target: RecyclerView.ViewHolder, to: Int) {
                Logger.d(TAG, "move from: " + source.adapterPosition + " to: " + target.adapterPosition)
            }

            override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Logger.d(TAG, "drag end")
            }
        }
        val paint = Paint()
        paint.isAntiAlias = true
        paint.textSize = 20f
        paint.color = Color.BLACK
        val onItemSwipeListener = object : OnItemSwipeListener {
            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Logger.d(TAG, "view swiped start: " + pos)
            }

            override fun clearView(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Logger.d(TAG, "View reset: " + pos)
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Logger.d(TAG, "View Swiped: " + pos)
            }

            override fun onItemSwipeMoving(canvas: Canvas, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
                canvas.drawColor(ContextCompat.getColor(this@ItemDragAndSwipeUseActivity, R.color.color_light_blue))
                //                canvas.drawText("Just some text", 0, 40, paint);
            }
        }

        mAdapter = ItemDragAdapter(mData)
        mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mAdapter)
        mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRecyclerView)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        mAdapter.enableSwipeItem()
        mAdapter.setOnItemSwipeListener(onItemSwipeListener)
        mAdapter.enableDragItem(mItemTouchHelper)
        mAdapter.setOnItemDragListener(listener)
        mRecyclerView.adapter = mAdapter
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position -> toast("点击了" + position) }
    }

    private fun generateData(size: Int): List<String> {
        val data = ArrayList<String>(size)
        (0 until size).mapTo(data) { "item " + it }
        return data
    }


}
