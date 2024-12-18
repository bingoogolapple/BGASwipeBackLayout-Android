:running:BGASwipeBackLayout-Android:running:
============

强烈建议与 **[StatusBarUtil](https://github.com/laobie/StatusBarUtil)** 结合着一起使用

## 常见问题与反馈

### 1.使用透明主题时，滑动返回看见了 Launcher

保证栈底 Activity 的主题是不透明的。例如 demo 中的首个 Activity 是 SplashActivity，进入主界面后 SplashActivity 就销毁了，此时 MainActivity 就是栈底 Activity，需保证 MainActivity 的主题不透明

### 2.使用非透明主题时，滑动返回结束时立即触摸界面应用程序崩溃

把该崩溃界面里比较特殊的 View 的 class 添加到集合中作为「BGASwipeBackHelper.init」的第2个参数，例如地图控件。目前在库中已经添加了 WebView 和 SurfaceView，不用再次添加这两个了

## 功能介绍

- [x] 通过修改 support-v4 包中 SlidingPaneLayout 的源码来实现滑动返回布局
- [x] 支持非透明主题滑动返回，不影响 Activity 生命周期
- [x] 动态设置滑动返回是否可用
- [x] 动态设置是否仅仅跟踪左侧边缘的滑动返回
- [x] 动态设置是否是微信滑动返回样式
- [x] 动态设置是否显示滑动返回的阴影效果
- [x] 支持全屏、横屏和竖屏

## 效果图与示例 apk

| 普通滑动返回样式 | 微信滑动返回样式 |
| ------------ | ------------- |
| ![BGASwipeBackLayoutDemo](https://cloud.githubusercontent.com/assets/8949716/21512903/fac699f8-ccec-11e6-8437-1bfe8b9bd9d3.gif) | ![BGASwipeBackLayoutDemo-WeChat](https://cloud.githubusercontent.com/assets/8949716/21536263/7aa0fe88-cdbb-11e6-801d-4b370d6c454c.gif)  |

| 配合滑动删除列表一起使用 | 配合 RecycerView 一起使用 |
| ------------ | ------------- |
| ![bgaswipebacklayout-swipe-delete](https://cloud.githubusercontent.com/assets/8949716/21843157/ec74aeec-d824-11e6-9579-77549892e273.gif) | ![bgaswipebacklayout-recycler-index](https://cloud.githubusercontent.com/assets/8949716/21843158/ec784a3e-d824-11e6-9649-7397e5aad7eb.gif)  |

[点击下载 BGASwipeBackLayoutDemo.apk](http://fir.im/BGASwipeBackLayout) 或扫描下面的二维码安装

![BGASwipeBackLayoutDemo apk 文件二维码](https://cloud.githubusercontent.com/assets/8949716/21510942/c8e9c9e0-ccd4-11e6-9757-bbc6653cccdb.png)

### 1.添加 Gradle 依赖

* 把 `maven { url 'https://jitpack.io' }` 添加到 root build.gradle 的 repositories 中
* 在 app build.gradle 中添加如下依赖，末尾的「latestVersion」指的是徽章 [![](https://jitpack.io/v/bingoogolapple/BGASwipeBackLayout-Android.svg)](https://jitpack.io/#bingoogolapple/BGASwipeBackLayout-Android) 里的版本名称，请自行替换

```groovy
implementation 'com.github.bingoogolapple:BGASwipeBackLayout-Android:latestVersion'
// 换成己工程里依赖的 support-v4 的版本
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
```

### 2.必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
    }
}
```

### 3.将下面的代码拷贝到你自己的 BaseActivity 中，建议参考 demo 里的这个 [BaseActivity](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/blob/master/demo/src/main/java/cn/bingoogolapple/swipebacklayout/demo/activity/BaseActivity.java) 来设置界面跳转动画

```java
public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {
    protected BGASwipeBackHelper mSwipeBackHelper;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }
}
```

### 4.强烈强烈强烈建议把 [BGASwipeBackHelper](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/blob/master/library/src/main/java/cn/bingoogolapple/swipebacklayout/BGASwipeBackHelper.java) 里的每个方法的注释看一遍，只看注释就好

## demo 中用到的第三方库

* [StatusBarUtil](https://github.com/laobie/StatusBarUtil) A util for setting status bar style on Android App
* [BGABaseAdapter-Android](https://github.com/bingoogolapple/BGABaseAdapter-Android) 在 AdapterView 和 RecyclerView 中通用的 Adapter 和 ViewHolder。RecyclerView 支持 DataBinding 、多种 Item 类型、添加 Header 和 Footer。RecyclerView 竖直方向通用分割线 BGADivider，吸顶分类
* [BGAProgressBar-Android](https://github.com/bingoogolapple/BGAProgressBar-Android) 带百分比数字的水平、圆形进度条
* [BGARefreshLayout-Android](https://github.com/bingoogolapple/BGARefreshLayout-Android) 多种下拉刷新效果、上拉加载更多、可配置自定义头部广告位
* [BGASwipeItemLayout-Android](https://github.com/bingoogolapple/BGASwipeItemLayout-Android) 类似 iOS 带弹簧效果的左右滑动控件，可作为 AbsListView 和 RecyclerView 的 item
* 谷爹的 support 包

## 代码是最好的老师，更多详细用法请查看 [demo](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/tree/master/demo):feet:

## 作者联系方式

| 个人主页 | 邮箱 |
| ------------- | ------------ |
| <a  href="https://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> |

| 个人微信号 | 微信群 | 公众号 |
| ------------ | ------------ | ------------ |
| <img width="180" alt="个人微信号" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/BGAQrCode.png"> | <img width="180" alt="微信群" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/WeChatGroup1QrCode.jpg"> | <img width="180" alt="公众号" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/GongZhongHao.png"> |

| 个人 QQ 号 | QQ 群 |
| ------------ | ------------ |
| <img width="180" alt="个人 QQ 号" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/BGAQQQrCode.jpg"> | <img width="180" alt="QQ 群" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/QQGroup1QrCode.jpg"> |

## 打赏支持作者

如果您觉得 BGA 系列开源库或工具软件帮您节省了大量的开发时间，可以扫描下方的二维码打赏支持。您的支持将鼓励我继续创作，打赏后还可以加我微信免费开通一年 [上帝小助手浏览器扩展/插件开发平台](https://github.com/bingoogolapple/bga-god-assistant-config) 的会员服务

| 微信 | QQ | 支付宝 |
| ------------- | ------------- | ------------- |
| <img width="180" alt="微信" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/donate-wechat.jpg"> | <img width="180" alt="QQ" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/donate-qq.jpg"> | <img width="180" alt="支付宝" src="https://github.com/bingoogolapple/bga-god-assistant-config/raw/main/images/donate-alipay.jpg"> |

## 作者项目推荐

* 欢迎您使用我开发的第一个独立开发软件产品 [上帝小助手浏览器扩展/插件开发平台](https://github.com/bingoogolapple/bga-god-assistant-config)

## License

    Copyright (C) 2012 The Android Open Source Project
    Copyright 2016 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
