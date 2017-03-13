Change Log
==========

Version 1.0.8 *(2017-03-13)*
----------------------------

* fix #31 修复使用微信滑动返回方式，界面有明显的抖动
* fix #30 修复正在滑动时，按下返回键上一个 Activity View 显示异常

Version 1.0.7 *(2017-01-11)*
----------------------------

* 在 BGASwipeBackHelper 中处理开始滑动返回时自动关闭软键盘

Version 1.0.6 *(2017-01-11)*
----------------------------

* 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回，避免任务栈里只有一个 Activity 时滑动返回看见 Launcher

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

* 增加 setIsWeChatStyle 方法，设置是否是微信滑动返回样式，默认值为 true「如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this)」
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
