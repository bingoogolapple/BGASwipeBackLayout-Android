package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.bingoogolapple.swipebacklayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 上午10:10
 * 描述:测试下拉刷新、WebView等
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
 */
public class MovieActivity extends BaseActivity {
    private Button mControlBtn;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_movie);
        mControlBtn = getViewById(R.id.btn_movie_control);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSwipeBackHelper.setIsNavigationBarOverlap(true);
    }

    public void hideNavigationBar(View view) {
        mControlBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                getWindow().getDecorView().setSystemUiVisibility(uiOptions);

                mControlBtn.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }
}
