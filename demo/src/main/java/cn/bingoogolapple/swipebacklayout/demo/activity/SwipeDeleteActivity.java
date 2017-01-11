package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.adapter.SwipeDeleteAdapter;
import cn.bingoogolapple.swipebacklayout.demo.util.DataUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/11 上午2:46
 * 描述:测试与滑动删除一起使用
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class SwipeDeleteActivity extends BaseActivity implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private SwipeDeleteAdapter mAdapter;
    private RecyclerView mDataRv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_swipe_delete);
        mDataRv = getViewById(R.id.rv_swipe_delete_data);
    }

    @Override
    protected void setListener() {
        mAdapter = new SwipeDeleteAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initToolbar();

        mAdapter.setData(DataUtil.loadNormalModelDatas());
        mDataRv.addItemDecoration(BGADivider.newBitmapDivider());
        mDataRv.setAdapter(mAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("测试滑动删除");
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
            mAdapter.closeOpenedSwipeItemLayoutWithAnim();
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
            Toast.makeText(this, "长按了删除 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(this, "点击了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(this, "长按了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
        return true;
    }
}
