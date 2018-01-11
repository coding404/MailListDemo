package com.example.ls.maillistdemo.maillist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by LS on 2017/12/29.
 */

public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {


    String getHeaderId(int position);


    T onCreateHeaderViewHolder(ViewGroup parent);


    void onBindHeaderViewHolder(T viewholder, int position);
}
