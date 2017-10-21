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
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 下午11:42
 * 描述:
 */
class BGASwipeBackManager implements Application.ActivityLifecycleCallbacks {
    private static final BGASwipeBackManager sInstance = new BGASwipeBackManager();
    private Stack<Activity> mActivityStack = new Stack<>();
    private Set<Class<? extends View>> mProblemViewClassSet = new HashSet<>();

    public static BGASwipeBackManager getInstance() {
        return sInstance;
    }

    private BGASwipeBackManager() {
    }

    public void init(Application application, List<Class<? extends View>> problemViewClassList) {
        application.registerActivityLifecycleCallbacks(this);

        mProblemViewClassSet.add(WebView.class);
        mProblemViewClassSet.add(SurfaceView.class);
        if (problemViewClassList != null) {
            mProblemViewClassSet.addAll(problemViewClassList);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 获取倒数第二个 Activity
     *
     * @return
     */
    @Nullable
    public Activity getPenultimateActivity(Activity currentActivity) {
        Activity activity = null;
        try {
            if (mActivityStack.size() > 1) {
                activity = mActivityStack.get(mActivityStack.size() - 2);

                if (currentActivity.equals(activity)) {
                    int index = mActivityStack.indexOf(currentActivity);
                    if (index > 0) {
                        // 处理内存泄漏或最后一个 Activity 正在 finishing 的情况
                        activity = mActivityStack.get(index - 1);
                    } else if (mActivityStack.size() == 2) {
                        // 处理屏幕旋转后 mActivityStack 中顺序错乱
                        activity = mActivityStack.lastElement();
                    }
                }
            }
        } catch (Exception e) {
        }
        return activity;
    }

    /**
     * 滑动返回是否可用
     *
     * @return
     */
    public boolean isSwipeBackEnable() {
        return mActivityStack.size() > 1;
    }

    /**
     * 某个 view 是否会导致滑动返回后立即触摸界面时应用崩溃
     *
     * @param view
     * @return
     */
    public boolean isProblemView(View view) {
        return mProblemViewClassSet.contains(view.getClass());
    }
}