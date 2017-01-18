package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.view.View;

import cn.bingoogolapple.swipebacklayout.demo.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:35
 * 描述:示例项目主界面，改界面不需要支持滑动返回
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class MainActivity extends BaseActivity {

    /**
     * 主界面不需要支持滑动返回，重写该方法永久禁用当前界面的滑动返回功能
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mToolbar = getViewById(R.id.toolbar);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
    }

    public void testSwipeBack(View v) {
        mSwipeBackHelper.forward(TestActivity.class);
    }

    public void testTranslucent(View v) {
        mSwipeBackHelper.forward(TranslucentActivity.class);

//        mSwipeBackHelper.forwardAndFinish(TranslucentActivity.class);
    }

    public void testPullRefreshAndWebView(View v) {
        mSwipeBackHelper.forward(WebViewActivity.class);
    }

    public void testSwipeDelete(View v) {
        mSwipeBackHelper.forward(SwipeDeleteActivity.class);
    }

    public void testRecyclerView(View v) {
        mSwipeBackHelper.forward(RecyclerIndexActivity.class);
    }

    public void testEditText(View v) {
        mSwipeBackHelper.forward(EditTextActivity.class);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
