package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import cn.bingoogolapple.swipebacklayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/9 上午10:27
 * 描述:「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class SplashActivity extends BaseActivity {
    private TextView mVersionTv;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        mVersionTv = getViewById(R.id.tv_splash_version);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mVersionTv.setText("v" + getCurrentVersionName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeBackHelper.forwardAndFinish(MainActivity.class);
            }
        }, 2000);
    }

    /**
     * 获取当前版本名称
     *
     * @return
     */
    public String getCurrentVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            // 利用系统api getPackageName()得到的包名，这个异常根本不可能发生
            return "";
        }
    }
}
