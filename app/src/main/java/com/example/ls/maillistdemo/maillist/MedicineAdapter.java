package com.example.ls.maillistdemo.maillist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ls.maillistdemo.R;

import java.util.List;

/**
 * Created by LS on 2017/12/29.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> implements StickyHeaderAdapter<MedicineAdapter.HeaderHolder> {
    private Context mContext;
    private List<MedicineBean> mDatas;
    private LayoutInflater mInflater;
    private View.OnClickListener mListener;

    public void setListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public MedicineAdapter(Context mContext, List<MedicineBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_medicine, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MedicineBean medicineBean = mDatas.get(position);
        String name = medicineBean.getName();
        int portId = medicineBean.getPortId();
        holder.tvName.setText(name);
        if (mListener != null) {
            holder.llName.setTag(name);
            holder.llName.setOnClickListener(mListener);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public String getHeaderId(int position) {
        return mDatas.get(position).getLetter();
    }

    @Override//生成header的布局
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderHolder(mInflater.inflate(R.layout.item_medicine_head, parent, false));
    }

    @Override//绑定header的数据
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText(mDatas.get(position).getLetter());
    }

    /**
     * 根据分类的首字母获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(String section) {
        for (int i = 0; i < mDatas.size(); i++) {
            String sortStr = mDatas.get(i).getLetter();
            if (sortStr.equals(section)) {
                return i;
            }
        }
        return -1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        LinearLayout llName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            llName = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }
}