package cn.bingoogolapple.swipebacklayout.demo;

import android.app.Application;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 下午11:55
 * 描述:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BGASwipeBackManager.getInstance().init(this);
    }
}
