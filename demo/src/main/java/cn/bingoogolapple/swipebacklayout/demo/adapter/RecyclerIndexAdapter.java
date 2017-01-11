package cn.bingoogolapple.swipebacklayout.demo.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.model.IndexModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerIndexAdapter extends BGARecyclerViewAdapter<IndexModel> {

    public RecyclerIndexAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_index_city);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        helper.setText(R.id.tv_item_index_city, model.name);
    }

    public boolean isCategory(int position) {
        int category = getItem(position).topc.charAt(0);
        return position == getPositionForCategory(category);
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getItem(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}