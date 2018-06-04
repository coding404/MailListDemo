package com.example.ls.maillistdemo.maillist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.example.ls.maillistdemo.R

/**
 * Created by LS on 2017/12/29.
 */

class MedicineAdapter(private val mContext: Context, private val mDatas: List<MedicineBean>?) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>(), StickyHeaderAdapter<MedicineAdapter.HeaderHolder> {
    private val mInflater: LayoutInflater
    private var mListener: View.OnClickListener? = null
    fun setListener(listener:View.OnClickListener){
        mListener=listener
    }

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_medicine, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicineBean = mDatas!![position]
        val name = medicineBean.name
        val portId = medicineBean.portId
        holder.tvName.text = name
        if (mListener != null) {
            holder.llName.tag = name
            holder.llName.setOnClickListener(mListener)
        }

    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun getHeaderId(position: Int): String? {
        return mDatas!![position].letter
    }

    override//生成header的布局
    fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderHolder {
        return HeaderHolder(mInflater.inflate(R.layout.item_medicine_head, parent, false))
    }

    override//绑定header的数据
    fun onBindHeaderViewHolder(viewholder: HeaderHolder, position: Int) {
        viewholder.header.text = mDatas!![position].letter
    }

    /**
     * 根据分类的首字母获取其第一次出现该首字母的位置
     */
    fun getPositionForSection(section: String): Int {
        for (i in mDatas!!.indices) {
            val sortStr = mDatas[i].letter
            if (sortStr == section) {
                return i
            }
        }
        return -1
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvName: TextView
        internal var llName: LinearLayout

        init {
            tvName = itemView.findViewById<View>(R.id.name) as TextView
            llName = itemView.findViewById<View>(R.id.ll_item) as LinearLayout
        }
    }

    class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var header: TextView

        init {
            header = itemView as TextView
        }
    }
}