package com.example.ls.maillistdemo.maillist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by LS on 2017/12/29.
 */

interface StickyHeaderAdapter<T : RecyclerView.ViewHolder> {


    fun getHeaderId(position: Int): String?


    fun onCreateHeaderViewHolder(parent: ViewGroup): T


    fun onBindHeaderViewHolder(viewholder: T, position: Int)
}
