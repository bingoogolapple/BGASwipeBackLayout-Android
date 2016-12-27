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

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:26
 * 描述:
 */
class UIUtil {
    private UIUtil() {
    }

    /**
     * 获取底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 检测是否具有底部导航栏。改方法在一加手机上判断不准确，希望有猿友能修复这个 bug
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }
}
