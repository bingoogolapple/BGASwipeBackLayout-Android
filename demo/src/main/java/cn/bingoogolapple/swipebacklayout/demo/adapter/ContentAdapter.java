package cn.bingoogolapple.swipebacklayout.demo.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipebacklayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 上午1:21
 * 描述:
 */
public class ContentAdapter extends BGARecyclerViewAdapter<String> {

    public ContentAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_test);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.setText(R.id.tv_item_test_title, model);
        helper.setText(R.id.tv_item_test_content, "内容" + (position + 1));
    }
}