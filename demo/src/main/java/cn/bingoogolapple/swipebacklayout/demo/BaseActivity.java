package cn.bingoogolapple.swipebacklayout.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:35
 * 描述:
 */
public class BaseActivity extends AppCompatActivity implements BGASwipeBackLayout.PanelSlideListener {
    protected BGASwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(this);
            mSwipeBackLayout.attachToActivity(this);
            mSwipeBackLayout.setPanelSlideListener(this);
        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 设置滑动返回是否可用
     *
     * @param swipeBackEnable
     */
    protected void setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackEnable(swipeBackEnable);
        }
    }

    @Override
    public void onPanelClosed(View view) {
    }

    @Override
    public void onPanelOpened(View view) {
        finish();
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }
}
