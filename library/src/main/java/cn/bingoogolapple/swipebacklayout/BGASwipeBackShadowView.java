package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 作者:王浩 邮件:wanghao76@meituan.com
 * 创建时间:2017/10/13
 * 描述:左侧阴影控件
 */
class BGASwipeBackShadowView extends FrameLayout {
    private Activity mActivity;
    private WeakReference<Activity> mPreActivity;
    private ViewGroup mPreDecorView;
    private View mPreContentView;
    private View mShadowView;
    /**
     * 是否使用透明模式
     */
    private boolean mIsTranslucent;
    /**
     * 是否显示滑动返回的阴影效果
     */
    private boolean mIsNeedShowShadow = true;
    /**
     * 阴影资源 id
     */
    private int mShadowResId = R.drawable.bga_sbl_shadow;
    /**
     * 阴影区域的透明度是否根据滑动的距离渐变
     */
    private boolean mIsShadowAlphaGradient = true;
    /**
     * 是否是微信滑动返回样式
     */
    private boolean mIsWeChatStyle = true;

    BGASwipeBackShadowView(Activity activity, boolean isTranslucent) {
        super(activity);
        mActivity = activity;
        mIsTranslucent = isTranslucent;
    }

    /**
     * 设置是否显示滑动返回的阴影效果。默认值为 true
     *
     * @param isNeedShowShadow
     */
    void setIsNeedShowShadow(boolean isNeedShowShadow) {
        mIsNeedShowShadow = isNeedShowShadow;
        updateShadow();
    }

    /**
     * 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
     *
     * @param shadowResId
     */
    void setShadowResId(@DrawableRes int shadowResId) {
        mShadowResId = shadowResId;
        updateShadow();
    }

    /**
     * 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
     *
     * @param isShadowAlphaGradient
     */
    void setIsShadowAlphaGradient(boolean isShadowAlphaGradient) {
        mIsShadowAlphaGradient = isShadowAlphaGradient;
    }

    /**
     * 设置是否是微信滑动返回样式。默认值为 true
     */
    void setIsWeChatStyle(boolean isWeChatStyle) {
        mIsWeChatStyle = isWeChatStyle;
    }

    private void updateShadow() {
        if (mIsTranslucent) {
            if (mIsNeedShowShadow) {
                setBackgroundResource(mShadowResId);
            } else {
                setBackgroundResource(android.R.color.transparent);
            }
        } else {
            if (mIsNeedShowShadow) {
                if (mShadowView == null) {
                    mShadowView = new View(getContext());
                    addView(mShadowView, getChildCount(), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                }
                mShadowView.setBackgroundResource(mShadowResId);
            } else {
                if (mShadowView != null) {
                    removeView(mShadowView);
                }
            }
        }
    }

    void bindPreActivity() {
        if (mIsTranslucent) {
            return;
        }

        if (mPreActivity == null) {
            Activity preActivity = BGASwipeBackManager.getInstance().getPenultimateActivity(mActivity);
            if (preActivity != null) {
                mPreActivity = new WeakReference<>(preActivity);
                mPreDecorView = (ViewGroup) preActivity.getWindow().getDecorView();
                mPreContentView = mPreDecorView.getChildAt(0);
                mPreDecorView.removeView(mPreContentView);

                addView(mPreContentView, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
        }
    }

    void unBindPreActivity() {
        if (mIsTranslucent) {
            return;
        }

        if (mPreActivity == null) {
            return;
        }

        Activity activity = mPreActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (mPreDecorView != null && mPreContentView != null) {
            fixTwinkle();

            removeView(mPreContentView);
            mPreDecorView.addView(mPreContentView, 0);

            mPreContentView = null;
            mPreActivity.clear();
            mPreActivity = null;
        }
    }

    /**
     * 修复滑动返回后闪屏
     * TODO 还有问题，ClassLoader 无法加载 com.android.internal.policy.PhoneWindow
     */
    private void fixTwinkle() {
        try {
            Window window = mActivity.getWindow();
            Class phoneWindowClass = Class.forName("com.android.internal.policy.PhoneWindow");
            Field isTranslucentField = phoneWindowClass.getDeclaredField("mIsTranslucent");
            isTranslucentField.setAccessible(true);
            isTranslucentField.setBoolean(window, true);
            Log.d("BGA", "动态设置 mIsTranslucent 成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BGA", "动态设置 mIsTranslucent 失败");
        }
    }

    void setShadowAlpha(float alpha) {
        if (mIsNeedShowShadow && mIsShadowAlphaGradient) {
            if (mIsTranslucent) {
                ViewCompat.setAlpha(this, alpha);
            } else if (mShadowView != null) {
                ViewCompat.setAlpha(mShadowView, alpha);
            }
        }
    }

    void onPanelSlide(float slideOffset) {
        if (!mIsWeChatStyle) {
            return;
        }

        if (mIsTranslucent) {
            BGASwipeBackManager.onPanelSlide(mActivity, slideOffset);
        } else if (mPreContentView != null) {
            ViewCompat.setTranslationX(mPreContentView, (mPreContentView.getMeasuredWidth() / 3.0f) * (1 - slideOffset));
        }
    }

    void onPanelClosed() {
        if (!mIsWeChatStyle) {
            return;
        }

        if (mIsTranslucent) {
            BGASwipeBackManager.onPanelClosed(mActivity);
        } else if (mPreContentView != null) {
            ViewCompat.setTranslationX(mPreContentView, 0);
        }
    }
}
