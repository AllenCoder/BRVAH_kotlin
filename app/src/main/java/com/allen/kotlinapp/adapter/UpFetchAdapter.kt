package com.allen.kotlinapp.adapter

import com.allen.kotlinapp.R
import com.allen.kotlinapp.base.BaseDataBindingAdapter
import com.allen.kotlinapp.databinding.ItemMovieBinding
import com.allen.kotlinapp.entity.Movie

/**
 * 文 件 名: UpFetchAdapter
 * 创 建 人: Allen
 * 创建日期: 2017/6/14 18:58
 * 修改时间：
 * 修改备注：
 */
open class UpFetchAdapter : BaseDataBindingAdapter<Movie, ItemMovieBinding>(R.layout.item_movie) {

    override fun convert(binding: ItemMovieBinding, item: Movie) {
        binding.movie = item
    }
}
