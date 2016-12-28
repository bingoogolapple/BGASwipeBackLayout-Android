:running:BGASwipeBackLayout-Android:running:
============

## 功能介绍

- [x] 通过修改 support-v4 包中 SlidingPaneLayout 的源码来实现滑动返回布局
- [x] 动态设置滑动返回是否可用
- [x] 动态设置是否仅仅跟踪左侧边缘的滑动返回
- [x] 动态设置是否显示滑动返回的阴影效果

## 效果图与示例 apk

![BGASwipeBackLayoutDemo](https://cloud.githubusercontent.com/assets/8949716/21512903/fac699f8-ccec-11e6-8437-1bfe8b9bd9d3.gif)

[点击下载 BGASwipeBackLayoutDemo.apk](http://fir.im/BGASwipeBackLayout) 或扫描下面的二维码安装

![BGABannerDemo apk文件二维](https://cloud.githubusercontent.com/assets/8949716/21510942/c8e9c9e0-ccd4-11e6-9757-bbc6653cccdb.png)

### 1.添加 Gradle 依赖
 [ ![Download](https://api.bintray.com/packages/bintray/jcenter/cn.bingoogolapple%3Abga-swipebacklayout/images/download.svg) ](https://bintray.com/bintray/jcenter/cn.bingoogolapple%3Abga-swipebacklayout/_latestVersion) bga-swipebacklayout 后面的「latestVersion」指的是左边这个 Download 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    compile 'cn.bingoogolapple:bga-swipebacklayout:latestVersion@aar'

    // 换成己工程里依赖的 support-v4 的版本
    compile 'com.android.support:support-v4:25.1.0'
}
```

### 2.为需要支持滑动返回的 Activity 设置透明主题 AppTheme.Transparent

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

### 3.将下面的代码拷贝到你自己的 BaseActivity 中，建议参考 demo 里的这个 [BaseActivity](https://github.com/bingoogolapple/BGASwipeBackLayout-Android/blob/master/demo/src/main/java/cn/bingoogolapple/swipebacklayout/demo/activity/BaseActivity.java) 来设置界面跳转动画

```java
public class BaseActivity extends AppCompatActivity implements BGASwipeBackLayout.PanelSlideListener {
    protected BGASwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(this);
            // 将该滑动返回控件添加到 Activity 上
            mSwipeBackLayout.attachToActivity(this);
            // 设置滑动监听器
            mSwipeBackLayout.setPanelSlideListener(this);

            // 下面三项可以不配置，这里只是为了讲述接口用法

            // 设置滑动返回是否可用。默认值为 true
            mSwipeBackLayout.setSwipeBackEnable(true);
            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
            mSwipeBackLayout.setIsOnlyTrackingLeftEdge(true);
            // 设置是否显示滑动返回的阴影效果。默认值为 true
            mSwipeBackLayout.setIsNeedShowShadow(true);
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
}
```

## demo 中用到的第三方库

* [StatusBarUtil](https://github.com/laobie/StatusBarUtil) A util for setting status bar style on Android App
* [BGAAdapter-Android](https://github.com/bingoogolapple/BGAAdapter-Android) 在 AdapterView 和 RecyclerView 中通用的 Adapter 和 ViewHolder。RecyclerView 支持 DataBinding 、多种 Item 类型、添加 Header 和 Footer
* [BGAProgressBar-Android](https://github.com/bingoogolapple/BGAProgressBar-Android) 带百分比数字的水平、圆形进度条
* [BGARefreshLayout-Android](https://github.com/bingoogolapple/BGARefreshLayout-Android) 多种下拉刷新效果、上拉加载更多、可配置自定义头部广告位
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

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
