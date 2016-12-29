package cn.bingoogolapple.swipebacklayout.demo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/29 下午9:29
 * 描述:该类是临时修复 https://github.com/laobie/StatusBarUtil 与 CoordinatorLayout 结合使用时的 bug，等 StatusBarUtil 的作者更新
 */
public class BGAStatusBarUtil {
    private BGAStatusBarUtil() {
    }

    public static void setColorForSwipeBack(final Activity activity, @ColorInt final int color, @IntRange(from = 0, to = 255) final int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));

            // ======================== 新加的 START ========================
//            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
//            contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));

            View rootView = contentView.getChildAt(0);
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (coordinatorLayout.getFitsSystemWindows()) {
                            coordinatorLayout.setFitsSystemWindows(false);
                        }
                    }

                    contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                }
                coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            } else {
                contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            }
            // ======================== 新加的 END ========================

            setTransparentForWindow(activity);
        }
    }

    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    private static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
