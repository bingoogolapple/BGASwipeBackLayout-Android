/*
 * Copyright 2016 bingoogolapple
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/4 下午3:44
 * 描述:滑动返回帮助类
 */
public class BGASwipeBackHelper {
    private Activity mActivity;
    private Delegate mDelegate;
    private BGASwipeBackLayout mSwipeBackLayout;

    public BGASwipeBackHelper(Activity activity, Delegate delegate) {
        mActivity = activity;
        mDelegate = delegate;

        initSwipeBackFinish();
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (mDelegate.isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(mActivity);
            mSwipeBackLayout.attachToActivity(mActivity);
            mSwipeBackLayout.setPanelSlideListener(new BGASwipeBackLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    mDelegate.onSwipeBackLayoutSlide(slideOffset);
                }

                @Override
                public void onPanelOpened(View panel) {
                    mDelegate.onSwipeBackLayoutExecuted();
                }

                @Override
                public void onPanelClosed(View panel) {
                    mDelegate.onSwipeBackLayoutCancel();
                }
            });
        }
    }

    /**
     * 设置滑动返回是否可用。默认值为 true
     *
     * @param swipeBackEnable
     * @return
     */
    public BGASwipeBackHelper setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackEnable(swipeBackEnable);
        }
        return this;
    }

    /**
     * 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
     *
     * @param isOnlyTrackingLeftEdge
     * @return
     */
    public BGASwipeBackHelper setIsOnlyTrackingLeftEdge(boolean isOnlyTrackingLeftEdge) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsOnlyTrackingLeftEdge(isOnlyTrackingLeftEdge);
        }
        return this;
    }

    /**
     * 设置是否是微信滑动返回样式。默认值为 true。如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this)
     *
     * @param isWeChatStyle
     * @return
     */
    public BGASwipeBackHelper setIsWeChatStyle(boolean isWeChatStyle) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsWeChatStyle(isWeChatStyle);
        }
        return this;
    }

    /**
     * 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
     *
     * @param shadowResId
     * @return
     */
    public BGASwipeBackHelper setShadowResId(@DrawableRes int shadowResId) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setShadowResId(shadowResId);
        }
        return this;
    }

    /**
     * 设置是否显示滑动返回的阴影效果。默认值为 true
     *
     * @param isNeedShowShadow
     * @return
     */
    public BGASwipeBackHelper setIsNeedShowShadow(boolean isNeedShowShadow) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsNeedShowShadow(isNeedShowShadow);
        }
        return this;
    }

    /**
     * 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
     *
     * @param isShadowAlphaGradient
     * @return
     */
    public BGASwipeBackHelper setIsShadowAlphaGradient(boolean isShadowAlphaGradient) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setIsShadowAlphaGradient(isShadowAlphaGradient);
        }
        return this;
    }

    /**
     * 执行跳转到下一个 Activity 的动画
     */
    public void executeForwardAnim() {
        mActivity.overridePendingTransition(R.anim.bga_sbl_activity_forward_enter, R.anim.bga_sbl_activity_forward_exit);
    }

    /**
     * 执行回到到上一个 Activity 的动画
     */
    public void executeBackwardAnim() {
        mActivity.overridePendingTransition(R.anim.bga_sbl_activity_backward_enter, R.anim.bga_sbl_activity_backward_exit);
    }

    /**
     * 执行滑动返回到到上一个 Activity 的动画
     */
    public void executeSwipeBackAnim() {
        mActivity.overridePendingTransition(R.anim.bga_sbl_activity_swipeback_enter, R.anim.bga_sbl_activity_swipeback_exit);
    }

    /**
     * 跳转到下一个 Activity，并且销毁当前 Activity
     *
     * @param cls 下一个 Activity 的 Class
     */
    public void forwardAndFinish(Class<?> cls) {
        forward(cls);
        mActivity.finish();
    }

    /**
     * 跳转到下一个 Activity，不销毁当前 Activity
     *
     * @param cls 下一个 Activity 的 Class
     */
    public void forward(Class<?> cls) {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(new Intent(mActivity, cls));
        executeForwardAnim();
    }

    /**
     * 跳转到下一个 Activity，不销毁当前 Activity
     *
     * @param cls         下一个 Activity 的 Class
     * @param requestCode 请求码
     */
    public void forward(Class<?> cls, int requestCode) {
        forward(new Intent(mActivity, cls), requestCode);
    }

    /**
     * 跳转到下一个 Activity，销毁当前 Activity
     *
     * @param intent 下一个 Activity 的意图对象
     */
    public void forwardAndFinish(Intent intent) {
        forward(intent);
        mActivity.finish();
    }

    /**
     * 跳转到下一个 Activity,不销毁当前 Activity
     *
     * @param intent 下一个 Activity 的意图对象
     */
    public void forward(Intent intent) {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(intent);
        executeForwardAnim();
    }

    /**
     * 跳转到下一个 Activity,不销毁当前 Activity
     *
     * @param intent      下一个 Activity 的意图对象
     * @param requestCode 请求码
     */
    public void forward(Intent intent, int requestCode) {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivityForResult(intent, requestCode);
        executeForwardAnim();
    }

    /**
     * 回到上一个 Activity，并销毁当前 Activity
     */
    public void backward() {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.finish();
        executeBackwardAnim();
    }

    /**
     * 滑动返回上一个 Activity，并销毁当前 Activity
     */
    public void swipeBackward() {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.finish();
        executeSwipeBackAnim();
    }

    /**
     * 回到上一个 Activity，并销毁当前 Activity（应用场景：欢迎、登录、注册这三个界面）
     *
     * @param cls 上一个 Activity 的 Class
     */
    public void backwardAndFinish(Class<?> cls) {
        BGAKeyboardUtil.closeKeyboard(mActivity);
        mActivity.startActivity(new Intent(mActivity, cls));
        mActivity.finish();
        executeBackwardAnim();
    }

    public interface Delegate {
        /**
         * 是否支持滑动返回
         *
         * @return
         */
        boolean isSupportSwipeBack();

        /**
         * 正在滑动返回
         *
         * @param slideOffset 从 0 到 1
         */
        void onSwipeBackLayoutSlide(float slideOffset);

        /**
         * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
         */
        void onSwipeBackLayoutCancel();

        /**
         * 滑动返回执行完毕，销毁当前 Activity
         */
        void onSwipeBackLayoutExecuted();
    }
}