package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.bingoogolapple.progressbar.BGAProgressBar;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.swipebacklayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 上午10:10
 * 描述:测试下拉刷新、WebView等
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class WebViewActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private WebView mContentWv;
    private BGAProgressBar mProgressBar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        mRefreshLayout = getViewById(R.id.refreshLayout);
        mContentWv = getViewById(R.id.wv_webview_content);
        mProgressBar = getViewById(R.id.progressBar);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);

        mContentWv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mRefreshLayout.endRefreshing();
            }
        });
        mContentWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        findViewById(R.id.transparent).setOnClickListener(this);
        findViewById(R.id.not_transparent).setOnClickListener(this);
        findViewById(R.id.praise).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initToolbar();

        initRefreshLayout();

        initWebView();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("测试下拉刷新和WebView");
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void initRefreshLayout() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(this, false);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.imoocstyle);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
    }

    private void initWebView() {
        mContentWv.getSettings().setJavaScriptEnabled(true);
        mContentWv.loadUrl("https://github.com/bingoogolapple");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.transparent) {
            setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (v.getId() == R.id.not_transparent) {
            setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark), 0);
        } else if (v.getId() == R.id.praise) {
            Toast.makeText(this, "谢谢你的赞", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mContentWv.reload();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
