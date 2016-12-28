package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.adapter.ContentAdapter;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 上午1:06
 * 描述:Test Translucent StatusBar
 */
public class TranslucentActivity extends BaseActivity {
    private ContentAdapter mContentAdapter;
    private RecyclerView mContentRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent);

        initToolbar();
        initRecyclerView();

        StatusBarUtil.setTranslucentForImageView(this, 0, findViewById(R.id.ll_translucent_content));
    }

    private void initToolbar() {
        Toolbar toolbar = getViewById(R.id.toolbar);
        toolbar.setBackgroundResource(android.R.color.transparent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Translucent StatusBar");
    }

    private void initRecyclerView() {
        mContentRv = getViewById(R.id.recyclerView);
        mContentRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, getResources().getDimensionPixelOffset(R.dimen.size_level1), 0, getResources().getDimensionPixelOffset(R.dimen.size_level1));
            }
        });

        mContentAdapter = new ContentAdapter(mContentRv);
        mContentAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(parent.getContext(), "点击了条目 " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mContentAdapter.setOnRVItemLongClickListener(new BGAOnRVItemLongClickListener() {
            @Override
            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(parent.getContext(), "长按了条目 " + (position + 1), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        List<String> data = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            data.add("标题" + i);
        }
        mContentAdapter.setData(data);
        mContentRv.setAdapter(mContentAdapter);
    }
}
