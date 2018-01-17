package com.allen.kotlinapp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.allen.kotlinapp.adapter.UpFetchAdapter
import com.allen.kotlinapp.base.BaseActivity
import com.allen.kotlinapp.entity.Movie
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.util.*

/**
 * 文 件 名: UpFetchUseActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/20 11:25
 * 修改时间：
 * 修改备注：
 */

class UpFetchUseActivity() : BaseActivity(), Parcelable {
    private lateinit  var mRecyclerView: RecyclerView
    private lateinit  var mAdapter: UpFetchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackBtn()
        setTitle("UpFetch Use")
        setContentView(R.layout.activity_data_binding_use)

        mRecyclerView = findViewById(R.id.rv)
        mAdapter = UpFetchAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mAdapter.setNewData(genData())
        mAdapter.isUpFetchEnable = true
        /**
         * start fetch when scroll to position 2, default is 1.
         */
        mAdapter.setStartUpFetchPosition(2)
        mAdapter.setUpFetchListener({ startUpFetch() })
    }

    private var count: Int = 0

    constructor(parcel: Parcel) : this() {
        count = parcel.readInt()
    }

    private fun startUpFetch() {
        count++
        /**
         * set fetching on when start network request.
         */
        mAdapter.isUpFetching = true
        /**
         * get data from internet.
         */
        async(UI){
            mAdapter.addData(0, genData())
            /**
             * set fetching off when network request ends.
             */
            /**
             * set fetching off when network request ends.
             */
            mAdapter.isUpFetching = false
            /**
             * set fetch enable false when you don't need anymore.
             */
            /**
             * set fetch enable false when you don't need anymore.
             */
            if (count > 5) {
                mAdapter.isUpFetchEnable = false
            }
        }
    }


    private fun genData(): List<Movie> {
        val list = ArrayList<Movie>()
        val random = Random()
        for (i in 0..9) {
            val name = "Chad"
            val price = random.nextInt(10) + 10
            val len = random.nextInt(80) + 60
            val movie = Movie(name, len, price, "He was one of Australia's most distinguished artistes")
            list.add(movie)
        }
        return list
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpFetchUseActivity> {
        override fun createFromParcel(parcel: Parcel): UpFetchUseActivity {
            return UpFetchUseActivity(parcel)
        }

        override fun newArray(size: Int): Array<UpFetchUseActivity?> {
            return arrayOfNulls(size)
        }
    }
}
