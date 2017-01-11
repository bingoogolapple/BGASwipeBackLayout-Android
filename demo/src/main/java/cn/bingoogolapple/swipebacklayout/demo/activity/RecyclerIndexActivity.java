package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewScrollHelper;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.adapter.RecyclerIndexAdapter;
import cn.bingoogolapple.swipebacklayout.demo.util.DataUtil;
import cn.bingoogolapple.swipebacklayout.demo.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/11 下午5:08
 * 描述:测试与 RecyclerView 一起使用
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class RecyclerIndexActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private RecyclerIndexAdapter mAdapter;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private BGARecyclerViewScrollHelper mRecyclerViewScrollHelper;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycler_index);
        mDataRv = getViewById(R.id.rv_recyclerindexview_data);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_recyclerindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RecyclerIndexAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);

        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });
        mRecyclerViewScrollHelper = BGARecyclerViewScrollHelper.newInstance(mDataRv);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initToolbar();

        mIndexView.setTipTv(mTipTv);

        mDataRv.addItemDecoration(BGADivider.newBitmapDivider()
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(new BGADivider.SuspensionCategoryDelegate() {
                    @Override
                    protected boolean isCategory(int position) {
                        return mAdapter.isCategory(position);
                    }

                    @Override
                    protected String getCategoryName(int position) {
                        return mAdapter.getItem(position).topc;
                    }

                    @Override
                    protected int getFirstVisibleItemPosition() {
                        return mLayoutManager.findFirstVisibleItemPosition();
                    }
                }));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);

        mAdapter.setData(DataUtil.loadIndexModelData());
        mDataRv.setAdapter(mAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("测试 RecyclerView");
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(this, "选择了城市 " + mAdapter.getItem(position).name, Toast.LENGTH_SHORT).show();
    }
}
