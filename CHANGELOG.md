Change Log
==========

Version 1.2.0 *(2018-10-27)*
----------------------------

* fix #122 fix #134 fix #138

Version 1.1.9 *(2018-06-20)*
----------------------------

* 修复某些手机能够动态设置底部导航导致布局显示不全的问题 fix #108 fix #106 fix #99 fix #101

Version 1.1.8 *(2017-12-21)*
----------------------------

* 前一个界面设置了 gravity 时，滑动返回导致布局下移 fix #78 fix #86

Version 1.1.7 *(2017-12-14)*
----------------------------

* 升级 gradle 插件到 3.x fix #82

Version 1.1.6 *(2017-11-04)*
----------------------------

* 修复非微信滑动返回样式时，先触摸滑动然后按返回键出现白屏

Version 1.1.5 *(2017-10-25)*
----------------------------

* fix #70

Version 1.1.4 *(2017-10-21)*
----------------------------

* fix #65
* fix #66
* 修改初始化方法 BGASwipeBackHelper.init
  * 第一个参数：应用程序上下文
  * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView

Version 1.1.3 *(2017-10-15)*
----------------------------

* 支持非透明主题模式
* 修改初始化方法 BGASwipeBackHelper.init
  * 第一个参数：应用程序上下文
  * 第二个参数：是否使用透明主题模式，建议传入 false 来使用非透明主题模式
  * 第三个参数：使用非透明主题时，如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView

Version 1.1.2 *(2017-10-13)*
----------------------------

* 初始化由「BGASwipeBackManager.getInstance().init(this)」改成「BGASwipeBackHelper.init(this)」
* 移除 BGASwipeBackManager 的 ignoreNavigationBarModels 方法
* 支持全屏、横屏
* 修复滑动释放逻辑错误
* 修复部分手机底部出现出现白色区域

Version 1.1.1 *(2017-09-07)*
----------------------------

* 修复部分手机底部出现出现白色区域

Version 1.1.0 *(2017-08-01)*
----------------------------

* BGASwipeBackManager 增加 ignoreNavigationBarModels 方法，忽略底部出现空白区域的手机对应的 android.Build.MODEL，修复部分手机底部出现出现白色区域

Version 1.0.9 *(2017-08-01)*
----------------------------

* fix #45 #47 #48 修复华为手机底部白色问题

Version 1.0.8 *(2017-03-13)*
----------------------------

* fix #31 修复使用微信滑动返回方式，界面有明显的抖动
* fix #30 修复正在滑动时，按下返回键上一个 Activity View 显示异常

Version 1.0.7 *(2017-01-11)*
----------------------------

* 在 BGASwipeBackHelper 中处理开始滑动返回时自动关闭软键盘

Version 1.0.6 *(2017-01-11)*
----------------------------

* 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init(this) 来初始化滑动返回，避免任务栈里只有一个 Activity 时滑动返回看见 Launcher

Version 1.0.5 *(2017-01-09)*
----------------------------

* 修复 Nexus 4 无法获取底部导航栏导致界面底部出现空白区域

Version 1.0.4 *(2017-01-08)*
----------------------------

* fix #10 修复4.4真机上结合 CoordinatorLayout 一起使用时底部出现空白
* fix #14 修复部分底部导航栏可以动态设置显示状态的手机出现遮挡

Version 1.0.3 *(2017-01-04)*
----------------------------

* 增加滑动返回帮助类 BGASwipeBackHelper，详细用法情况最新版 README 文档

Version 1.0.2 *(2016-12-29)*
----------------------------

* 增加 setIsWeChatStyle 方法，设置是否是微信滑动返回样式，默认值为 true「如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init(this)」
* 增加 setShadowResId 方法，设置阴影资源 id，默认值为 R.drawable.bga_swipebacklayout_shadow
* 增加 setIsShadowAlphaGradient，设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true

Version 1.0.1 *(2016-12-28)*
----------------------------

* 增加 setSwipeBackEnable 方法，设置滑动返回是否可用，默认值为 true
* 增加 setIsNeedShowShadow 方法，设置是否显示滑动返回的阴影效果，默认值为 true
* 增加 setIsOnlyTrackingLeftEdge 方法，设置是否仅仅跟踪左侧边缘的滑动返回，默认值为 true

Version 1.0.0 *(2016-12-27)*
----------------------------

* Initial release.
