package cn.bingoogolapple.swipebacklayout.demo.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.adapter.ContentAdapter;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午11:29
 * 描述:
 */
public class ContentFragment extends BaseFragment {
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private RecyclerView mContentRv;
    private ContentAdapter mContentAdapter;
    private ImageView mHeaderIv;

    private int mPosition;

    /**
     * @param position 位置
     * @return
     */
    public static ContentFragment newInstance(int position) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_test);
        mHeaderIv = getViewById(R.id.headerView);
        mContentRv = getViewById(R.id.recyclerView);
    }

    @Override
    protected void setListener() {
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
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mContentRv.addItemDecoration(BGADivider.newBitmapDivider());

        mPosition = getArguments().getInt(EXTRA_POSITION);
        if (mPosition == 0) {
            mHeaderIv.setImageResource(R.mipmap.one);
        } else if (mPosition == 1) {
            mHeaderIv.setImageResource(R.mipmap.two);
        } else {
            mHeaderIv.setImageResource(R.mipmap.three);
        }

        List<String> data = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            data.add("第" + (mPosition + 1) + "页 标题" + i);
        }
        mContentAdapter.setData(data);
        mContentRv.setAdapter(mContentAdapter);
    }
}
