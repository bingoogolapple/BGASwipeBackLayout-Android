package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.util.KeyboardUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:35
 * 描述:开发者可将该类中的某些方法拷贝到自己的 BaseActivity 中封装成适合自己项目的滑动返回基类
 */
public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackLayout.PanelSlideListener, View.OnClickListener {
    protected BGASwipeBackLayout mSwipeBackLayout;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);

        initView(savedInstanceState);
        mToolbar = getViewById(R.id.toolbar);

        setListener();
        processLogic(savedInstanceState);
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(this);
            mSwipeBackLayout.attachToActivity(this);
            mSwipeBackLayout.setPanelSlideListener(this);

            // 下面四项项可以不配置，这里只是为了讲述接口用法。
            // 如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this)

            // 设置滑动返回是否可用。默认值为 true
            mSwipeBackLayout.setSwipeBackEnable(true);
            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
            mSwipeBackLayout.setIsOnlyTrackingLeftEdge(true);
            // 设置是否是微信滑动返回样式。默认值为 true
            mSwipeBackLayout.setIsWeChatStyle(true);
            // 设置阴影资源 id。默认值为 R.drawable.bga_swipebacklayout_shadow
            mSwipeBackLayout.setShadowResId(R.drawable.bga_swipebacklayout_shadow);
            // 设置是否显示滑动返回的阴影效果。默认值为 true
            mSwipeBackLayout.setIsNeedShowShadow(true);
            // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
            mSwipeBackLayout.setIsShadowAlphaGradient(true);
        }
    }

    /**
     * 是否支持滑动返回。默认支持，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 动态设置滑动返回是否可用。
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
        swipeBackward();
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backward();
    }

    /**
     * 跳转到下一个Activity，并且销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forwardAndFinish(Class<?> cls) {
        forward(cls);
        finish();
    }

    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<?> cls) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(new Intent(this, cls));
        executeForwardAnim();
    }

    public void forward(Class<?> cls, int requestCode) {
        forward(new Intent(this, cls), requestCode);
    }

    public void forwardAndFinish(Intent intent) {
        forward(intent);
        finish();
    }

    public void forward(Intent intent) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(intent);
        executeForwardAnim();
    }

    public void forward(Intent intent, int requestCode) {
        KeyboardUtil.closeKeyboard(this);
        startActivityForResult(intent, requestCode);
        executeForwardAnim();
    }

    /**
     * 执行跳转到下一个Activity的动画
     */
    public void executeForwardAnim() {
        overridePendingTransition(R.anim.activity_forward_enter, R.anim.activity_forward_exit);
    }

    /**
     * 回到上一个Activity，并销毁当前Activity
     */
    public void backward() {
        KeyboardUtil.closeKeyboard(this);
        finish();
        executeBackwardAnim();
    }

    /**
     * 滑动返回上一个Activity，并销毁当前Activity
     */
    public void swipeBackward() {
        KeyboardUtil.closeKeyboard(this);
        finish();
        executeSwipeBackAnim();
    }

    /**
     * 回到上一个Activity，并销毁当前Activity（应用场景：欢迎、登录、注册这三个界面）
     *
     * @param cls 上一个Activity的Class
     */
    public void backwardAndFinish(Class<?> cls) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(new Intent(this, cls));
        executeBackwardAnim();
        finish();
    }

    /**
     * 执行回到到上一个Activity的动画
     */
    public void executeBackwardAnim() {
        overridePendingTransition(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
    }

    /**
     * 执行滑动返回到到上一个Activity的动画
     */
    public void executeSwipeBackAnim() {
        overridePendingTransition(R.anim.activity_swipeback_enter, R.anim.activity_swipeback_exit);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 需要处理点击事件时，重写该方法
     *
     * @param v
     */
    public void onClick(View v) {
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }
}
