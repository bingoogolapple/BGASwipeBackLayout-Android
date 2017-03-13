:running:BGASwipeBackLayout-Android:running:
============

强烈建议与 **[StatusBarUtil](https://github.com/laobie/StatusBarUtil)** 结合着一起使用

## 常见问题与反馈

### 1.滑动返回时看见了 Launcher

保证栈底 Activity 的主题是不透明的。例如 demo 中的首个 Activity 是 SplashActivity，进入主界面后 SplashActivity 就销毁了，此时 MainActivity 就是栈底 Activity，需保证 MainActivity 的主题不透明

### 2.滑动返回不生效

必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回

### 3.如果发现某些手机上底部出现空白区域，麻烦打印以下信息新建 [Issue](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/issues/new) 反馈

```
android.Build.VERSION.SDK_INT

android.Build.MODEL
```

## 功能介绍

- [x] 通过修改 support-v4 包中 SlidingPaneLayout 的源码来实现滑动返回布局
- [x] 动态设置滑动返回是否可用
- [x] 动态设置是否仅仅跟踪左侧边缘的滑动返回
- [x] 动态设置是否是微信滑动返回样式
- [x] 动态设置是否显示滑动返回的阴影效果

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
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-swipebacklayout/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-swipebacklayout) bga-swipebacklayout 后面的「latestVersion」指的是左边这个 maven-central 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    compile 'cn.bingoogolapple:bga-swipebacklayout:latestVersion@aar'

    // 换成己工程里依赖的 support-v4 的版本
    compile 'com.android.support:support-v4:25.2.0'
}
```

### 2.必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);
    }
}
```

### 3.为需要支持滑动返回的 Activity 设置透明主题 AppTheme.Transparent

```xml
<!-- 这里面的内容改成你自己项目里的 -->
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <!--colorPrimaryDark对应状态栏的颜色-->
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <!--colorPrimary对应ActionBar的颜色-->
    <item name="colorPrimary">@color/colorPrimary</item>
    <!-- 底部导航栏的颜色 -->
    <item name="android:navigationBarColor" tools:targetApi="lollipop">@color/navigationBarColor</item>
    <item name="android:windowBackground">@color/windowBackground</item>
    <!--colorAccent 对应EditText编辑时、RadioButton选中、CheckBox等选中时的颜色-->
    <item name="colorAccent">@color/colorAccent</item>
</style>

<!-- 用于开启滑动返回功能的 Activity -->
<style name="AppTheme.Transparent">
    <item name="android:windowBackground">@android:color/transparent</item>
    <item name="android:windowIsTranslucent">true</item>
</style>
```

### 4.将下面的代码拷贝到你自己的 BaseActivity 中，建议参考 demo 里的这个 [BaseActivity](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/blob/master/demo/src/main/java/cn/bingoogolapple/swipebacklayout/demo/activity/BaseActivity.java) 来设置界面跳转动画

```java
public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate, View.OnClickListener {
    protected BGASwipeBackHelper mSwipeBackHelper;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
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

### 5.强烈强烈强烈建议把 [BGASwipeBackHelper](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/blob/master/library/src/main/java/cn/bingoogolapple/swipebacklayout/BGASwipeBackHelper.java) 里的每个方法的注释看一遍，只看注释就好

## demo 中用到的第三方库

* [StatusBarUtil](https://github.com/laobie/StatusBarUtil) A util for setting status bar style on Android App
* [BGAAdapter-Android](https://github.com/bingoogolapple/BGAAdapter-Android) 在 AdapterView 和 RecyclerView 中通用的 Adapter 和 ViewHolder。RecyclerView 支持 DataBinding 、多种 Item 类型、添加 Header 和 Footer。RecyclerView 竖直方向通用分割线 BGADivider
* [BGAProgressBar-Android](https://github.com/bingoogolapple/BGAProgressBar-Android) 带百分比数字的水平、圆形进度条
* [BGARefreshLayout-Android](https://github.com/bingoogolapple/BGARefreshLayout-Android) 多种下拉刷新效果、上拉加载更多、可配置自定义头部广告位
* [BGASwipeItemLayout-Android](https://github.com/bingoogolapple/BGASwipeItemLayout-Android) 类似 iOS 带弹簧效果的左右滑动控件，可作为 AbsListView 和 RecyclerView 的 item
* 谷爹的 support 包

## 代码是最好的老师，更多详细用法请查看 [demo](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/tree/master/demo):feet:

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果您觉得 BGA 系列开源库帮你节省了大量的开发时间，请扫描下方的二维码随意打赏，要是能打赏个 10.24 :monkey_face:就太:thumbsup:了。您的支持将鼓励我继续创作:octocat:

如果您目前正打算购买通往墙外的梯子，可以使用我的邀请码「YFQ9Q3B」购买 [Lantern](https://github.com/getlantern/forum)，双方都赠送三个月的专业版使用时间:beers:

<p align="center">
  <img src="http://7xk9dj.com1.z0.glb.clouddn.com/bga_pay.png" width="450">
</p>

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
